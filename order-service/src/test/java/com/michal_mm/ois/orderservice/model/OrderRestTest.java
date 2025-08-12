package com.michal_mm.ois.orderservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderRestTest {

	@Test
	public void testSimpleOrderRestObject () {
		UUID orderId = UUID.randomUUID();
		UUID itemId = UUID.randomUUID();
		String itemName = "Test Item";
		Integer price = 1234;
		String orderName = "Test Order Name";
		
		String toString = "OrderRest [orderId=" + orderId 
				+ ", itemId=" + itemId + ", itemName=" 
				+ itemName + ", price=" + price
				+ ", orderName=" + orderName + "]";
		
		OrderRest orderRest = new OrderRest(orderId,
				itemId, itemName, price, orderName);
		
		assertNotNull(orderRest);
		assertEquals(orderId, orderRest.getOrderId());
		assertEquals(itemId, orderRest.getItemId());
		assertEquals(itemName, orderRest.getItemName());
		assertEquals(price, orderRest.getPrice());
		assertEquals(orderName, orderRest.getOrderName());
		assertEquals(toString, orderRest.toString());
	}
	
	@Test
	public void testSimpleOrderRestObjectWithSetters () {
		UUID orderId = UUID.randomUUID();
		UUID itemId = UUID.randomUUID();
		String itemName = "Test Item";
		Integer price = 1234;
		String orderName = "Test Order Name";
		
		OrderRest orderRest = new OrderRest();
		orderRest.setOrderId(orderId);
		orderRest.setItemId(itemId);
		orderRest.setItemName(itemName);
		orderRest.setPrice(price);
		orderRest.setOrderName(orderName);
		
		assertNotNull(orderRest);
		assertEquals(orderId, orderRest.getOrderId());
		assertEquals(itemId, orderRest.getItemId());
		assertEquals(itemName, orderRest.getItemName());
		assertEquals(price, orderRest.getPrice());
		assertEquals(orderName, orderRest.getOrderName());
	}
}
