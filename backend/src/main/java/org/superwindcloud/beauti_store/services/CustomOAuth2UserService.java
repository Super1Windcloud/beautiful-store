package org.superwindcloud.beauti_store.services;

import java.util.Map;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.superwindcloud.beauti_store.dao.Account;
import org.superwindcloud.beauti_store.repository.AccountRepo;

@Service
@Data
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final AccountRepo accountRepo;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = delegate.loadUser(userRequest);

    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    Map<String, Object> attributes = oAuth2User.getAttributes();

    String email = null;
    String username = null;
    String avatarUrl = null;
    String loginType = null;

    if ("github".equals(registrationId)) {
      email = (String) attributes.get("email");
      username = (String) attributes.get("login");
      avatarUrl = (String) attributes.get("avatar_url");
      loginType = "oauth2_github";

      if (email == null) {
        email = username + "@github.fake";
      }

    } else if ("google".equals(registrationId)) {
      email = (String) attributes.get("email");
      username = (String) attributes.get("name");
      avatarUrl = (String) attributes.get("picture");
      loginType = "oauth2_google";
    } else {
      throw new OAuth2AuthenticationException(
          new OAuth2Error("unsupported_provider"), "暂不支持该平台登录: " + registrationId);
    }

    Account account = accountRepo.findByEmail(email);

    if (account == null || account.getEmail().equals("ss1178933440@gmail.com")) {
      System.out.println("创建新用户: " + email);
      var accountOauth2 = new Account();
      accountOauth2.setEmail("oauth2_" + registrationId + "_" + email);
      accountOauth2.setPassword("oauth2_" + registrationId); // 占位
      accountOauth2.setUsername(username);
      accountOauth2.setAvatarUrl(avatarUrl);
      accountOauth2.setBalance("0.01");
      accountOauth2.setLoginType(loginType);
      accountRepo.save(accountOauth2);
    }

    System.out.println("Oauth2登录成功: " + email);
    return oAuth2User;
  }
}
