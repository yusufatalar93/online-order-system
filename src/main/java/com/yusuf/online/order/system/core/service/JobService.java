package com.yusuf.online.order.system.core.service;

import com.yusuf.online.order.system.core.service.base.OrderService;
import com.yusuf.online.order.system.core.service.base.ProfitRecordService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class JobService {

  private final OrderService orderService;

  private final ProfitRecordService profitRecordService;


  @Scheduled(cron = "0 * * * * *")
  public void deliverOrders() {
    orderService.deliverOrdersRandomly();

  }

  @Scheduled(cron = "0 15 0 * * *")
  public void calculateAndRecordSellersDailyProfit() {
    profitRecordService.calculateAndRecordSellersDailyProfit();
  }


}
