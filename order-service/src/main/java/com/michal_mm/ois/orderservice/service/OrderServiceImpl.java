package com.michal_mm.ois.orderservice.service;

import com.michal_mm.ois.orderservice.data.OrderEntity;
import com.michal_mm.ois.orderservice.data.OrderRepository;
import com.michal_mm.ois.orderservice.exception.NotEnoughItemsInInventoryException;
import com.michal_mm.ois.orderservice.exception.OrderNotFoundException;
import com.michal_mm.ois.orderservice.model.CreateOrderRequest;
import com.michal_mm.ois.orderservice.model.ItemRestDTO;
import com.michal_mm.ois.orderservice.model.OrderRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.random.RandomGenerator;

@Service
public class OrderServiceImpl implements OrderService {

    public static final String URL_INVENTORY_SERVICE = "http://localhost:8090/items";
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
        String inventoryServiceUrl = URL_INVENTORY_SERVICE + "/{id}";

        ResponseEntity<ItemRestDTO> response = restClient.get()
                .uri(inventoryServiceUrl, createOrderRequest.getItemId())
                .retrieve()
                .toEntity(ItemRestDTO.class);

        ItemRestDTO tmpItemRestDTO = response.getBody();

		// success depends on the itemId and the quantity (inventory service has to have enough items)
        verifyIfEnoughItemsAndUpdateTheInventory(createOrderRequest, tmpItemRestDTO);

        OrderRest orderRest = new OrderRest(
				UUID.randomUUID(),
				createOrderRequest.getItemId(),
				Objects.requireNonNull(tmpItemRestDTO).itemName(),
				createOrderRequest.getQuantity(),
				tmpItemRestDTO.price(),
				createOrderRequest.getOrderName()
				);

        return getOrderRestFromOrderEntity(orderRepository.save(getOrderEntityFromOrderRest(orderRest)));
	}

    public void doSimpleJob(String anArgument) throws InterruptedException {
        long millSecsOfSleep = RandomGenerator.getDefault().nextInt(10000);
        System.out.println("Falling asleep for " + millSecsOfSleep + "ms before executing [" + anArgument + "]");
        Thread.sleep(millSecsOfSleep);
        System.out.println("This is an easy job triggered by JobRunr - " + anArgument);
    }

    private void verifyIfEnoughItemsAndUpdateTheInventory(CreateOrderRequest createOrderRequest,
                                                          ItemRestDTO tmpItemRestDTO) {
        verifyEnoughItems(createOrderRequest, tmpItemRestDTO);
        updateInventoryWithOrder(createOrderRequest.getItemId(),
                tmpItemRestDTO.amount()-createOrderRequest.getQuantity());
    }

    private void updateInventoryWithOrder(UUID itemId, Integer updatedAmount) {
        String inventoryServiceUrl = URL_INVENTORY_SERVICE + "/{id}";

        URI uri = UriComponentsBuilder.fromUriString(inventoryServiceUrl)
                .queryParam("amount", updatedAmount)
                .buildAndExpand(itemId)
                .toUri();

        restClient.patch()
                .uri(uri)
                .retrieve()
                .toEntity(ItemRestDTO.class);
    }

    private void verifyEnoughItems(CreateOrderRequest createOrderRequest, ItemRestDTO tmpItemRestDTO) {
        if (tmpItemRestDTO.amount() < createOrderRequest.getQuantity()) {
            throw new NotEnoughItemsInInventoryException("Not enough items "
                    + "item_id:[" + tmpItemRestDTO.itemId() + "] "
                    + tmpItemRestDTO.itemName() + "="
                    + tmpItemRestDTO.amount() + " in inventory");
        }
    }


}
