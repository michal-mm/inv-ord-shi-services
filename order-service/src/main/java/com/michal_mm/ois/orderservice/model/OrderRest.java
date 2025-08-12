package com.michal_mm.ois.orderservice.model;

import java.util.UUID;

public class OrderRest {

	private UUID orderId;

	private UUID itemId;
	
	private String itemName;
	
	private Integer price;
	
	private String orderName;
	
	
	public OrderRest(){}


	public OrderRest(UUID orderId, UUID itemId,
			String itemName, Integer price, String orderName) {
		this.orderId = orderId;
		this.itemId = itemId;
		this.itemName = itemName;
		this.price = price;
		this.orderName = orderName;
	}


	public UUID getOrderId() {
		return orderId;
	}


	public void setOrderId(UUID orderId) {
		this.orderId = orderId;
	}


	public UUID getItemId() {
		return itemId;
	}


	public void setItemId(UUID itemId) {
		this.itemId = itemId;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public Integer getPrice() {
		return price;
	}


	public void setPrice(Integer price) {
		this.price = price;
	}


	public String getOrderName() {
		return orderName;
	}


	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}


	@Override
	public String toString() {
		return "OrderRest [orderId=" + orderId + ", itemId=" + itemId + ", itemName=" + itemName + ", price=" + price
				+ ", orderName=" + orderName + "]";
	}
}
