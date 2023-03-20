package com.yusuf.online.order.system.core.service.base;

import com.yusuf.online.order.system.core.model.dto.ProductDTO;
import com.yusuf.online.order.system.core.model.request.ProductListRequest;
import java.util.List;

public interface ProductService {

  ProductDTO save(ProductDTO productDTO);

  ProductDTO update(ProductDTO productDTO);

  void deleteByProductId(String productName);

  List<ProductDTO> getProductsByNameAndDescription(ProductListRequest request);

  ProductDTO getProductById(Integer id);

}
