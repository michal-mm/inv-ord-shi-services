package com.michal_mm.ois.orderservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.michal_mm.ois.orderservice.model.CreateOrderRequest;
import com.michal_mm.ois.orderservice.model.OrderRest;
import com.michal_mm.ois.orderservice.service.OrderService;

@RestController()
@RequestMapping("/orders")
public class OrderController {
	
	private final OrderService orderService;
	
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	

	@GetMapping
	public List<OrderRest> getAllOrders() {
		return orderService.getAllOrders();
	}
	
	@GetMapping("/{orderId}")
	public OrderRest getOrderById(@PathVariable UUID orderId) {
		return orderService.getOrderById(orderId);
	}
	
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public OrderRest createOrder (@RequestBody CreateOrderRequest createOrderRequest) {
		return orderService.createOrder(createOrderRequest)
;	}
}
