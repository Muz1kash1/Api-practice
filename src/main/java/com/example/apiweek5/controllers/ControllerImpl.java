package com.example.apiweek5.controllers;

import com.example.apiweek5.repositiries.OrderRepository;
import com.example.apiweek5.services.OrdersService;
import org.openapitools.api.OrdersApi;
import org.openapitools.model.OrderDTO;
import org.openapitools.model.OrderUpdateDTO;
import org.openapitools.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import java.io.IOException;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class ControllerImpl implements OrdersApi {
  @Autowired OrderRepository orderRepository;
  @Autowired OrdersService ordersService;

  /**
   * @param status status of order (optional)
   * @param startDateTime Date of start (optional)
   * @param endDateTime Date of end (optional)
   * @return
   */

  /**
   * @return
   */
  @Override
  @Consumes(MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<OrderDTO>> getAllOrders(
      Status status, OffsetDateTime startDateTime, OffsetDateTime endDateTime) {

    return ResponseEntity.ok()
        .body(ordersService.getAllOrdersByStatusAndPeriod(status, startDateTime, endDateTime));
  }

  @GetMapping(value = "/orders", produces = "application/octet-stream")
  public ResponseEntity<InputStreamResource> getAllOrdersCsv(
          @RequestParam(value = "status", required = false) @Valid Status status,
          @RequestParam(value = "startDateTime", required = false)
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          @Valid
          OffsetDateTime startDateTime,
          @RequestParam(value = "endDateTime", required = false)
          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
          @Valid
          OffsetDateTime endDateTime) throws IOException {

    String filename = "myfile.csv";

    InputStreamResource file =
        new InputStreamResource(
            ordersService.csvOut(
                ordersService.getAllOrdersByStatusAndPeriod(status, startDateTime, endDateTime)));
    HttpHeaders headers = new HttpHeaders();
    headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
    headers.set(HttpHeaders.CONTENT_TYPE, "application/octet-stream");


    return new ResponseEntity<>(file, headers, HttpStatus.OK);
  }

  /**
   * @return
   */
  @Override
  public Optional<NativeWebRequest> getRequest() {
    return OrdersApi.super.getRequest();
  }

  /**
   * @param orderId ID of order to return (required)
   * @return
   */

  /**
   * @param orderDTO Create a new order in the store (required)
   * @return
   */
  @Override
  public ResponseEntity<OrderDTO> addOrder(OrderDTO orderDTO) {
    orderRepository.addOrder(orderDTO);
    return ResponseEntity.created(
            URI.create("http://localhost:8080/orders/" + orderDTO.getProductId()))
        .body(orderRepository.getOrder(orderDTO.getProductId()));
  }

  /**
   * @param orderId order id to delete (required)
   * @return
   */
  @Override
  public ResponseEntity<Void> deleteOrder(Long orderId) {
    orderRepository.deleteOrder(orderId);
    return ResponseEntity.ok().build();
  }

  /**
   * @param orderId ID of order to return (required)
   * @return
   */
  @Override
  public ResponseEntity<OrderDTO> getOrderById(Long orderId) {
    OrderDTO orderDTOById = orderRepository.getOrder(orderId);
    if (orderDTOById != null) {
      return ResponseEntity.ok().body(orderDTOById);
    } else {
      throw new NoSuchElementException();
    }
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

  /**
   * @param body Put order list (required)
   * @return
   */
  @Override
  public ResponseEntity<List<OrderDTO>> uploadOrderList(Resource body) {
    return ResponseEntity.accepted().body(ordersService.addOrderList(body));
  }
}
