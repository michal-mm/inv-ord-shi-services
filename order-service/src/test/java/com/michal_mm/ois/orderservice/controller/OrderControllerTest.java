package com.michal_mm.ois.orderservice.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.michal_mm.ois.orderservice.exception.OrderNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.michal_mm.ois.orderservice.data.OrderEntity;
import com.michal_mm.ois.orderservice.data.OrderRepository;
import com.michal_mm.ois.orderservice.model.CreateOrderRequest;
import com.michal_mm.ois.orderservice.model.OrderRest;
import com.michal_mm.ois.orderservice.service.OrderServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.RequestHeadersUriSpec;

@SpringBootTest
public class OrderControllerTest {

	private OrderController orderController;

    private OrderServiceImpl orderService;
	
	@Mock
	private OrderRepository orderRepository;

    @Mock
    private RestClient restClient;
	
	@BeforeEach
	void setUp() {
        orderService = new OrderServiceImpl(orderRepository);
		orderController = new OrderController(orderService);
	}
	
	@Test
	public void testGetAllOrders_simpleGETScenario_returnsValidOrderRest() {
		// Arrange 
		UUID uuid = UUID.randomUUID();
		OrderEntity orderEntity = new OrderEntity(uuid, uuid,
                "unit test item name",
                "unit test order",
                100,
                5);
		
		// Act
		when(orderRepository.findAll()).thenReturn(List.of(orderEntity));
	
		List<OrderRest> orders = orderController.getAllOrders();
		
		// Assert
		assertEquals(1, orders.size());
		assertEquals(uuid.toString(), orders.getFirst().getOrderId().toString());
	}
	
	@Test
	public void testGetOrderById_withValidOrderId_returnsValidOrderRest() {
		// Arrange
		UUID orderId = UUID.randomUUID();
		UUID itemId = UUID.randomUUID();
        String itemName = "unit test item name";
		String orderName = "unit test order";
		OrderEntity orderEntity = new OrderEntity(orderId, itemId, itemName, orderName, 100, 5);
		
		// Act
		when(orderRepository.findOrderById(orderId)).thenReturn(orderEntity);
		OrderRest orderRest = orderController.getOrderById(orderId);
		
		// Assert
		assertNotNull(orderRest);
		assertEquals(orderId.toString(), orderRest.getOrderId().toString());
		assertEquals(itemId.toString(), orderRest.getItemId().toString());
		assertEquals(orderName, orderRest.getOrderName());
        assertEquals(itemName, orderRest.getItemName());
	}

    @Test
    public void testGetOrderById_withInvalidOrderId_handlesException() {
        // Arrange
        UUID orderId = UUID.randomUUID();

        // Act
        when(orderRepository.findOrderById(orderId)).thenReturn(null);
        OrderRest orderRest = null;
        try {
            orderRest = orderController.getOrderById(orderId);
            fail("Didn't catch OrderNotFoundException");
        } catch (OrderNotFoundException e) {
            assertNotNull(e);
            assertNull(orderRest);
        }
    }
	
	@Test
	public void testCreateOrder_withValidCreateOrderRequest_returnsValidOrderRest() {
		// Arrange
		UUID orderId = UUID.randomUUID();
		UUID itemId = UUID.randomUUID();
        String itemName = "Unit test item name";
		String orderName = "Unit test order name";
		Integer quantity = 125;
		Integer itemPrice = 5;

		CreateOrderRequest createOrderRequest = new CreateOrderRequest(itemId, orderName, quantity);
		OrderEntity orderEntity = new OrderEntity(orderId, itemId, itemName, orderName, quantity, itemPrice);
        OrderRest orderRestExpected = new OrderRest(orderId, itemId, itemName, quantity, itemPrice, orderName);
        ResponseEntity<OrderRest> responseEntity = ResponseEntity.of(Optional.of(orderRestExpected));
		
		// Act
		when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);

        RequestHeadersUriSpec reqHeaders = mock(RequestHeadersUriSpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);

        when(restClient.get()).thenReturn(reqHeaders);
        when(reqHeaders.uri((String) any(), (Object) any())).thenReturn(reqHeaders);
        when(reqHeaders.retrieve()).thenReturn(responseSpec);
        when(responseSpec.toEntity(OrderRest.class)).thenReturn(responseEntity);

//
//        when(restClient.get()
//                .uri((String) any(), (Object) any())
//                .retrieve()
//                .toEntity(OrderRest.class))
//                .thenReturn(responseEntity);
        OrderRest orderRestFromEntity = responseEntity.getBody();
		OrderRest savedRest = orderController.createOrder(createOrderRequest);
		
		// Assert
		assertNotNull(savedRest);
		assertEquals(itemId.toString(), savedRest.getItemId().toString());
		assertEquals(orderName, savedRest.getOrderName());
        assertEquals(itemName, savedRest.getItemName());
		assertEquals(orderId.toString(), savedRest.getOrderId().toString());
	}
}
