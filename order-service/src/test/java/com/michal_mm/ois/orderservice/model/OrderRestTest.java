package com.michal_mm.ois.orderservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {OrderRestTest.class})
public class OrderRestTest {

	@Test
	public void testSimpleOrderRestObject () {
		UUID orderId = UUID.randomUUID();
		UUID itemId = UUID.randomUUID();
		String itemName = "Test Item";
		Integer price = 1234;
		Integer quantity = 5;
		String orderName = "Test Order Name";
		
		String toString = "OrderRest [orderId=" + orderId 
				+ ", itemId=" + itemId + ", itemName=" 
				+ itemName + ", price=" + price
				+ ", quantity=" + quantity
				+ ", orderName=" + orderName + "]";
		
		OrderRest orderRest = new OrderRest(orderId,
				itemId, itemName, quantity, price, orderName);
		
		assertNotNull(orderRest);
		assertEquals(orderId, orderRest.getOrderId());
		assertEquals(itemId, orderRest.getItemId());
		assertEquals(itemName, orderRest.getItemName());
		assertEquals(price, orderRest.getPrice());
		assertEquals(quantity, orderRest.getQuantity());
		assertEquals(orderName, orderRest.getOrderName());
		assertEquals(toString, orderRest.toString());
	}
	
	@Test
	public void testSimpleOrderRestObjectWithSetters () {
		UUID orderId = UUID.randomUUID();
		UUID itemId = UUID.randomUUID();
		String itemName = "Test Item";
		Integer price = 1234;
		Integer quantity = 10;
		String orderName = "Test Order Name";
		
		OrderRest orderRest = new OrderRest();
		orderRest.setOrderId(orderId);
		orderRest.setItemId(itemId);
		orderRest.setItemName(itemName);
		orderRest.setPrice(price);
		orderRest.setQuantity(quantity);
		orderRest.setOrderName(orderName);
		
		assertNotNull(orderRest);
		assertEquals(orderId, orderRest.getOrderId());
		assertEquals(itemId, orderRest.getItemId());
		assertEquals(itemName, orderRest.getItemName());
		assertEquals(price, orderRest.getPrice());
		assertEquals(quantity, orderRest.getQuantity());
		assertEquals(orderName, orderRest.getOrderName());
	}
}
