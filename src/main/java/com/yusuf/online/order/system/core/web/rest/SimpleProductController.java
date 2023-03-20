package com.yusuf.online.order.system.core.web.rest;

import com.yusuf.online.order.system.core.model.dto.ProductDTO;
import com.yusuf.online.order.system.core.service.base.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
  public ResponseEntity<ProductDTO> save(@RequestBody ProductDTO productDTO) {
    return ResponseEntity.ok(productService.save(productDTO));
  }
  @PutMapping
  public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO productDTO) {
    return ResponseEntity.ok(productService.update(productDTO));
  }

  @DeleteMapping("/{productId}")
  public void delete(@PathVariable String productName) {
    productService.deleteByProductId(productName);
  }



}
