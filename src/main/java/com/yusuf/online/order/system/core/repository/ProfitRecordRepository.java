package com.yusuf.online.order.system.core.repository;

import com.yusuf.online.order.system.core.entity.Product;
import com.yusuf.online.order.system.core.entity.ProfitRecord;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfitRecordRepository extends JpaRepository<ProfitRecord, Integer> {


  @Query(value = """
      select 
      u.id as seller,
      :startDateTime as  date,
      sum(o.quantity) as profit 
      from Order o
      left join Product p on o.productId = p.id
      left join User  u on p.sellerId = u.id
      where o.orderStatus = 'DELIVERED'
      and o.lastModifiedDate
      between :startDateTime and :endDateTime
      group by p.sellerId
      """)
  List<ProfitRecord> calculateSellersProfit(@Param("startDateTime") LocalDateTime startDateTime,
      @Param("endDateTime") LocalDateTime endDateTime);


}
