package org.superwindcloud.beauti_store.services;

import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.superwindcloud.beauti_store.dao.Account;
import org.superwindcloud.beauti_store.dao.Goods;
import org.superwindcloud.beauti_store.repository.AccountRepo;
import org.superwindcloud.beauti_store.repository.GoodsRepo;

@Service
@Data
@RequiredArgsConstructor
public final class GoodsService {

  private final AccountRepo accountRepo;

  private final GoodsRepo goodsRepo;

  public final int initGoodsForAllAccounts() {
    List<Account> accounts = accountRepo.findAll();
    int createdCount = 0;

    for (Account account : accounts) {
      Goods goods = goodsRepo.findByEmail(account.getEmail());

      if (goods == null) {
        // 新建
        goods = new Goods();
        goods.setEmail(account.getEmail());
      }

      // ✅ 不管新建还是已有的，统一设置正确值
      goods.setSteamUnitPrice(2.01);
      goods.setNeteasecloudPrice(4.01);
      goods.setJbIdePrice(5.41);
      goods.setMeituanPrice(3.01);
      goods.setPiaoPrice(10.01);
      goods.setGirlPrice(1.33);

      goodsRepo.save(goods);
      createdCount++;
    }

    return createdCount;
  }
}
