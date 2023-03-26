package com.yusuf.online.order.system.core.repository;

import com.yusuf.online.order.system.core.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {

  boolean existsByNameAndSellerId(String name,Integer sellerId);


  @Query(value = """
      select p from Product p
      where (:name is null  or :name=p.name) 
      and (:description is null  or :description=p.description)
      and p.quantity > 0
      """)
  List<Product> getProductsByNameAndDescription(@Param("name") String name,
      @Param("description") String description);


}
