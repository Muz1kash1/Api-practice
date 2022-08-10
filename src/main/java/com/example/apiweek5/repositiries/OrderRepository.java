package com.example.apiweek5.repositiries;

import org.openapitools.model.OrderDTO;
import org.openapitools.model.OrderUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {
  private final Map<Long, OrderDTO> orderRepo;

  @Autowired
  public OrderRepository(Map<Long, OrderDTO> orderRepo) {
    this.orderRepo = orderRepo;
  }

  public Map<Long, OrderDTO> getOrderRepo() {
    return orderRepo;
  }

  public List<OrderDTO> getOrderList() {
    List<OrderDTO> orderDTOList = new ArrayList<>();
    orderRepo.forEach(
        (id, order) -> {
          orderDTOList.add(order);
        });
    return orderDTOList;
  }

  public void addOrder(OrderDTO orderDTO) {
    orderRepo.putIfAbsent(orderDTO.getProductID(), orderDTO);
  }

  public void deleteOrder(Long orderId) {
    orderRepo.remove(orderId);
  }

  public OrderDTO getOrder(Long id) {
    return orderRepo.get(id);
  }

  public OrderDTO updateOrder(Long id, OrderUpdateDTO orderUpdateDTO) {
    OrderDTO orderDTO = orderRepo.get(id);
    orderDTO.setStatus(orderUpdateDTO.getStatus());
    orderDTO.setQuantity(orderUpdateDTO.getQuantity());
    orderDTO.setComplete(orderUpdateDTO.getComplete());
    orderRepo.replace(id, orderDTO);
    return orderRepo.get(id);
  }

  public OrderDTO replaceOrder(Long id, OrderDTO orderDTO) {
    orderRepo.replace(id, orderDTO);
    return orderRepo.get(id);
  }
}
