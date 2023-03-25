package com.yusuf.online.order.system.core.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(uniqueConstraints={
    @UniqueConstraint(columnNames = {"NAME", "SELLER_ID"})
})
public class Product extends BaseEntity<String> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Integer id;
  @Column(name = "NAME")
  private String name;
  @Column(name = "DESCRIPTION")
  private String description;
  @Column(name = "QUANTITY")
  private Long quantity;
  @Column(name = "SELLER_ID",updatable = false)
  private Integer sellerId;


}
