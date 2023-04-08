package com.yusuf.online.order.system.core.service;

import com.yusuf.online.order.system.core.service.base.OrderService;
import com.yusuf.online.order.system.core.service.base.ProfitRecordService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class JobService {

  private final OrderService orderService;

  private final ProfitRecordService profitRecordService;


  @Scheduled(cron = "0 * * * * *")
  public void deliverOrders() {
    log.info("DeliverOrder job started. StartTime : {}", LocalDateTime.now());
    orderService.deliverOrdersRandomly();
    log.info("DeliverOrder job finished. EndTime : {}", LocalDateTime.now());

  }

  @Scheduled(cron = "0 15 0 * * *")
  public void calculateAndRecordSellersDailyProfit() {
    log.info("calculateAndRecordSellersDailyProfit job started. StartTime : {}", LocalDateTime.now());
    profitRecordService.calculateAndRecordSellersDailyProfit();
    log.info("calculateAndRecordSellersDailyProfit job finished. EndTime : {}", LocalDateTime.now());

  }


}
