package com.yusuf.online.order.system.core.repository;

import com.yusuf.online.order.system.core.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Integer> {


  Optional<Product> findByNameAndSellerId(String name, Integer sellerId);

  boolean existsByName(String name);


  @Query(value = """
      select p from Product p
      where (:name is null  or :name=p.name) 
      and (:description is null  or :description=p.description)
      """)
  List<Product> getProductsByNameAndDescription(@Param("name") String name,
      @Param("description") String description);


}
