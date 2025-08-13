package com.michal_mm.ois.orderservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.michal_mm.ois.orderservice.model.OrderRest;

@RestController()
@RequestMapping("/orders")
public class OrderController {

	@GetMapping
	public List<OrderRest> getAllOrders() {
		
		OrderRest order1 = new OrderRest(UUID.randomUUID(),
								UUID.randomUUID(), 
								"Item #1", 5, "ORDER #1");
		
		OrderRest order2 = new OrderRest(UUID.randomUUID(),
				UUID.randomUUID(), 
				"Item #2", 20, "ORDER #2");
		
		OrderRest order3 = new OrderRest(UUID.randomUUID(),
				UUID.randomUUID(), 
				"Item #3", 123, "ORDER #3");


		
		
		return List.of(order1, order2, order3);
	}
	
	@GetMapping("/{orderId}")
	public OrderRest getOrderById(@PathVariable UUID orderId) {
		OrderRest orderToReturn = new OrderRest(orderId, 
									UUID.randomUUID(),
									"Test Item", 999, "TEST ORDER Name");
		
		return orderToReturn;
	}
}
