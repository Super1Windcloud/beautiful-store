package org.superwindcloud.beauti_store;

import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.superwindcloud.beauti_store.dao.Account;
import org.superwindcloud.beauti_store.dao.GirlPhoto;
import org.superwindcloud.beauti_store.dao.Goods;
import org.superwindcloud.beauti_store.repository.AccountRepo;
import org.superwindcloud.beauti_store.repository.GirlPhotoRepository;
import org.superwindcloud.beauti_store.repository.GoodsRepo;
import org.superwindcloud.beauti_store.services.AccountService;
import org.superwindcloud.beauti_store.services.GoodsService;

@Data
@SpringBootTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class BeautiStoreApplicationTests {

  private final GoodsService goodsService;

  private final GoodsRepo goodsRepo;

  private final AccountRepo accountRepo;

  private final GirlPhotoRepository grilRepo;

  private final AccountService accountService;

  @Test
  public void testInitGoodsForAllAccounts() {
    var count = goodsService.initGoodsForAllAccounts();
    System.out.println(count);
    var goods = goodsRepo.findAll();
    for (Goods good : goods) {
      System.out.println(good);
    }
  }

  @Autowired private final JdbcTemplate jdbcTemplate;

  @Test
  public void printAllTables() {
    List<String> tableNames =
        jdbcTemplate.queryForList(
            "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'",
            String.class);

    System.out.println("📦 数据库中的所有表：");
    for (String table : tableNames) {
      System.out.println(" - " + table);
      try {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM " + table);

        for (Map<String, Object> row : rows) {
          System.out.println("  🔹 " + row);
        }

        if (rows.isEmpty()) {
          System.out.println("  ⚠️ 无数据");
        }
      } catch (Exception e) {
        System.out.println("  ❌ 查询失败: " + e.getMessage());
      }
    }
  }

  @Test
  public final void printAccountInfos() {
    List<Account> accounts = accountRepo.findAll();
    for (Account account : accounts) {
      System.out.println("User Account: " + account);
    }
  }

  @Test
  public final void printGrilImgs() {
    List<GirlPhoto> grilPhotos = grilRepo.findAll();
    for (GirlPhoto grilPhoto : grilPhotos) {
      System.out.println("GirlPhoto: " + grilPhoto);
    }
  }

  @Test
  public final void updateDao() {
    accountService.fillMissingCreatedDate();
    accountService.initAccountBalance();
    this.printAccountInfos();
  }
}
