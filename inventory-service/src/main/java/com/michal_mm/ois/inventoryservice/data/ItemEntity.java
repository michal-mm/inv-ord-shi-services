package com.michal_mm.ois.inventoryservice.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Table(name="items")
@Entity
public class ItemEntity {

    @Id
    @Column(name="item_id")
    private UUID itemId;

    @Column(name="item_name")
    private String itemName;

    @Column(name="amount")
    private Integer amount;

    @Column(name="price")
    private Integer price;

    public ItemEntity(){}

    public ItemEntity(UUID itemId, String itemName, Integer amount, Integer price) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.amount = amount;
        this.price = price;
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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ItemEntity{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
