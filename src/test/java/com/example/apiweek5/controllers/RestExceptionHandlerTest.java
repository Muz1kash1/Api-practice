package com.example.apiweek5.controllers;

import com.example.apiweek5.services.OrdersService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class RestExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    OrdersService ordersService;

    @Test
    @DisplayName("Exception handler should handle IllegalArgumentException")
    void illegalArgExceptionTest() throws Exception {

        when(ordersService.getAllOrdersByStatusAndPeriod(isNull(),nullable(OffsetDateTime.class),nullable(OffsetDateTime.class)))
                .thenThrow(new IllegalArgumentException());

        mockMvc.perform(get("/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title", is("Illegal Argument Exception")));
    }

    @Test
    @DisplayName("Exception handler should handle NotFound or NoSuchElement")
    void notFoundOrNoSuchElemExceptionHandlerTest() throws Exception {

        when(ordersService.getAllOrdersByStatusAndPeriod(isNull(),nullable(OffsetDateTime.class),nullable(OffsetDateTime.class)))
                .thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title", is("Not found or not such element exception")));
    }
    @Test
    @DisplayName("Exception handler should handle IllegalStateException")
    void illegalStateExceptionHandleTest() throws Exception {

        when(ordersService.getAllOrdersByStatusAndPeriod(isNull(),nullable(OffsetDateTime.class),nullable(OffsetDateTime.class)))
                .thenThrow(new IllegalStateException());

        mockMvc.perform(get("/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title", is("Illegal State Exception")));
    }

    @Test
    @DisplayName("Exception handler should handle Internal Server Error")
    void internalServerErrorHandleTest() throws Exception {

        when(ordersService.getAllOrdersByStatusAndPeriod(isNull(),nullable(OffsetDateTime.class),nullable(OffsetDateTime.class)))
                .thenThrow(new RuntimeException());

        mockMvc.perform(get("/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.title", is("Internal server error")));
    }

}
