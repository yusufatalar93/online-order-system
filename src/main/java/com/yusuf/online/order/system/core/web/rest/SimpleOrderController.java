package com.yusuf.online.order.system.core.web.rest;

import com.yusuf.online.order.system.core.model.dto.OrderDTO;
import com.yusuf.online.order.system.core.service.base.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class SimpleOrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
    return ResponseEntity.ok(orderService.createOrder(orderDTO));
  }

  @GetMapping("/cancel-order/{orderId}")
  public void cancelOrder(@PathVariable Integer orderId) {
    orderService.cancelOrder(orderId);
  }

  @GetMapping("/accept-order/{orderId}")
  public void acceptOrder(@PathVariable Integer orderId) {
    orderService.acceptOrder(orderId);
  }

  @GetMapping("/reject-order/{orderId}")
  public void rejectOrder(@PathVariable Integer orderId) {
    orderService.rejectOrder(orderId);
  }

  @GetMapping
  public ResponseEntity<List<OrderDTO>> getMyOrders() {
    return ResponseEntity.ok(orderService.getCurrentUserOrders());
  }


}
