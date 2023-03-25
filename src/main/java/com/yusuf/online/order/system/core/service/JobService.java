package com.yusuf.online.order.system.core.service;

import com.yusuf.online.order.system.core.enums.OrderStatus;
import com.yusuf.online.order.system.core.model.dto.OrderDTO;
import com.yusuf.online.order.system.core.service.base.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class JobService {

  private final OrderService orderService;

  @Scheduled(cron = "0 * * * * *")
  public void deliverOrders() {
    orderService.deliverOrdersRandomly();

  }
}
