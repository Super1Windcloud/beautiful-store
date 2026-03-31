package org.superwindcloud.beauti_store.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.superwindcloud.beauti_store.dao.Account;
import org.superwindcloud.beauti_store.repository.AccountRepo;
import org.superwindcloud.beauti_store.repository.GirlPhotoRepository;
import org.superwindcloud.beauti_store.repository.GoodsRepo;
import org.superwindcloud.beauti_store.services.GirlPhotoService;
import org.superwindcloud.beauti_store.utils.SystemUtils;

@RestController
@Data
@RequiredArgsConstructor
@RequestMapping("/api")
@EqualsAndHashCode(callSuper = false)
public class Home {

  private final GoodsRepo goodsRepo;
  private final GirlPhotoService girlPhotoService;
  private final GirlPhotoRepository girlPhotoRepository;
  private final AccountRepo accountRepo;

  @GetMapping("/home/hello")
  public String getHelloMessage() {
    return "Hello World! home controller";
  }

  @GetMapping("/home/login_user_info")
  public Map<String, Object> getCurrentUserInfo(Authentication authentication) {
    Map<String, Object> result = new HashMap<>();

    if (authentication instanceof UsernamePasswordAuthenticationToken) {
      // 本地用户登录
      String email = authentication.getName();
      Account account = accountRepo.findByEmail(email);
      result.put("email", account.getEmail());
      result.put("loginType", "email");

    } else if (authentication instanceof OAuth2AuthenticationToken oauthToken) {
      String registrationId = oauthToken.getAuthorizedClientRegistrationId(); // 关键点

      OAuth2User user = oauthToken.getPrincipal();
      System.out.println(user.getAttributes());

      String username = null;
      String avatar = null;
      String loginType = "oauth2"; // 默认值

      if ("github".equalsIgnoreCase(registrationId)) {
        username = (String) user.getAttribute("login");
        avatar = (String) user.getAttribute("avatar_url");
        loginType = "oauth2_github";

      } else if ("google".equalsIgnoreCase(registrationId)) {
        username = (String) user.getAttribute("name");
        avatar = (String) user.getAttribute("picture");
        loginType = "oauth2_google";
      }

      result.put("username", username);
      result.put("avatar", avatar);
      result.put("loginType", loginType);
    }

    return result;
  }

  @GetMapping("/home/email")
  public ResponseEntity<String> getEmailAddress() {
    System.out.println(
        "email address " + SecurityContextHolder.getContext().getAuthentication().getName());
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null
        || !authentication.isAuthenticated()
        || "anonymousUser".equals(authentication.getPrincipal())) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("未登录");
    }

    String email = authentication.getName();
    return ResponseEntity.ok(email);
  }

  @GetMapping("/home/goods/steam")
  public ResponseEntity<String> getSteamGoods(@RequestParam String email) {

    if (!email.contains("@")) {
      email = email + "@github.fake";
    }

    System.out.println("current user email" + email);
    var good = goodsRepo.findByEmail(email);

    if (good == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("未找到该用户的商品信息");
    }

    return ResponseEntity.ok(good.getSteamUnitPrice().toString());
  }

  @GetMapping("/home/goods/neteasecloud")
  public ResponseEntity<String> getNeteasecloudGoods(@RequestParam String email) {

    if (!email.contains("@")) {
      email = email + "@github.fake";
    }

    System.out.println("current user email" + email);
    var good = goodsRepo.findByEmail(email);

    if (good == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("未找到该用户的商品信息");
    }

    return ResponseEntity.ok(good.getNeteasecloudPrice().toString());
  }

  @GetMapping("/home/goods/jbide")
  public ResponseEntity<String> getJbideGoodsPrice(@RequestParam String email) {

    if (!email.contains("@")) {
      email = email + "@github.fake";
    }

    System.out.println("current user email" + email);
    var good = goodsRepo.findByEmail(email);

    if (good == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("未找到该用户的商品信息");
    }

    return ResponseEntity.ok(good.getJbIdePrice().toString());
  }

  @GetMapping("/home/goods/xiecheng")
  public ResponseEntity<String> getXiechengGoodsPrice(@RequestParam String email) {

    if (!email.contains("@")) {
      email = email + "@github.fake";
    }

    var good = goodsRepo.findByEmail(email);

    if (good == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("未找到该用户的商品信息");
    }

    return ResponseEntity.ok(good.getMeituanPrice().toString());
  }

  @GetMapping("/home/goods/piao")
  public ResponseEntity<String> getPiaoGoodsPrice(@RequestParam String email) {

    if (!email.contains("@")) {
      email = email + "@github.fake";
    }
    var good = goodsRepo.findByEmail(email);

    if (good == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("未找到该用户的商品信息");
    }

    return ResponseEntity.ok(good.getPiaoPrice().toString());
  }

  @GetMapping("/home/goods/girl")
  public ResponseEntity<String> getGirlPhotoPrice(@RequestParam String email) {
    if (!email.contains("@")) {
      email = email + "@github.fake";
    }
    System.out.println("current user email" + email);

    var good = goodsRepo.findByEmail(email);

    if (good == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("未找到该用户的商品信息");
    }

    return ResponseEntity.ok(good.getGirlPrice().toString());
  }

  @GetMapping("/home/goods/girl-imgs")
  public ResponseEntity<String> getGirlPhotos() {

    List<String> base64Images = new ArrayList<>();
    try {
      var images = SystemUtils.readGirlImages();
      images.forEach(
          image -> {
            try {
              Resource resource = new ClassPathResource(image);
              byte[] bytes = resource.getInputStream().readAllBytes();
              String base64Image = Base64.getEncoder().encodeToString(bytes);
              base64Images.add(base64Image);

            } catch (Exception e) {
              e.printStackTrace();
            }
          });
      return ResponseEntity.ok(base64Images.toString());
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.ok("[]");
    }
  }

  @GetMapping("/home/goods/girl-imgs-db")
  public List<String> getGirlPhotosFromDb(HttpServletRequest request) {

    return girlPhotoRepository.findAll().stream()
        .map(
            photo -> {
              String base64 = Base64.getEncoder().encodeToString(photo.getData());
              return "data:" + photo.getContentType() + ";base64," + base64;
            })
        .toList();
  }

  private final GirlPhotoService service;

  @GetMapping("/home/goods/girl-import")
  public String importImages() {
    try {
      service.saveAllGirlImages();
      return "图片已成功保存到数据库";
    } catch (Exception e) {
      e.printStackTrace();
      return "保存失败：" + e.getMessage();
    }
  }
}
