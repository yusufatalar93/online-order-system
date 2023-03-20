package com.yusuf.online.order.system.core.service;

import com.yusuf.online.order.system.core.entity.Order;
import com.yusuf.online.order.system.core.enums.OrderStatus;
import com.yusuf.online.order.system.core.mapper.OrderMapper;
import com.yusuf.online.order.system.core.model.dto.OrderDTO;
import com.yusuf.online.order.system.core.model.dto.ProductDTO;
import com.yusuf.online.order.system.core.model.dto.UserDTO;
import com.yusuf.online.order.system.core.repository.OrderRepository;
import com.yusuf.online.order.system.core.service.base.OrderService;
import com.yusuf.online.order.system.core.service.base.ProductService;
import com.yusuf.online.order.system.core.service.base.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SimpleOrderService implements OrderService {


  private final OrderRepository repository;
  private final OrderMapper orderMapper;
  private final ProductService productService;


  private final UserService userService;


  @Override
  public OrderDTO createOrder(OrderDTO orderDTO) {
    final ProductDTO product = productService.getProductById(orderDTO.getProductId());
    if (product.getQuantity() < orderDTO.getQuantity()) {
      throw new IllegalArgumentException(
          String.format("Yeterli miktarda ürün yok. Talep edilen : %s, Mevcut : %s",
              orderDTO.getQuantity(), product.getQuantity()));
    }
    final Order order = orderMapper.convertToEntity(orderDTO);
    order.setOrderStatus(OrderStatus.CREATED);
    final Order savedOrder = repository.save(order);
    return orderMapper.convertToDTO(savedOrder);
  }

  @Override
  public void cancelOrder(Integer orderId) {
    Order order = repository.findById(orderId).orElseThrow(
        () -> new EntityNotFoundException(String.format("%s ID'ye ait sipariş bulunamadı!")));

    if (order.getOrderStatus().equals(OrderStatus.CREATED)) {
      order.setOrderStatus(OrderStatus.CANCELLED);
      repository.save(order);
    } else {
      throw new RuntimeException(
          String.format("Bu sipariş iptal edilemez. Çünkü sipraişi statüsü %s 'dir",
              order.getOrderStatus()));
    }
  }

  @Override
  public List<OrderDTO> getCurrentUserOrders() {
    final UserDTO currentUser = userService.getCurruntUser();
    final List<Order> myOrders = repository.findAllByCreatedBy(currentUser.getEmail());
    return orderMapper.convertAllToDTO(myOrders);
  }

  @Transactional
  @Override
  public void acceptOrder(Integer orderId) {
    Order order = repository.findById(orderId).orElseThrow(
        () -> new EntityNotFoundException(String.format("%s ID'ye ait sipariş bulunamadı!")));
    if (order.getOrderStatus().equals(OrderStatus.CREATED)) {
      order.setOrderStatus(OrderStatus.ACCEPTED);
      ProductDTO product = productService.getProductById(order.getProductId());
      final long productQuantity = product.getQuantity();
      final long orderQuantity = order.getQuantity();
      if (orderQuantity > productQuantity) {
        throw new IllegalArgumentException(
            String.format("Yeterli miktarda ürün yok. Talep edilen : %s, Mevcut : %s",
                orderQuantity, productQuantity));
      } else {
        product.setQuantity(productQuantity - orderQuantity);
        productService.update(product);
      }
    } else {
      throw new RuntimeException(
          String.format("Bu sipariş kabul edilemez. Çünkü sipraişi statüsü %s 'dir",
              order.getOrderStatus()));
    }
  }

  @Override
  public void rejectOrder(Integer orderId) {
    Order order = repository.findById(orderId).orElseThrow(
        () -> new EntityNotFoundException(String.format("%s ID'ye ait sipariş bulunamadı!")));

    if (order.getOrderStatus().equals(OrderStatus.CREATED)) {
      order.setOrderStatus(OrderStatus.REJECTED);
      repository.save(order);
    } else {
      throw new RuntimeException(
          String.format("Bu sipariş reddedilemez. Çünkü sipraişi statüsü %s 'dir",
              order.getOrderStatus()));
    }
  }

}
