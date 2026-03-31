package org.superwindcloud.beauti_store.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.superwindcloud.beauti_store.dao.GirlPhoto;
import org.superwindcloud.beauti_store.repository.GirlPhotoRepository;

@Service
@Data
@RequiredArgsConstructor
public class GirlPhotoService {

  private final GirlPhotoRepository repository;

  public final void saveAllGirlImages() throws IOException {
    Path assetPath = new ClassPathResource("assets").getFile().toPath();
    Pattern pattern = Pattern.compile("girl.*\\.jpg", Pattern.CASE_INSENSITIVE);

    Files.walk(assetPath)
        .filter(Files::isRegularFile)
        .filter(
            path -> {
              Path fileName = path.getFileName();
              return fileName != null && pattern.matcher(fileName.toString()).matches();
            })
        .forEach(
            path -> {
              try {
                Path fileName = path.getFileName();
                if (fileName == null) {
                  return;
                }
                GirlPhoto photo = new GirlPhoto();
                photo.setName(fileName.toString());
                photo.setData(Files.readAllBytes(path));
                photo.setContentType("image/jpeg");
                repository.save(photo);
              } catch (IOException e) {
                e.printStackTrace();
              }
            });
  }
}
