package com.example.apiweek5.controllers;

import com.example.apiweek5.repositiries.OrderRepository;
import com.example.apiweek5.services.OrdersService;
import org.openapitools.api.OrderApi;
import org.openapitools.model.OrderDTO;
import org.openapitools.model.OrderUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@RequestMapping("/")
@RestController
public class ControllerImpl implements OrderApi {
  @Autowired OrderRepository orderRepository;
  @Autowired OrdersService ordersService;
  /**
   * @return
   */
  @Override
  public ResponseEntity<List<OrderDTO>> getAllOrders() {
    return ResponseEntity.ok()
        .body(orderRepository.getOrderList());
  }

  /**
   * @param startDateTime Date of start (required)
   * @param endDateTime Date of end (required)
   * @return
   */
  @Override
  public ResponseEntity<List<OrderDTO>> ordersBetweenDates(
      OffsetDateTime startDateTime, OffsetDateTime endDateTime) {
    return ResponseEntity.ok().body(ordersService.getAllOrdersByDates(startDateTime, endDateTime));
  }

  /**
   * @param status status of order (required)
   * @return
   */
  @RequestMapping("/order/status/{status}")
  @Override
  public ResponseEntity<List<OrderDTO>> ordersByStatus(String status) {
    return ResponseEntity.ok().body(ordersService.getAllOrdersByStatus(status));
  }

  /**
   * @param orderDTO Create a new order in the store (required)
   * @return
   */
  @Override
  public ResponseEntity<OrderDTO> addorder(OrderDTO orderDTO) {
    orderRepository.addOrder(orderDTO);
    return ResponseEntity.accepted().body(orderRepository.getOrder(orderDTO.getProductID()));
  }

  /**
   * @param orderId order id to delete (required)
   * @return
   */
  @Override
  public ResponseEntity<Void> deleteorder(Long orderId) {
    orderRepository.deleteOrder(orderId);
    return ResponseEntity.noContent().build();
  }

  /**
   * @param orderId ID of order to return (required)
   * @return
   */
  @Override
  public ResponseEntity<OrderDTO> getorderById(Long orderId) {
    return ResponseEntity.ok().body(orderRepository.getOrder(orderId));
  }

  /**
   * @param orderId ID of order to return (required)
   * @param orderUpdateDTO Update an existent order (required)
   * @return
   */
  @Override
  public ResponseEntity<OrderDTO> patchOrder(Long orderId, OrderUpdateDTO orderUpdateDTO) {
    return ResponseEntity.ok().body(orderRepository.updateOrder(orderId,orderUpdateDTO));
  }

  /**
   * @param orderDTO Replace an existent order (required)
   * @return
   */
}
