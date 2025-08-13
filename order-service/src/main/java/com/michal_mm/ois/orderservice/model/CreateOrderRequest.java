package com.michal_mm.ois.orderservice.model;

import java.util.UUID;



public class CreateOrderRequest {

	// TODO - input validation rules missing!
	private UUID itemId;
	
	private String orderName;
	
	private Integer quantity;
	
	
	public CreateOrderRequest() {}


	public CreateOrderRequest(UUID itemId, String orderName, Integer quantity) {
		this.itemId = itemId;
		this.orderName = orderName;
		this.quantity = quantity;
	}


	public UUID getItemId() {
		return itemId;
	}


	public void setItemId(UUID itemId) {
		this.itemId = itemId;
	}


	public String getOrderName() {
		return orderName;
	}


	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
}
