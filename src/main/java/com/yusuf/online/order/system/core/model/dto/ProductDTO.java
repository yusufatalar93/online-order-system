package com.yusuf.online.order.system.core.model.dto;

import com.yusuf.online.order.system.core.model.dto.base.BaseDTO;
import lombok.Data;

@Data
public class ProductDTO extends BaseDTO<String> {


  private Integer id;
  private String name;
  private String description;
  private Long quantity;

  private Integer sellerId;

}
