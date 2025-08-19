package com.michal_mm.ois.orderservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.michal_mm.ois.orderservice.data.OrderEntity;
import com.michal_mm.ois.orderservice.data.OrderRepository;
import com.michal_mm.ois.orderservice.model.CreateOrderRequest;
import com.michal_mm.ois.orderservice.model.OrderRest;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	public OrderServiceImpl() {}
	
	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public List<OrderRest> getAllOrders() {
		
		return orderRepository.findAll().stream()
				.map(orderEntity -> new OrderRest(
						orderEntity.getId(),
						orderEntity.getItemId(),
						"FIXED ITEM NAME with FIXED PRICE",
						12345,
						orderEntity.getOrderName()
						))
				.toList();
	}

	@Override
	public OrderRest getOrderById(UUID orderId) {
		OrderEntity orderEntity = orderRepository.findOrderById(orderId);
		
		OrderRest orderRestToReturn = new OrderRest(
								orderEntity.getId(),
								orderEntity.getItemId(),
								"FIXED ITEM NAME with FIXED PRICE",
								12345,
								orderEntity.getOrderName()
				);
				

		return orderRestToReturn;
	}

	@Override
	public OrderRest createOrder(CreateOrderRequest createOrderRequest) {
		OrderRest orderRest = new OrderRest(
				UUID.randomUUID(),
				createOrderRequest.getItemId(), 
				"Item name to come from DB", 
				-10, 
				createOrderRequest.getOrderName() );
		
		return orderRest;
	}

	
	
}
