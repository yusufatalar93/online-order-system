package com.yusuf.online.order.system.core.service;

import com.yusuf.online.order.system.core.service.util.Messages;
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
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
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
      final String errorMessage =Messages.getMessageForLocale("product.exist.exception");
      log.error("An error occurred while creating the product. Error message : {}", errorMessage);
      throw new EntityExistsException(errorMessage);
    }
    final Product product = productMapper.convertToEntity(productDTO);
    product.setSellerId(currentUserId);
    final Product savedProduct = repository.save(product);
    log.info("{} - {} product  is created", savedProduct.getSellerId(), savedProduct.getName());
    return productMapper.convertToDTO(savedProduct);
  }

  @Override
  public ProductDTO update(ProductDTO productDTO) {
    final Integer currentUserId = userService.getCurruntUser().getId();
    final Product product = repository.findById(productDTO.getId()).map(p -> {
      if (!Objects.equals(currentUserId, p.getSellerId())) {
        final String errorMessage = Messages.getMessageForLocale("product.owner.exception");
        log.error("An error occurred while updating the product. Error message : {}", errorMessage);
        throw new IllegalArgumentException(errorMessage);
      }
      productMapper.updateEntity(productDTO, p);
      return p;
    }).orElseThrow(() -> {
      final String messageForLocale = Messages.getMessageForLocale("product.not.found.exception");
      log.error("An error occurred while updating the product. Error message : {}", messageForLocale);
      throw  new EntityNotFoundException(
          String.format(messageForLocale, productDTO.getId()));
    });
    final Product savedProduct = repository.save(product);
    log.info("{} - {} product  is updated", savedProduct.getSellerId(), savedProduct.getName());
    return productMapper.convertToDTO(savedProduct);
  }

  @Override
  public void deleteByProductId(Integer id) {
    final Integer currentUserId = userService.getCurruntUser().getId();
    final Product product = repository.findById(id)
        .orElseThrow(() -> {
          final String messageForLocale = Messages.getMessageForLocale("product.not.found.exception");
         throw  new EntityNotFoundException(
              String.format(messageForLocale, id));
        });
    if (!Objects.equals(currentUserId, product.getSellerId())) {
      final String errorMessage = Messages.getMessageForLocale("product.owner.exception");
      log.error("An error occurred while deleting the product. Error message : {}", errorMessage);
      throw new IllegalArgumentException(errorMessage);
    }
    repository.delete(product);
    log.info("{} - {} product  is deleted", product.getSellerId(), product.getName());
  }

  @Override
  public List<ProductDTO> getProductsByNameAndDescription(ProductListRequest request) {
    final List<Product> productsByNameAndDescription = repository.getProductsByNameAndDescription(
        request.getName(), request.getDescription());
    return productMapper.convertAllToDTO(productsByNameAndDescription);
  }

  @Override
  public ProductDTO getProductById(Integer id) {

    final Product product = repository.findById(id).orElseThrow(
        () -> {
          final String messageForLocale = Messages.getMessageForLocale("product.not.found.exception");
          throw new EntityNotFoundException(String.format(messageForLocale, id));
        });
    return productMapper.convertToDTO(product);
  }


}
