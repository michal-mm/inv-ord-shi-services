package com.michal_mm.ois.orderservice.service;

import java.util.List;
import java.util.UUID;

import com.michal_mm.ois.orderservice.model.CreateOrderRequest;
import com.michal_mm.ois.orderservice.model.OrderRest;

public interface OrderService {

	List<OrderRest> getAllOrders();
	
	OrderRest getOrderById(UUID orderId);
	
	OrderRest createOrder(CreateOrderRequest createOrderRequest);

    void doSimpleJob(String anArgument) throws InterruptedException;
}
