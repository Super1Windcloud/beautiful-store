package org.superwindcloud.beauti_store.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.superwindcloud.beauti_store.services.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Data
public class SecurityConfig {

  private final CustomOAuth2UserService customOAuth2UserService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            auth ->
                auth.requestMatchers(
                        "/api/**", "/register/**", "/login/**", "/logout/**", "/", "/login")
                    .permitAll()
                    .requestMatchers("/admin/**")
                    .hasRole("ADMIN")
                    .requestMatchers("/", "/login/**", "/oauth2/**", "/error")
                    .permitAll()
                    .requestMatchers(
                        "/",
                        "/index.html",
                        "/static/**",
                        "/assets/**",
                        "/favicon.ico",
                        "/js/**",
                        "/css/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .oauth2Login(
            oauth ->
                oauth
                    .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                    .defaultSuccessUrl("http://localhost:33333/home", true))
        .formLogin(AbstractHttpConfigurer::disable) // ❌ 禁用 Spring 默认登录页
        .httpBasic(AbstractHttpConfigurer::disable) // 也禁用 basic 登录
        .csrf(AbstractHttpConfigurer::disable) // 跨站请求伪造
        .cors(Customizer.withDefaults())
        .exceptionHandling(
            ex ->
                ex.authenticationEntryPoint(
                    (request, response, authException) -> {
                      String accept = request.getHeader("Accept");
                      if (accept != null && accept.contains("application/json")) {
                        response.setContentType("application/json;charset=UTF-8");
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("{\"code\":401,\"message\":\"未登录\"}");
                      } else {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.setContentType("text/plain;charset=UTF-8");
                        response.getWriter().write("请登录");
                        response.sendRedirect("/");
                      }
                    }));

    return http.build();
  }
}
