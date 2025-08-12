package com.michal_mm.ois.orderservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.michal_mm.ois.orderservice.model.OrderRest;

@SpringBootTest
public class OrderControllerTest {

	@Autowired
	private OrderController orderController;
	
	@Test
	public void testGetAllOrders_simpleGETScenarioWithFiuxedResponse() {
		
		List<OrderRest> orders = orderController.getAllOrders();
		assertEquals(3, orders.size());
	}
}
