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
		OrderRest order1 = new OrderRest(UUID.randomUUID(),
				UUID.randomUUID(), "Item #1", 5, "ORDER #1");

		OrderRest order2 = new OrderRest(UUID.randomUUID(), 
				UUID.randomUUID(), "Item #2", 20, "ORDER #2");

		OrderRest order3 = new OrderRest(UUID.randomUUID(), 
				UUID.randomUUID(), "Item #3", 123, "ORDER #3");
		
		OrderRest order4 = new OrderRest(UUID.randomUUID(), 
				UUID.randomUUID(), "Item #4", 321, "ORDER #4");
		
		return List.of(order1, order2, order3, order4);
	}

	@Override
	public OrderRest getOrderById(UUID orderId) {
//		OrderRest orderToReturn = new OrderRest(orderId, 
//				UUID.randomUUID(), "Test Item", 999, "TEST ORDER Name");
		OrderEntity orderEntity = orderRepository.getOrderById(orderId);
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
