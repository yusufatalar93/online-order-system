package com.yusuf.online.order.system.core.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductListRequest {

  private String name;
  private String description;


}
