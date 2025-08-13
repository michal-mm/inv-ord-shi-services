package com.michal_mm.ois.orderservice.service;

import java.util.List;
import java.util.UUID;

import com.michal_mm.ois.orderservice.model.CreateOrderRequest;
import com.michal_mm.ois.orderservice.model.OrderRest;

public interface OrderService {

	public List<OrderRest> getAllOrders();
	
	public OrderRest getOrderById(UUID orderId);
	
	public OrderRest createOrder(CreateOrderRequest createOrderRequest);
}
