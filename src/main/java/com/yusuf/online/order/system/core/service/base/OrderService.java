package com.yusuf.online.order.system.core.service.base;

import com.yusuf.online.order.system.core.model.dto.OrderDTO;
import com.yusuf.online.order.system.core.entity.ProfitRecord;
import java.time.LocalDate;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {

  OrderDTO createOrder(OrderDTO orderDTO);

  void cancelOrder(Integer orderId);

  List<OrderDTO> getCurrentUserOrders();

  @Transactional
  void acceptOrder(Integer orderId);

  @Transactional
  void rejectOrder(Integer orderId);


  void deliverOrdersRandomly();

}
