package com.yusuf.online.order.system.core.service.base;

import com.yusuf.online.order.system.core.model.dto.ProductDTO;

public interface ProductService {

  ProductDTO save(ProductDTO productDTO);

  ProductDTO update(ProductDTO productDTO);

  void deleteByProductId(String productName);
}
