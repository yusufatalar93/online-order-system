package com.yusuf.online.order.system.core.service;

import com.yusuf.online.order.system.core.config.Messages;
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
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleOrderService implements OrderService {


  private final OrderRepository repository;
  private final OrderMapper orderMapper;
  private final ProductService productService;
  private final UserService userService;

  @Value("${RANDOM_RATE}")
  private Double randomRate;


  @Override
  public OrderDTO createOrder(OrderDTO orderDTO) {
    final ProductDTO product = productService.getProductById(orderDTO.getProductId());
    if (product.getQuantity() < orderDTO.getQuantity()) {
      final String errorMessage = String.format(
          Messages.getMessageForLocale("not.enough.product.exception"),
          orderDTO.getQuantity(), product.getQuantity());
      log.error("An error occurred while creating the order. Error message : {}", errorMessage);
      throw new IllegalArgumentException(errorMessage);
    }
    final Order order = orderMapper.convertToEntity(orderDTO);
    order.setOrderStatus(OrderStatus.CREATED);
    final Order savedOrder = repository.save(order);
    log.info("Order with {} id is created", savedOrder.getId());
    return orderMapper.convertToDTO(savedOrder);
  }

  @Override
  public void cancelOrder(Integer orderId) {
    Order order = repository.findById(orderId).orElseThrow(
        () -> {
          final String errorMessage = Messages.getMessageForLocale("order.not.found.exception");
          log.error("An error occurred while canceling the order. Error message : {}",
              errorMessage);
          throw new EntityNotFoundException(
              String.format(errorMessage, orderId));
        });

    if (order.getOrderStatus().equals(OrderStatus.CREATED)) {
      order.setOrderStatus(OrderStatus.CANCELLED);
      repository.save(order);
      log.info("Order with {} id is cancelled", order.getId());
    } else {
      final String errorMessage = String.format(
          Messages.getMessageForLocale("order.can.not.be.canceled.exception"),
          order.getOrderStatus());
      log.error("An error occurred while canceling the order. Error message {}", errorMessage);
      throw new RuntimeException(errorMessage);
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
        () -> {
          final String errorMessage = Messages.getMessageForLocale("order.not.found.exception");
          log.error("An error occurred while accepting the order. Error message : {}",
              errorMessage);
          throw new EntityNotFoundException(
              String.format(errorMessage, orderId));
        });
    if (order.getOrderStatus().equals(OrderStatus.CREATED)) {
      ProductDTO product = productService.getProductById(order.getProductId());
      final long productQuantity = product.getQuantity();
      final long orderQuantity = order.getQuantity();
      if (orderQuantity > productQuantity) {
        final String errorMessage = String.format(
            Messages.getMessageForLocale("not.enough.product.exception"),
            orderQuantity, productQuantity);
        log.error("An error occurred while accepting the order. Error message : {}", errorMessage);
        throw new IllegalArgumentException(errorMessage);
      } else {
        order.setOrderStatus(OrderStatus.ACCEPTED);
        product.setQuantity(productQuantity - orderQuantity);
        productService.update(product);
        log.info("Order with {} id is accepted", order.getId());
      }
    } else {
      final String errorMessage = String.format(
          Messages.getMessageForLocale("order.can.not.be.accepted.exception"),
          order.getOrderStatus());
      log.error("An error occurred while accepting the order. Error message {}", errorMessage);
      throw new RuntimeException(errorMessage);
    }
  }

  @Override
  public void rejectOrder(Integer orderId) {
    Order order = repository.findById(orderId).orElseThrow(
        () -> {
          final String errorMessage = Messages.getMessageForLocale("order.not.found.exception");
          log.error("An error occurred while rejecting the order. Error message : {}",
              errorMessage);
          throw new EntityNotFoundException(
              String.format(errorMessage, orderId));
        });
    if (order.getOrderStatus().equals(OrderStatus.CREATED)) {
      order.setOrderStatus(OrderStatus.REJECTED);
      repository.save(order);
      log.info("Order with {} id is rejected", order.getId());
    } else {
      final String errorMessage = String.format(
          Messages.getMessageForLocale("order.can.not.be.rejected.exception"),
          order.getOrderStatus());
      log.error("An error occurred while rejecting the order. Error message {}", errorMessage);
      throw new RuntimeException(errorMessage);
    }
  }


  @Transactional
  @Override
  public void deliverOrdersRandomly() {
    List<Order> acceptedOrders = repository.findAllByOrderStatus(OrderStatus.ACCEPTED);
    for (Order order : acceptedOrders) {
      if (order.getLastModifiedDate().plusMinutes(2).isAfter(LocalDateTime.now())) {
        continue;
      }
      if (order.getLastModifiedDate().plusMinutes(15).isBefore(LocalDateTime.now())) {
        order.setOrderStatus(OrderStatus.DELIVERED);
      } else if (Math.random() < randomRate) {
        order.setOrderStatus(OrderStatus.DELIVERED);
      }
      repository.save(order);
      log.info("Order with {} id is delivered", order.getId());
    }
  }

}
