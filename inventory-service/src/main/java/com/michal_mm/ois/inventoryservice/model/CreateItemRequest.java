package com.michal_mm.ois.inventoryservice.model;

public class CreateItemRequest {

    private String itemName;

    private Integer amount;

    private Integer price;

    public CreateItemRequest(){}

    public CreateItemRequest(String itemName, Integer amount, Integer price) {
        this.itemName = itemName;
        this.amount = amount;
        this.price = price;
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
        return "CreateItemRequest{" +
                "itemName='" + itemName + '\'' +
                ", amoutn=" + amount +
                ", price=" + price +
                '}';
    }
}
