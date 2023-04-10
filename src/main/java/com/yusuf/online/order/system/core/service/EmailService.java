package com.yusuf.online.order.system.core.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class EmailService {

  private final JavaMailSender javaMailSender;

  @Async
  public void sendEmail(SimpleMailMessage email) {
    javaMailSender.send(email);
  }

}
