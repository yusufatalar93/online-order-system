package com.yusuf.online.order.system.core.service;

import com.yusuf.online.order.system.core.entity.Product;
import com.yusuf.online.order.system.core.mapper.ProductMapper;
import com.yusuf.online.order.system.core.model.dto.ProductDTO;
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
    final Integer currentUserId = userService.getCurruntUser().getId();
    if (repository.existsByNameAndSellerId(productDTO.getName(), currentUserId)) {
      throw new EntityExistsException(
          String.format("%s ürünü için ürün kaydı mevcuttur. Yeni kayıt atılamaz.",
              productDTO.getName()));
    }
    final Product product = productMapper.convertToEntity(productDTO);
    product.setSellerId(currentUserId);
    final Product savedProduct = repository.save(product);
    return productMapper.convertToDTO(savedProduct);
  }

  @Override
  public ProductDTO update(ProductDTO productDTO) {
    final Product product = repository.findById(productDTO.getId()).map(p -> {
      productMapper.updateEntity(productDTO, p);
      return p;
    }).orElseThrow(() -> new EntityNotFoundException(
        String.format("%s ID'ye ait ürün kaydı bulunamadığından güncelleme yapılamadı.",
            productDTO.getId())));
    final Product savedProduct = repository.save(product);
    return productMapper.convertToDTO(savedProduct);
  }

  @Override
  public void deleteByProductId(Integer id) {
    final Product product = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(
            String.format(
                "%s ID'ye ait ürün kaydı bulunamadığından silme işlemi gerçekleştirilemedi.", id)));
    repository.delete(product);
  }

  @Override
  public List<ProductDTO> getProductsByNameAndDescription(ProductListRequest request) {
    final List<Product> productsByNameAndDescription = repository.getProductsByNameAndDescription(
        request.getProductName(), request.getDescription());
    return productMapper.convertAllToDTO(productsByNameAndDescription);
  }

  @Override
  public ProductDTO getProductById(Integer id) {
    final Product product = repository.findById(id).orElseThrow(
        () -> new EntityNotFoundException(String.format("%s ID'ye ait ürün bulunamadı!")));
    return productMapper.convertToDTO(product);
  }


}
