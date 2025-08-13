package com.michal_mm.ois.orderservice.data;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="orders")
public class OrderEntity {

	@Id
	private UUID orderId;
	
	@Column(name="item_id")
	private UUID itemId;
	
	@Column(name="order_name")
	private String orderName;
	
	@Column(name="quantity")
	private Integer quantity;
	

	public OrderEntity() {}


	public OrderEntity(UUID orderId, UUID itemId, String orderName, Integer quantity) {
		super();
		this.orderId = orderId;
		this.itemId = itemId;
		this.orderName = orderName;
		this.quantity = quantity;
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
