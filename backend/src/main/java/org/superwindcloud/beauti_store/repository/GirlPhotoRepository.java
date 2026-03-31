package org.superwindcloud.beauti_store.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.superwindcloud.beauti_store.dao.GirlPhoto;

@Repository
public interface GirlPhotoRepository extends JpaRepository<GirlPhoto, Long> {

  @Transactional(readOnly = true) // 开启只读事务
  List<GirlPhoto> findByContentTypeIsNull();
}
