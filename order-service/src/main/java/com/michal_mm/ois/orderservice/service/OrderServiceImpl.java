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
	private final OrderRepository orderRepository;
	
	public OrderServiceImpl(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public List<OrderRest> getAllOrders() {
		
		return orderRepository.findAll().stream()
				.map(this::getOrderRestFromOrderEntity)
				.toList();
	}

	@Override
	public OrderRest getOrderById(UUID orderId) {

        OrderEntity orderById = orderRepository.findOrderById(orderId);
        if(orderById == null) {
            // TODO
            // item not found, throw an exception
            throw new RuntimeException("Item not found");
        }

        return getOrderRestFromOrderEntity(orderById);
	}

	private OrderRest getOrderRestFromOrderEntity(OrderEntity orderEntity) {
		return new OrderRest(
								orderEntity.getId(),
								orderEntity.getItemId(),
								"FIXED ITEM NAME with FIXED PRICE",
								orderEntity.getQuantity(),
								orderEntity.getItemPrice(),
								orderEntity.getOrderName()
				);
	}
	
	private OrderEntity getOrderEntityFromOrderRest(OrderRest orderRest) {
		return new OrderEntity(orderRest.getOrderId(),
				orderRest.getItemId(),
				orderRest.getOrderName(),
				orderRest.getQuantity(),
				orderRest.getPrice());
	}
	
	

	@Override
	public OrderRest createOrder(CreateOrderRequest createOrderRequest) {
		// TODO - this should be remote call to inventory service
		// success depends on the itemId and the quantity (inventory service has to have enough items)
		int itemPrice = 10000;
		String itemName = "Fixed item name (from POST req to OrderService)";
		
		OrderRest orderRest = new OrderRest(
				UUID.randomUUID(),
				createOrderRequest.getItemId(),
				itemName,
				createOrderRequest.getQuantity(),
				itemPrice, 
				createOrderRequest.getOrderName()
				);

        return getOrderRestFromOrderEntity(orderRepository.save(getOrderEntityFromOrderRest(orderRest)));
	}

	
	
}
