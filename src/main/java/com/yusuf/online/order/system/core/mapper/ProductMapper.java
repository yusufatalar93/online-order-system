package com.yusuf.online.order.system.core.mapper;

import com.yusuf.online.order.system.core.entity.Product;
import com.yusuf.online.order.system.core.model.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface ProductMapper extends BaseMapper<Product, ProductDTO> {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "version", ignore = true)
  @Mapping(target = "createdBy", ignore = true)
  @Mapping(target = "creationDate", ignore = true)
  @Mapping(target = "sellerId", ignore = true)
  void updateEntity(ProductDTO source, @MappingTarget Product target);

}


