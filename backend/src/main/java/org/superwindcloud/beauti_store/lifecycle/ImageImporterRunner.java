package org.superwindcloud.beauti_store.lifecycle;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.superwindcloud.beauti_store.repository.GirlPhotoRepository;
import org.superwindcloud.beauti_store.services.GirlPhotoService;

@Component
@Data
@RequiredArgsConstructor
public class ImageImporterRunner implements ApplicationRunner {

  private final GirlPhotoRepository girlPhotoRepository;

  private final GirlPhotoService service;

  @Override
  public void run(ApplicationArguments args) {
    if (girlPhotoRepository.count() == 0) {
      try {
        service.saveAllGirlImages();
        System.out.println("图片已成功导入数据库");
      } catch (Exception e) {
        e.printStackTrace();
        System.err.println("图片导入失败：" + e.getMessage());
      }
    } else if (!girlPhotoRepository.findByContentTypeIsNull().isEmpty()) {
      var nullPhotos = girlPhotoRepository.findByContentTypeIsNull();
      nullPhotos.forEach(
          photo -> {
            photo.setContentType("image/jpeg");
          });
      girlPhotoRepository.saveAll(nullPhotos);
      System.out.println("图片已成功导入数据库");
    } else {
      System.out.println("数据库已有图片，跳过导入");
    }
  }
}
