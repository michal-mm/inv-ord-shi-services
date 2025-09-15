package com.michal_mm.ois.inventoryservice.model;

import java.util.UUID;

public class ItemRest {
    // TODO
    private UUID itemId;

    private String itemName;

    private Integer amount;

    private Integer price;


    public ItemRest(){}

    public ItemRest(UUID itemId, String itemName, Integer amount, Integer price) {
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
        return "ItemRest{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
