package org.superwindcloud.beauti_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.superwindcloud.beauti_store.dao.Goods;

@Repository
public interface GoodsRepo extends JpaRepository<Goods, Long> {

  Goods findByEmail(String email);
}
