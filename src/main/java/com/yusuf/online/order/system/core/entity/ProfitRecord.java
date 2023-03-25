package com.yusuf.online.order.system.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints={
    @UniqueConstraint(columnNames = {"SELLER", "DATE"})
})
public class ProfitRecord extends BaseEntity<String> {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "SELLER")
  private Integer seller;
  @Column(name = "DATE")
  private LocalDateTime date;
  @Column(name = "TOTAL")
  private Long total;

}
