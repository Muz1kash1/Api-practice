package com.example.apiweek5.controllers;

import com.example.apiweek5.RestResponseEntityExceptionHandler;
import com.example.apiweek5.repositiries.OrderRepository;
import com.example.apiweek5.services.OrdersService;
import org.openapitools.api.OrdersApi;
import org.openapitools.model.OrderDTO;
import org.openapitools.model.OrderUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class ControllerImpl implements OrdersApi {
  @Autowired RestResponseEntityExceptionHandler restResponseEntityExceptionHandler;
  @Autowired OrderRepository orderRepository;
  @Autowired OrdersService ordersService;

  /**
   * @return
   */

  /**
   * @return
   */
  @Override
  public ResponseEntity<List<OrderDTO>> getAllOrders(
      String status, OffsetDateTime startDateTime, OffsetDateTime endDateTime) {
    return ResponseEntity.ok()
        .body(ordersService.getAllOrdersByStatusAndPeriod(status, startDateTime, endDateTime));
  }

  /**
   * @return
   */
  @Override
  public Optional<NativeWebRequest> getRequest() {
    return OrdersApi.super.getRequest();
  }

  /**
   * @param orderDTO Create a new order in the store (required)
   * @return
   */
  @Override
  public ResponseEntity<OrderDTO> addOrder(OrderDTO orderDTO) {
    orderRepository.addOrder(orderDTO);
    return ResponseEntity.accepted().body(orderRepository.getOrder(orderDTO.getProductID()));
  }

  /**
   * @param orderId order id to delete (required)
   * @return
   */
  @Override
  public ResponseEntity<Void> deleteOrder(Long orderId) {
    orderRepository.deleteOrder(orderId);
    return ResponseEntity.noContent().build();
  }

  /**
   * @param orderId ID of order to return (required)
   * @return
   */
  @Override
  public ResponseEntity<OrderDTO> getOrderById(Long orderId) {
    return ResponseEntity.ok().body(orderRepository.getOrder(orderId));
  }

  /**
   * @param orderId ID of order to return (required)
   * @param orderUpdateDTO Update an existent order (required)
   * @return
   */
  @Override
  public ResponseEntity<OrderDTO> patchOrder(Long orderId, OrderUpdateDTO orderUpdateDTO) {
    return ResponseEntity.ok().body(orderRepository.updateOrder(orderId, orderUpdateDTO));
  }

  /**
   * @param orderDTO Replace an existent order (required)
   * @return
   */
  @Override
  public ResponseEntity<OrderDTO> updateOrder(Long orderId, OrderDTO orderDTO) {
    return ResponseEntity.accepted().body(orderRepository.replaceOrder(orderId, orderDTO));
  }
}
