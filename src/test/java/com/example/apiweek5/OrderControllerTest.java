package com.example.apiweek5;

import com.example.apiweek5.repositiries.OrderRepository;
import com.example.apiweek5.services.OrdersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.openapitools.model.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

  @Mock OrdersService ordersService;

  @Mock OrderRepository repository;
  @Autowired private MockMvc mockMvc;

  @Test
  @DisplayName("POST /orders")
  void postOrdersTest() throws Exception {

    OrderDTO order = new OrderDTO();
    order.setProductID(4L);
    String dateTime = "1974-10-25T02:08:09.450Z";

    String jsonToPost =
        "{ \"id\": \"QKDF454543JDFJDK\",\"productId\": 5, \"quantity\": 10 , \"date\" : \"1974-10-25T02:08:09.450Z\", \"status\": \"APPROVED\", \"complete\": true }";

    when(repository.addOrder(any(OrderDTO.class))).thenReturn(order);

    mockMvc
        .perform(post("/orders").contentType(MediaType.APPLICATION_JSON).content(jsonToPost))
        .andExpect(status().isCreated())
        .andExpect(content().json(jsonToPost));
  }

  //  @Test
  //  @DisplayName("DELETE /orders/{id}")
  //  void deleteOrderTest() throws Exception {
  //    mockMvc.perform(delete("/orders/1")).andExpect(status().isOk());
  //  }
  //
  //  @Test
  //  @DisplayName("PATCH /orders/{id}")
  //  void patchOrderTest() throws Exception {
  //
  //    String jsonToPost = "{ \"quantity\": 75, \"complete\": true}";
  //
  //    when(ordersService.patchOrder(anyString(), any(EditedOrder.class))).thenReturn(new
  // OrderDTO());
  //
  //    mockMvc
  //        .perform(patch("/orders/1").contentType(MediaType.APPLICATION_JSON).content(jsonToPost))
  //        .andExpect(status().isOk())
  //        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  //  }
  //
  //  @Test
  //  @DisplayName("PUT /orders/{id}/approved")
  //  void putApprovedTest() throws Exception {
  //
  //    OrderDTO order = new OrderDTO();
  //    order.setStatus(StatusEnum.APPROVED);
  //
  //    when(ordersService.approveOrder("1")).thenReturn(order);
  //
  //    mockMvc
  //        .perform(put("/orders/1/approved"))
  //        .andExpect(status().isOk())
  //        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
  //        .andExpect(jsonPath("$['status']", is("approved")));
  //  }
  //
  //  @Test
  //  @DisplayName("PUT /orders/{id}/delivered")
  //  void putDeliveredTest() throws Exception {
  //
  //    OrderDTO order = new OrderDTO();
  //    order.setStatus(StatusEnum.DELIVERED);
  //
  //    when(ordersService.approveOrder("1")).thenReturn(order);
  //
  //    mockMvc
  //        .perform(put("/orders/1/delivered"))
  //        .andExpect(status().isOk())
  //        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
  //        .andExpect(jsonPath("$['status']", is("delivered")));
  //  }
}
