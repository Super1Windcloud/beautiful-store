package org.superwindcloud.beauti_store.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.superwindcloud.beauti_store.dao.Account;

// 数据库接口
@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
  Account findByEmail(String email);

  Account findByBalance(String balance);

  Account findByUsername(String username);

  List<Account> findAllByPassword(String password);

  List<Account> findAllByLoginType(String type);
}
