package com.michal_mm.ois.inventoryservice.model;

import java.util.Objects;
import java.util.UUID;

/// {ItemRest} class is used as DTO object to transfer data
/// representing an Item for REST request and responses.
public class ItemRest {

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

    @Override
    public boolean equals(Object o) {
        return switch (o) {
            case ItemRest itemRest when itemRest.getClass() == this.getClass() ->
                    Objects.equals(itemId, itemRest.getItemId()) &&
                            Objects.equals(itemName, itemRest.getItemName()) &&
                            Objects.equals(amount, itemRest.getAmount()) &&
                            Objects.equals(price, itemRest.getPrice());
            case null, default -> false;
        };
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, itemName, amount, price);
    }
}
