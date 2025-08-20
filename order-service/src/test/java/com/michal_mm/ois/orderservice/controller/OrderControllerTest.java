package com.michal_mm.ois.orderservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.michal_mm.ois.orderservice.data.OrderEntity;
import com.michal_mm.ois.orderservice.data.OrderRepository;
import com.michal_mm.ois.orderservice.model.CreateOrderRequest;
import com.michal_mm.ois.orderservice.model.OrderRest;
import com.michal_mm.ois.orderservice.service.OrderServiceImpl;

@SpringBootTest
public class OrderControllerTest {

	private OrderController orderController;
	
	@Mock
	private OrderRepository orderRepository;
	
	@BeforeEach
	void setUp() {
		orderController = new OrderController(new OrderServiceImpl(orderRepository));
	}
	
	@Test
	public void testGetAllOrders_simpleGETScenarioWithFiuxedResponse() {
		// Arrange 
		UUID uuid = UUID.randomUUID();
		OrderEntity orderEntity = new OrderEntity(uuid, uuid, "unit test order", 100);
		
		// Act
		when(orderRepository.findAll()).thenReturn(List.of(orderEntity));
	
		List<OrderRest> orders = orderController.getAllOrders();
		
		// Assert
		assertEquals(1, orders.size());
	}
	
	@Test
	public void testGetOrderById_WithValidOrderId_ValidOrderRest() {
		// Arrange
		UUID orderId = UUID.randomUUID();
		UUID itemId = UUID.randomUUID();
		String orderName = "unit test order";
		OrderEntity orderEntity = new OrderEntity(orderId, itemId, orderName, 100);
		
		// Act
		when(orderRepository.findOrderById(orderId)).thenReturn(orderEntity);
		OrderRest orderRest = orderController.getOrderById(orderId);
		
		// Assert
		assertNotNull(orderRest);
		assertEquals(orderId.toString(), orderRest.getOrderId().toString());
		assertEquals(itemId.toString(), orderRest.getItemId().toString());
		assertEquals(orderName, orderRest.getOrderName());
	}
	
	@Test
	public void testCreateOrder_ValidCreateOrderRequest_ValidOrderRest() {
		// Arrange
		UUID orderId = UUID.randomUUID();
		UUID itemId = UUID.randomUUID();
		String orderName = "Unit test order name";
		Integer quantity = 125;
		
		CreateOrderRequest createOrderRequest = new CreateOrderRequest();
		createOrderRequest.setItemId(itemId);
		createOrderRequest.setOrderName(orderName);
		createOrderRequest.setQuantity(quantity);
		
		OrderEntity orderEntity = new OrderEntity(orderId, itemId, orderName, quantity);
		
		// Act
		when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);
		OrderRest savedRest = orderController.createOrder(createOrderRequest);
		
		// Assert
		assertNotNull(savedRest);
		assertEquals(itemId.toString(), savedRest.getItemId().toString());
		assertEquals(orderName, savedRest.getOrderName());
		assertEquals(orderId.toString(), savedRest.getOrderId().toString());
	}
}
