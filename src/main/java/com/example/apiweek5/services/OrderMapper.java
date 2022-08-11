package com.example.apiweek5.services;

import org.openapitools.model.OrderDTO;
import org.openapitools.model.Status;

import java.time.OffsetDateTime;

public class OrderMapper {

  public static OrderDTO map(String raw) {
    String[] dtoFieldsArray = raw.split(",");
    OrderDTO orderDTO = new OrderDTO();
    orderDTO.setId(dtoFieldsArray[0]);
    orderDTO.setProductID(Long.getLong(dtoFieldsArray[1]));
    orderDTO.setQuantity(Integer.getInteger(dtoFieldsArray[2]));
    orderDTO.setDate(OffsetDateTime.parse(dtoFieldsArray[3]));
    orderDTO.setStatus(Status.fromValue(dtoFieldsArray[4]));
    orderDTO.setComplete(Boolean.getBoolean(dtoFieldsArray[5]));
    return orderDTO;
  }
}
