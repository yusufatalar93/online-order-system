package com.yusuf.online.order.system.core.service;

import com.yusuf.online.order.system.core.entity.Product;
import com.yusuf.online.order.system.core.mapper.ProductMapper;
import com.yusuf.online.order.system.core.model.dto.ProductDTO;
import com.yusuf.online.order.system.core.model.dto.UserDTO;
import com.yusuf.online.order.system.core.model.request.ProductListRequest;
import com.yusuf.online.order.system.core.repository.ProductRepository;
import com.yusuf.online.order.system.core.service.base.ProductService;
import com.yusuf.online.order.system.core.service.base.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpleProductService implements ProductService {


  private final ProductRepository repository;
  private final ProductMapper productMapper;
  private final UserService userService;


  @Override
  public ProductDTO save(ProductDTO productDTO) {
    final String productName = productDTO.getName();
    if (repository.existsByName(productName)) {
      throw new EntityExistsException(
          String.format("%s isimli ürün zaten mevcut. Sadece güncelleme yapabilirsiniz",
              productName));
    }
    final Integer currentUserId = userService.getCurruntUser().getId();
    final Product product = productMapper.convertToEntity(productDTO);
    product.setSellerId(currentUserId);
    final Product savedProduct = repository.save(product);
    return productMapper.convertToDTO(savedProduct);
  }

  @Override
  public ProductDTO update(ProductDTO productDTO) {
    final UserDTO currentUser = userService.getCurruntUser();
    final String productName = productDTO.getName();
    Product product = repository.findByNameAndSellerId(productName, currentUser.getId())
        .orElseThrow(() -> new EntityNotFoundException(
            String.format("%s'ye ait %s ürünü bulunamadığından güncelleme yapılamadı.",
                currentUser.getEmail(), productName)));
    productMapper.updateEntity(productDTO, product);
    final Product savedProduct = repository.save(product);
    return productMapper.convertToDTO(savedProduct);
  }

  @Override
  public void deleteByProductId(String productName) {
    final UserDTO currentUser = userService.getCurruntUser();
    final Product product = repository.findByNameAndSellerId(productName, currentUser.getId())
        .orElseThrow(() -> new EntityNotFoundException(
            String.format("%s 'e ait %s ürünü bulunamadığından silme işlemi gerçekleştirilemedi.",
                currentUser.getEmail(),
                productName)));
    repository.delete(product);
  }

  @Override
  public List<ProductDTO> getProductsByNameAndDescription(ProductListRequest request) {
    final List<Product> productsByNameAndDescription = repository.getProductsByNameAndDescription(
        request.getProductName(), request.getDescription());
    return productMapper.convertAllToDTO(productsByNameAndDescription);
  }
}
