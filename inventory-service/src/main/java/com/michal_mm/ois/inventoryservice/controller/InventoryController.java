package com.michal_mm.ois.inventoryservice.controller;

import com.michal_mm.ois.inventoryservice.model.CreateItemRequest;
import com.michal_mm.ois.inventoryservice.model.ItemRest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/items")
public class InventoryController {

    @GetMapping
    public List<ItemRest> getAllItems() {
        // TODO - implement
        return List.of(new ItemRest(UUID.randomUUID(), "First test fixed item", 10, 100),
                new ItemRest(UUID.randomUUID(), "Second fixed test item", 20, 200));
    }

    @GetMapping("/{itemId}")
    public ItemRest getItemById(@PathVariable UUID itemId) {
        // TODO - implement method
        return new ItemRest(itemId, "Single fixed item", 5, 555);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemRest createNewItem(@RequestBody CreateItemRequest createItemRequest) {
        // TODO - implement method
        return new ItemRest(UUID.randomUUID(),
                createItemRequest.getItemName(),
                createItemRequest.getAmount(),
                createItemRequest.getPrice());
    }

    @PatchMapping("/{itemId}")
    public ItemRest updateItemDetals(@PathVariable UUID itemId,
                                     @RequestParam("price") Optional<Integer> price,
                                     @RequestParam("amount") Optional<Integer> amount) {
        // TODO - implement method body

        return new ItemRest(itemId,
                "Update API - Item name doesn't change, PRICE CHANGES",
                amount.orElse(9999),
                price.orElse(777));
    }
}
