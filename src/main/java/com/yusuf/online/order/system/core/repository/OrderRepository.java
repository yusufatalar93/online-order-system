package com.yusuf.online.order.system.core.repository;

import com.yusuf.online.order.system.core.entity.Order;
import com.yusuf.online.order.system.core.entity.Product;
import com.yusuf.online.order.system.core.enums.OrderStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Integer> {

  List<Order> findAllByCreatedBy(String orderOwner);

  List<Order> findAllByOrderStatus(OrderStatus orderStatus);


}
