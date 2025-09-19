package com.michal_mm.ois.orderservice;

import com.michal_mm.ois.orderservice.controller.OrderController;
import com.michal_mm.ois.orderservice.exception.OrderNotFoundException;
import com.michal_mm.ois.orderservice.model.OrderRest;
import com.michal_mm.ois.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderServiceApplicationTests {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private OrderService mockedOrderService;

    @Test
    void testGetAllOrders_withValidRequest_expectedOK () throws Exception {
        // Arrange
        UUID uuid = UUID.randomUUID();
        String unitTestItemName = "unit test item name";
        String unitTestOrderName = "unit test order name";
        OrderRest orderRest = new OrderRest(uuid,
                uuid,
                unitTestItemName,
                100,
                5,
                unitTestOrderName);

        // Act
        when(mockedOrderService.getAllOrders()).thenReturn(List.of(orderRest));

        // Assert
        mvc.perform(get("/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].itemName", is(unitTestItemName)))
                .andExpect(jsonPath("$[0].orderName", is(unitTestOrderName)));
    }

    @Test
    void testGetOrderById_WithValidOrderId_expectedOK () throws Exception {
        // Arrange
        UUID uuid = UUID.randomUUID();
        String unitTestItemName = "unit test item name";
        String unitTestOrderName = "unit test order name";
        OrderRest orderRest = new OrderRest(uuid,
                uuid,
                unitTestItemName,
                100,
                5,
                unitTestOrderName);

        // Act
        when(mockedOrderService.getOrderById(uuid)).thenReturn(orderRest);

        // Assert
        mvc.perform(get("/orders/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemName", is(unitTestItemName)))
                .andExpect(jsonPath("$.orderName", is(unitTestOrderName)));
    }

    @Test
    void testGetOrderById_WithInValidOrderId_expectedNotFound () throws Exception {
        // Arrange
        UUID uuid = UUID.randomUUID();

        // Act
        when(mockedOrderService.getOrderById(uuid)).thenThrow(new OrderNotFoundException("unit test order not found"));

        // Assert
        mvc.perform(get("/orders/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testNotExistingUri() throws Exception {
        mvc.perform(get("/not-existing-uri")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
