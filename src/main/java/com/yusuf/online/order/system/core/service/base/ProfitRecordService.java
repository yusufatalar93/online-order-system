package com.yusuf.online.order.system.core.service.base;

import com.yusuf.online.order.system.core.entity.ProfitRecord;
import java.time.LocalDate;
import java.util.List;

public interface ProfitRecordService {


  List<ProfitRecord> calculateAndRecordSellersDailyProfit();
}
