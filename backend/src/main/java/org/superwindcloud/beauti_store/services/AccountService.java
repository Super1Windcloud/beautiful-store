package org.superwindcloud.beauti_store.services;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.superwindcloud.beauti_store.repository.AccountRepo;

@Service
@Data
@RequiredArgsConstructor
public class AccountService {

  private final AccountRepo accountRepo;

  public final void fillMissingCreatedDate() {
    accountRepo
        .findAll()
        .forEach(
            account -> {
              if (account.getCreatedDate() == null) {
                account.setCreatedDate(LocalDateTime.now());
                accountRepo.save(account);
              }
            });
    System.out.println("All missing created dates have been filled.");
  }

  public final void initAccountBalance() {
    accountRepo
        .findAll()
        .forEach(
            account -> {
              if (account.getBalance() == null) {
                account.setBalance("0.00");
                accountRepo.save(account);
              }
            });
  }

  public final void initAccountLoginType() {
    accountRepo
        .findAll()
        .forEach(
            account -> {
              if (account.getLoginType() == null) {
                if (account.getPassword().equals("oauth2_github")) {
                  account.setLoginType("oauth2_github");
                  accountRepo.save(account);
                } else {
                  account.setLoginType("email");
                  accountRepo.save(account);
                }
              }
            });
  }
}
