package com.example.apiweek5.services;

import com.example.apiweek5.repositiries.OrderRepository;
import org.openapitools.model.OrderDTO;
import org.openapitools.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class OrdersService {
  @Autowired OrderRepository orderRepository;

  public List<OrderDTO> getAllOrdersByDates(OffsetDateTime start, OffsetDateTime end) {
    List<OrderDTO> ordersBetweenDates = new ArrayList<>();
    List<Map.Entry<Long, OrderDTO>> entryList =
        orderRepository.getOrderRepo().entrySet().stream()
            .filter(
                (longOrderDTOEntry ->
                    longOrderDTOEntry.getValue().getDate().isBefore(end)
                        && longOrderDTOEntry.getValue().getDate().isAfter(start)))
            .toList();
    for (Map.Entry<Long, OrderDTO> entry : entryList) {
      ordersBetweenDates.add(entry.getValue());
    }
    return ordersBetweenDates;
  }

  public List<OrderDTO> getAllOrdersByStatus(String status) {
    List<OrderDTO> ordersByStatus = new ArrayList<>();
    List<Map.Entry<Long, OrderDTO>> entryList =
        orderRepository.getOrderRepo().entrySet().stream()
            .filter(
                (longOrderDTOEntry ->
                    longOrderDTOEntry.getValue().getStatus() == Status.valueOf(status)))
            .toList();
    for (Map.Entry<Long, OrderDTO> entry : entryList) {
      ordersByStatus.add(entry.getValue());
    }
    return ordersByStatus;
  }

  public List<OrderDTO> getAllOrdersByStatusAndPeriod(
      String status, OffsetDateTime start, OffsetDateTime end) {
    return Stream.concat(
            getAllOrdersByStatus(status).stream(), getAllOrdersByDates(start, end).stream())
        .toList();
  }
}
