package org.superwindcloud.beauti_store;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.superwindcloud.beauti_store.repository.AccountRepo;
import org.superwindcloud.beauti_store.services.AccountService;

@Data
@SpringBootTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class OauthTest {

  private final AccountRepo accountRepo;

  private final AccountService accountService;

  @Test
  public void testOauthGithubAccount() {
    accountService.initAccountLoginType();
    printAllAccounts();
  }

  @Test
  public void printAllAccounts() {
    var accounts = accountRepo.findAll();
    System.out.println("All accounts:");
    accounts.forEach(System.out::println);
  }

  @Test
  public void printAllAccountsByLoginType() {
    var accounts = accountRepo.findAllByLoginType("oauth2_github");
    System.out.println("All accounts with oauth2_github login type:");
    accounts.forEach(System.out::println);
    var emails = accountRepo.findAllByLoginType("email");
    System.out.println("All accounts with email login type:");
    emails.forEach(System.out::println);
  }
}
