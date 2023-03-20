package com.yusuf.online.order.system.core.repository;

import com.yusuf.online.order.system.core.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {


  Optional<Product> findByNameAndSellerId(String name,Integer sellerId);

  boolean existsByName(String name);
}
