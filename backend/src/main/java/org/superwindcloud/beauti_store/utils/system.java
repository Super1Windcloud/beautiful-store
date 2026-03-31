package org.superwindcloud.beauti_store.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class system {
  public static List<String> readGirlImages() throws IOException {
    List<String> imageNames = new ArrayList<>();

    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    URL url = classLoader.getResource("static/assets");
    if (url == null) {
      throw new FileNotFoundException("找不到 static/assets 目录");
    }

    File folder = new File(url.getFile());
    File[] files = folder.listFiles();

    if (files != null) {
      Pattern pattern = Pattern.compile("girl.*\\.jpg", Pattern.CASE_INSENSITIVE);
      for (File file : files) {
        if (file.isFile() && pattern.matcher(file.getName()).matches()) {
          imageNames.add("static/assets/" + file.getName());
        }
      }
    }

    return imageNames;
  }
}
