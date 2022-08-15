package com.example.apiweek5.controllers;

import com.example.apiweek5.repositiries.OrderRepository;
import com.example.apiweek5.services.OrdersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openapitools.model.OrderDTO;
import org.openapitools.model.OrderUpdateDTO;
import org.openapitools.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

  @MockBean OrdersService ordersService;

  @MockBean OrderRepository repository;
  @Autowired private MockMvc mockMvc;

  @Test
  @DisplayName("POST /orders")
  void postOrdersTest() throws Exception {
    OrderDTO orderDTO = new OrderDTO();
    orderDTO.setProductID(360641l);
    orderDTO.setQuantity(88);
    orderDTO.setId("CG2DD7ZD3ADBFF9Q");
    orderDTO.setDate(OffsetDateTime.parse("1970-01-01T16:52:01.83Z"));
    orderDTO.setComplete(false);
    orderDTO.setStatus(Status.PLACED);

    when(repository.getOrder(any())).thenReturn(orderDTO);
    when(repository.addOrder(any())).thenReturn(orderDTO);
    String jsonToPost =
        "{\"id\": \"CG2DD7ZD3ADBFF9Q\",\"productID\": 360641, \"quantity\": 88 , \"date\" : \"1970-01-01T16:52:01.83Z\", \"status\": \"placed\", \"complete\": false }";

    mockMvc
        .perform(post("/orders").contentType(MediaType.APPLICATION_JSON).content(jsonToPost))
        .andExpect(status().isCreated())
        .andExpect(content().json(jsonToPost));
  }

  @Test
  @DisplayName("GET /orders json")
  void getOrdersTest() throws Exception {
    OrderDTO order1 = new OrderDTO();
    OrderDTO order2 = new OrderDTO();

    order1.setProductID(13l);
    order2.setQuantity(90);
    when(ordersService.getAllOrdersByStatusAndPeriod(any(), any(), any()))
        .thenReturn(List.of(order1, order2));

    mockMvc
        .perform(get("/orders").contentType(MediaType.APPLICATION_JSON).accept(MediaType.ALL_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.[0].productID", is(13)))
        .andExpect(jsonPath("$.[1].quantity", is(90)));
  }

  @Test
  @DisplayName("DELETE /orders/{id}")
  void deleteOrderTest() throws Exception {
    mockMvc.perform(delete("/orders/1")).andExpect(status().isOk());
  }

  @Test
  @DisplayName("PATCH /orders/{id}")
  void patchOrderTest() throws Exception {

    String jsonToPost = "{ \"quantity\": 88 , \"status\": \"placed\", \"complete\": false }";

    when(repository.updateOrder(anyLong(), any(OrderUpdateDTO.class))).thenReturn(new OrderDTO());

    mockMvc
        .perform(patch("/orders/1").contentType(MediaType.APPLICATION_JSON).content(jsonToPost))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @DisplayName("GET /orders/{id}")
  void getOrderByIdTest() throws Exception {
    OrderDTO order = new OrderDTO();
    order.setQuantity(22);
    when(repository.getOrder(any())).thenReturn(order);
    mockMvc
        .perform(get("/orders/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.quantity", is(22)));
  }

  @Test
  @DisplayName("PUT /orders csvfile")
  void putBigCsvFile() throws Exception {
    OrderDTO order1 = new OrderDTO();
    order1.setProductID(13l);
    when(ordersService.addOrderList(any())).thenReturn(List.of(order1));

    mockMvc.perform(put("/orders").contentType(MediaType.parseMediaType("text/csv")).content("skldjfdlk"))
            .andExpect(status().isAccepted())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.[0].productID",is(13)));
  }

  @Test
  @DisplayName("PUT /orders/{id}")
  void putOrder() throws Exception {
    OrderDTO orderDTO = new OrderDTO();
    orderDTO.setProductID(360641l);
    orderDTO.setQuantity(88);
    orderDTO.setId("CG2DD7ZD3ADBFF9Q");
    orderDTO.setDate(OffsetDateTime.parse("1970-01-01T16:52:01.83Z"));
    orderDTO.setComplete(false);
    orderDTO.setStatus(Status.PLACED);

    String jsonToPost =
            "{\"id\": \"CG2DD7ZD3ADBFF9Q\",\"productID\": 360641, \"quantity\": 88 , \"date\" : \"1970-01-01T16:52:01.83Z\", \"status\": \"placed\", \"complete\": false }";


    when(repository.replaceOrder(any(),any())).thenReturn(orderDTO);

    mockMvc.perform(put("/orders/1").contentType(MediaType.APPLICATION_JSON).content(jsonToPost))
            .andExpect(status().isAccepted())
            .andExpect(content().json(jsonToPost));
  }
}
