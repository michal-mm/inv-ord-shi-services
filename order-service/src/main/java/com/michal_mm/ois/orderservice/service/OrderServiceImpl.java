package com.michal_mm.ois.orderservice.service;

import com.michal_mm.ois.orderservice.data.OrderEntity;
import com.michal_mm.ois.orderservice.data.OrderRepository;
import com.michal_mm.ois.orderservice.exception.OrderNotFoundException;
import com.michal_mm.ois.orderservice.model.CreateOrderRequest;
import com.michal_mm.ois.orderservice.model.OrderRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private final OrderRepository orderRepository;

    private final RestClient restClient;
	
	public OrderServiceImpl(OrderRepository orderRepository, RestClient restClient) {
		this.orderRepository = orderRepository;
        this.restClient = restClient;
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
            throw new OrderNotFoundException("OrderID not found " + orderId.toString());
        }

        return getOrderRestFromOrderEntity(orderById);
	}

	private OrderRest getOrderRestFromOrderEntity(OrderEntity orderEntity) {
		return new OrderRest(
								orderEntity.getId(),
								orderEntity.getItemId(),
								orderEntity.getItemName(),
								orderEntity.getQuantity(),
								orderEntity.getItemPrice(),
								orderEntity.getOrderName()
				);
	}
	
	private OrderEntity getOrderEntityFromOrderRest(OrderRest orderRest) {
		return new OrderEntity(orderRest.getOrderId(),
				orderRest.getItemId(),
                orderRest.getItemName(),
				orderRest.getOrderName(),
				orderRest.getQuantity(),
				orderRest.getPrice());
	}
	
	

	@Override
	public OrderRest createOrder(CreateOrderRequest createOrderRequest) {
        String inventoryServiceUrl = "http://localhost:8090/items/{id}";

        ResponseEntity<OrderRest> response = restClient.get()
                .uri(inventoryServiceUrl, createOrderRequest.getItemId())
                .retrieve()
                .toEntity(OrderRest.class);

        System.out.println("Response body from inventory service: " + response.getBody());
        OrderRest tmpOrderRestObj = response.getBody();

		// success depends on the itemId and the quantity (inventory service has to have enough items)
//		int itemPrice = 10000;
//		String itemName = "Fixed item name (from POST req to OrderService)";
		
		OrderRest orderRest = new OrderRest(
				UUID.randomUUID(),
				createOrderRequest.getItemId(),
				tmpOrderRestObj.getItemName(),
				createOrderRequest.getQuantity(),
				tmpOrderRestObj.getPrice(),
				createOrderRequest.getOrderName()
				);

        return getOrderRestFromOrderEntity(orderRepository.save(getOrderEntityFromOrderRest(orderRest)));
	}

	
	
}
