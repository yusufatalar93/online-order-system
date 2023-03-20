package com.yusuf.online.order.system.core.model.dto;

import com.yusuf.online.order.system.core.enums.OrderStatus;
import com.yusuf.online.order.system.core.model.dto.base.BaseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class OrderDTO extends BaseDTO<String> {


  private Integer id;
  private Integer productId;
  private Long quantity;
  private OrderStatus orderStatus;


}
