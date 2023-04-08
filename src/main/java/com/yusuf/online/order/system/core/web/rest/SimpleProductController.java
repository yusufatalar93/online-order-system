package com.yusuf.online.order.system.core.web.rest;

import com.yusuf.online.order.system.core.model.dto.ProductDTO;
import com.yusuf.online.order.system.core.model.request.ProductListRequest;
import com.yusuf.online.order.system.core.service.base.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class SimpleProductController {

  private final ProductService productService;


  @PostMapping
  public ResponseEntity<ProductDTO> save(@Valid @RequestBody ProductDTO productDTO) {
    return ResponseEntity.ok(productService.save(productDTO));
  }
  @PutMapping
  public ResponseEntity<ProductDTO> update(@Valid @RequestBody ProductDTO productDTO) {
    return ResponseEntity.ok(productService.update(productDTO));
  }

  @DeleteMapping("/{productId}")
  public void delete(@PathVariable Integer productId) {
    productService.deleteByProductId(productId);
  }

  @GetMapping("/{productId}")
  public ProductDTO get(@PathVariable Integer productId) {
    return productService.getProductById(productId);
  }
  @PostMapping("/get-by-name-and-description")
  public List<ProductDTO> getProductsByNameAndDescription(@RequestBody ProductListRequest request) {
   return productService.getProductsByNameAndDescription(request);
  }


}
