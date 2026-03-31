package org.superwindcloud.beauti_store.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
@Data
@RequiredArgsConstructor
public class Email {

  private final MailSender mailSender;

  public void sendVerificationCode(String email, String code) {
    var simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setFrom("1178933440@qq.com");
    simpleMailMessage.setTo(email);
    System.out.println("发送到 :" + email);
    simpleMailMessage.setSubject("beauti_store registration by superwindcloud");
    simpleMailMessage.setText("您好，您的beauti_store 注册验证码是：" + code + "，5分钟内有效。");

    mailSender.send(simpleMailMessage);
  }
}
