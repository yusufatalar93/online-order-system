package com.yusuf.online.order.system.core.service;

import com.yusuf.online.order.system.core.entity.ProfitRecord;
import com.yusuf.online.order.system.core.repository.ProfitRecordRepository;
import com.yusuf.online.order.system.core.service.base.ProfitRecordService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SimpleProfitRecordService implements ProfitRecordService {


  private final ProfitRecordRepository repository;

  @Override
  public void calculateAndRecordSellersDailyProfit() {
    final LocalDate yesterday = LocalDate.now().minusDays(1);
    final LocalDateTime startDateTime = yesterday.atStartOfDay();
    final LocalDateTime endDateTime = yesterday.atTime(LocalTime.MAX);
    final List<ProfitRecord> profitRecords = repository.calculateSellersProfitByDates(startDateTime,
        endDateTime);
    repository.saveAll(profitRecords);
  }

}
