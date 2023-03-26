package com.yusuf.online.order.system.core.repository;

import com.yusuf.online.order.system.core.entity.Order;
import com.yusuf.online.order.system.core.enums.OrderStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

  List<Order> findAllByCreatedBy(String orderOwner);

  List<Order> findAllByOrderStatus(OrderStatus orderStatus);


}
