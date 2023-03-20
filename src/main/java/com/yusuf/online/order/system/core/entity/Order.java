package com.yusuf.online.order.system.core.entity;


import com.yusuf.online.order.system.core.enums.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Order extends BaseEntity<String> {

  @Id
  @GeneratedValue
  @Column(name = "ID")
  private Integer id;
  @Column(name = "productId")
  private Integer productId;
  @Column(name = "QUANTITY")
  private Long quantity;

  @Enumerated(EnumType.STRING)
  @Column(name = "SELLER_ID")
  private OrderStatus orderStatus;


}
