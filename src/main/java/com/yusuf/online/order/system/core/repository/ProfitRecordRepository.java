package com.yusuf.online.order.system.core.repository;

import com.yusuf.online.order.system.core.entity.ProfitRecord;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfitRecordRepository extends JpaRepository<ProfitRecord, Integer> {


  @Query(value = """
      select 
      new com.yusuf.online.order.system.core.entity.ProfitRecord(u.id,sum(o.quantity))
      from Order o
      left join Product p on o.productId = p.id
      left join User  u on p.sellerId = u.id
      where o.orderStatus = 'DELIVERED'
      and (o.lastModifiedDate
      between :startDateTime and :endDateTime)
      group by p.sellerId
      """)
  List<ProfitRecord> calculateSellersProfitByDates(@Param("startDateTime") LocalDateTime startDateTime,
      @Param("endDateTime") LocalDateTime endDateTime);


}
