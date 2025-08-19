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
				.map(orderEntity -> getOrderRestFromOrderEntity(orderEntity))
				.toList();
	}

	@Override
	public OrderRest getOrderById(UUID orderId) {
		return getOrderRestFromOrderEntity(orderRepository.findOrderById(orderId));
	}

	private OrderRest getOrderRestFromOrderEntity(OrderEntity orderEntity) {
		return new OrderRest(
								orderEntity.getId(),
								orderEntity.getItemId(),
								"FIXED ITEM NAME with FIXED PRICE",
								12345,
								orderEntity.getOrderName()
				);
	}
	
	private OrderEntity getOrderEntityFromOrderRest(OrderRest orderRest) {
		return new OrderEntity(orderRest.getOrderId(),
				orderRest.getItemId(),
				orderRest.getOrderName(),
				8888);
	}
	
	

	@Override
	public OrderRest createOrder(CreateOrderRequest createOrderRequest) {
		OrderRest orderRest = new OrderRest(
				UUID.randomUUID(),
				createOrderRequest.getItemId(),
				"Fixed item name through POST request",
				9999,
				createOrderRequest.getOrderName()
				);
		
		OrderEntity orderEntity = getOrderEntityFromOrderRest(orderRest);
		
		OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
		
		return getOrderRestFromOrderEntity(savedOrderEntity);
	}

	
	
}
