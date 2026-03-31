package org.superwindcloud.beauti_store.controller;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.superwindcloud.beauti_store.repository.AccountRepo;

@RestController
@Data
@RequiredArgsConstructor
@RequestMapping("/api")
@EqualsAndHashCode(callSuper = false)
public class About {

  private final AccountRepo accountRepo;

  @GetMapping("/about/balance")
  public String getCurrentBalance(@RequestParam String email) {

    var account = accountRepo.findByEmail(email);
    if (account != null) {
      return account.getBalance();
    } else {
      return "0.00";
    }
  }
}
