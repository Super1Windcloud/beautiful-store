package org.superwindcloud.beauti_store.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.superwindcloud.beauti_store.services.GoodsService;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
@RequiredArgsConstructor
public class UpdateDataBase {

  private GoodsService goodsService;

  // 初始化所有没有 Goods 的用户
  @PostMapping("/init-goods")
  public String initGoodsForExistingAccounts() {
    var count = goodsService.initGoodsForAllAccounts();
    return "成功为 " + count + " 个用户初始化了 Goods 数据";
  }
}
