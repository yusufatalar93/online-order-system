package com.yusuf.online.order.system.core.model.dto;

import com.yusuf.online.order.system.core.model.dto.base.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDTO extends BaseDTO<String> {


  private Integer id;
  @NotBlank
  private String name;
  @NotBlank
  private String description;
  @NotNull
  private Long quantity;

  private Integer sellerId;

}
