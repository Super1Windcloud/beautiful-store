package org.superwindcloud.beauti_store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Index {
  @GetMapping("/")
  public String indexEntry() {
    return "forward:index.html";
  }
}
