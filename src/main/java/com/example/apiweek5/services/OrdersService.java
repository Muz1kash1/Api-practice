package com.example.apiweek5.services;

import com.example.apiweek5.repositiries.OrderRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.openapitools.model.OrderDTO;
import org.openapitools.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
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

  public List<OrderDTO> addOrderList(Resource resource) {
    List<OrderDTO> orderDTOList = new ArrayList<>();

    try {
      Reader reader = new InputStreamReader(resource.getInputStream());
      Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
      for (CSVRecord record : records) {
        if (record.stream().toList().size() > 2) {
          OrderDTO tempDTO = new OrderDTO();
          tempDTO.setId(record.stream().toList().get(0));
          tempDTO.setProductID(Long.getLong(record.stream().toList().get(1)));
          tempDTO.setQuantity(Integer.getInteger(record.stream().toList().get(2)));
          tempDTO.setDate(OffsetDateTime.parse(record.stream().toList().get(3)));
          tempDTO.setStatus(Status.valueOf(record.stream().toList().get(4)));
          tempDTO.setComplete(Boolean.getBoolean(record.stream().toList().get(5)));
          orderRepository.addOrder(tempDTO);
          orderDTOList.add(tempDTO);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return orderDTOList;
  }
}
