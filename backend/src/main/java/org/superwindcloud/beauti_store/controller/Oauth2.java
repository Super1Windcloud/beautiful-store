package org.superwindcloud.beauti_store.controller;

import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/oauth2")
public class Oauth2 {

  @GetMapping("/github/userinfo")
  private List<String> getCurrentGithubUserInfo(OAuth2AuthenticationToken authentication) {
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new RuntimeException("未登录");
    }

    String registrationId = authentication.getAuthorizedClientRegistrationId(); // 关键点
    if (!registrationId.equals("github")) {
      throw new RuntimeException("非github登录");
    }
    OAuth2User user = authentication.getPrincipal();
    Map<String, Object> attributes = user.getAttributes();

    String username = (String) attributes.get("login");
    String avatarUrl = (String) attributes.get("avatar_url");
    return List.of(username, avatarUrl);
  }

  @GetMapping("/google/userinfo")
  private List<String> getCurrentGoogleUserInfo(OAuth2AuthenticationToken authentication) {
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new RuntimeException("未登录");
    }

    String registrationId = authentication.getAuthorizedClientRegistrationId(); // 关键点
    if (!registrationId.equals("google")) {
      throw new RuntimeException("非google登录");
    }
    OAuth2User user = authentication.getPrincipal();
    Map<String, Object> attributes = user.getAttributes();

    System.out.println("google attributes: " + attributes);
    String username = (String) attributes.get("name");
    String avatarUrl = (String) attributes.get("picture");
    return List.of(username, avatarUrl);
  }
}
