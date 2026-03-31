package org.superwindcloud.beauti_store.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.superwindcloud.beauti_store.dao.Account;
import org.superwindcloud.beauti_store.model.ApiResponse;
import org.superwindcloud.beauti_store.repository.AccountRepo;
import org.superwindcloud.beauti_store.services.Email;

@RestController
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@RequestMapping("/api")
public class Register {

  private final AccountRepo accountRepo;

  private final RedisTemplate<String, String> redisTemplate;
  private String username;
  private String password;
  private String email;

  private final Email emailService;

  @PostMapping("/register")
  public String register(@RequestParam String email) {
    return "success";
  }

  @GetMapping("/register/email")
  public String sendEmail(@RequestParam String email) {
    String code = generateCode();
    emailService.sendVerificationCode(email, code);
    if (redisTemplate.opsForValue().get("email:code:" + email) != null) {
      redisTemplate.delete("email:code:" + email);
      redisTemplate.opsForValue().set("email:code:" + email, code, 5, TimeUnit.MINUTES);
    } else {
      redisTemplate.opsForValue().set("email:code:" + email, code, 5, TimeUnit.MINUTES);
    }

    return "send email success";
  }

  private static String generateCode() {
    return String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
  }

  @GetMapping("/register/email/verify")
  public String verify(@RequestParam String email, @RequestParam String code) {
    String redisCode = redisTemplate.opsForValue().get("email:code:" + email);
    if (redisCode == null) {
      return "验证码已过期";
    }
    if (!redisCode.equals(code)) {
      return "验证码错误";
    }
    return "验证成功";
  }

  @PostMapping("/register/account")
  public String createAccount(@RequestBody @NonNull Account account) {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String hashedPassword = encoder.encode(account.getPassword());

    if (accountRepo.findByEmail(account.getEmail()) != null) {
      return "email already exists";
    }
    account.setPassword(hashedPassword);
    accountRepo.save(account);

    return "success create account";
  }

  public static void main(String[] args) {
    System.out.println(generateCode());
  }

  @PostMapping("/login/account")
  public ResponseEntity<ApiResponse<Object>> loginAccount(
      @RequestBody @NonNull Account account, HttpServletRequest request) {
    System.out.println(account.getEmail());
    Account accountInDb = accountRepo.findByEmail(account.getEmail());

    if (accountInDb == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(new ApiResponse<>(404, "账号不存在", null));
    }

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    if (!encoder.matches(account.getPassword(), accountInDb.getPassword())) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new ApiResponse<>(401, "密码错误", null));
    }

    List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
    // ✅ 手动创建认证信息，并加入 Spring Security 的上下文
    UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(accountInDb.getEmail(), null, authorities);

    System.out.println("创建session会话");
    SecurityContextHolder.getContext().setAuthentication(authToken);

    HttpSession session = request.getSession(true);
    session.setAttribute("username", accountInDb.getEmail());
    session.setMaxInactiveInterval(3600); // session 过期时间 1 小时
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authToken);
    session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
    return ResponseEntity.ok(new ApiResponse<>(200, "登录成功", null));
  }

  @PostMapping("/logout/account")
  public String logoutAccount(HttpServletRequest request, HttpServletResponse response) {
    var auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    return "已成功注销";
  }

  @GetMapping("/logout/oauth2")
  public String logoutOAuth2(HttpServletRequest request, HttpServletResponse response)
      throws ServletException {
    request.logout(); // Servlet 标准方法，等效于 session.invalidate() + 清空认证信息
    request.getSession().invalidate();
    SecurityContextHolder.clearContext();
    HttpSession session = request.getSession(false);
    if (session != null) {
      session.invalidate(); // 清除 session
    }
    //        response.sendRedirect("/login");
    return "已成功注销OAuth2";
  }

  @GetMapping("/login/check_status")
  public ResponseEntity<Map<String, Object>> checkLoginStatus(Authentication authentication) {
    Map<String, Object> response = new HashMap<>();

    if (authentication == null || !authentication.isAuthenticated()) {
      response.put("loggedIn", false);
      return ResponseEntity.ok(response);
    }

    response.put("loggedIn", true);

    if (authentication instanceof UsernamePasswordAuthenticationToken) {
      response.put("loginType", "email");
      response.put("username", authentication.getName());
    } else if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
      response.put("loginType", "oauth2");
      OAuth2User user = oauthToken.getPrincipal();
      response.put("username", user.getAttribute("login")); // GitHub 登录名
      response.put("provider", oauthToken.getAuthorizedClientRegistrationId()); // github / google
    }

    return ResponseEntity.ok(response);
  }
}
