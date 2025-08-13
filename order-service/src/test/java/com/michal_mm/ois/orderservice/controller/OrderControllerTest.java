package com.michal_mm.ois.orderservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.michal_mm.ois.orderservice.model.CreateOrderRequest;
import com.michal_mm.ois.orderservice.model.OrderRest;

@SpringBootTest
public class OrderControllerTest {

	@Autowired
	private OrderController orderController;
	
	@Test
	public void testGetAllOrders_simpleGETScenarioWithFiuxedResponse() {
		
		List<OrderRest> orders = orderController.getAllOrders();
		assertEquals(4, orders.size());
	}
	
	@Test
	public void testGetOrderById_WithValidOrderId_ValidOrderRest() {
		UUID orderId = UUID.randomUUID();
		
		OrderRest order = orderController.getOrderById(orderId);
		
		assertNotNull(order);
		assertEquals(order.getOrderId().toString(), orderId.toString());
	}
	
	@Test
	public void testCreateOrder_ValidCreateOrderRequest_ValidOrderRest() {
		UUID itemId = UUID.randomUUID();
		String orderName = "Unit test order name";
		Integer quantity = 125;
		
		CreateOrderRequest createOrderRequest = new CreateOrderRequest();
		createOrderRequest.setItemId(itemId);
		createOrderRequest.setOrderName(orderName);
		createOrderRequest.setQuantity(quantity);
		
		OrderRest orderRest = orderController.createOrder(createOrderRequest);
		
		assertNotNull(orderRest);
		assertEquals(itemId.toString(), orderRest.getItemId().toString());
		assertEquals(orderName, orderRest.getOrderName());
	}
}
