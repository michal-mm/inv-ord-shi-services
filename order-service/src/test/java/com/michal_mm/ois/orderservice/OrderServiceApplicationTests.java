package com.michal_mm.ois.orderservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.michal_mm.ois.orderservice.controller.OrderController;
import com.michal_mm.ois.orderservice.exception.OrderNotFoundException;
import com.michal_mm.ois.orderservice.model.CreateOrderRequest;
import com.michal_mm.ois.orderservice.model.OrderRest;
import com.michal_mm.ois.orderservice.service.OrderService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderServiceApplicationTests {

    public static final String UNIT_TEST_ITEM_NAME = "unit test item name";
    public static final String UNIT_TEST_ORDER_NAME = "unit test order name";
    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final UUID NOT_EXISTING_ITEM_ID = UUID.randomUUID();
    public static final int QUANTITY = 100;
    public static final int PRICE = 5;

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private OrderService mockedOrderService;

    @Test
    void testGetAllOrders_withValidRequest_expectedOK () throws Exception {
        // Arrange

        OrderRest orderRest = getValidOrderRest();

        // Act
        when(mockedOrderService.getAllOrders()).thenReturn(List.of(orderRest));

        // Assert
        mvc.perform(get("/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].itemName", is(UNIT_TEST_ITEM_NAME)))
                .andExpect(jsonPath("$[0].orderName", is(UNIT_TEST_ORDER_NAME)));
    }

    @Test
    void testGetOrderById_WithValidOrderId_expectedOK () throws Exception {
        // Arrange
        OrderRest orderRest = getValidOrderRest();

        // Act
        when(mockedOrderService.getOrderById(ITEM_ID)).thenReturn(orderRest);

        // Assert
        mvc.perform(get("/orders/" + ITEM_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemName", is(UNIT_TEST_ITEM_NAME)))
                .andExpect(jsonPath("$.orderName", is(UNIT_TEST_ORDER_NAME)));
    }

    @Test
    void testGetOrderById_WithInValidOrderId_expectedNotFound () throws Exception {
        // Arrange & ACT
        when(mockedOrderService.getOrderById(ITEM_ID)).thenThrow(new OrderNotFoundException("unit test order not found"));

        // Assert
        mvc.perform(get("/orders/" + ITEM_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateOrder_withInvalidItemId_expectedItemNotFoundException() throws Exception {
        // Arrange
        String jsonInput = createOrderRequest2JsonStr(getCreateOrderRequestNotExistingId());

        // Act
        when(mockedOrderService.createOrder(any()))
                .thenThrow(HttpClientErrorException.NotFound.class);

        mvc.perform(post("/orders")
                    .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isNotFound());
    }

    @Test
    void testNotExistingUri() throws Exception {
        mvc.perform(get("/not-existing-uri")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    private String createOrderRequest2JsonStr(CreateOrderRequest createOrderRequest) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(createOrderRequest);
        } catch (JsonProcessingException e) {
            fail("Problem with creating JSON from CreateOrderRequest: " + e.getMessage());
        }

        return "{}";
    }
    
    private CreateOrderRequest getCreateOrderRequestNotExistingId() {
        return new CreateOrderRequest(NOT_EXISTING_ITEM_ID, UNIT_TEST_ORDER_NAME, QUANTITY);
    }

    @NotNull
    private static OrderRest getValidOrderRest() {
        return new OrderRest(ITEM_ID,
                ITEM_ID,
                UNIT_TEST_ITEM_NAME,
                QUANTITY,
                PRICE,
                UNIT_TEST_ORDER_NAME);
    }
}
