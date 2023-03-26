package com.yusuf.online.order.system.core.model.dto;

import com.yusuf.online.order.system.core.enums.OrderStatus;
import com.yusuf.online.order.system.core.model.dto.base.BaseDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderDTO extends BaseDTO<String> {


  private Integer id;
  @NotNull
  private Integer productId;
  @NotNull
  private Long quantity;
  private OrderStatus orderStatus;


}
