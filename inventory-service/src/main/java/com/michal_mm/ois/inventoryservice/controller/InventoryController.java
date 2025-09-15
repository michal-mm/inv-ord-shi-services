package com.michal_mm.ois.inventoryservice.controller;

import com.michal_mm.ois.inventoryservice.data.ItemEntity;
import com.michal_mm.ois.inventoryservice.data.ItemRepository;
import com.michal_mm.ois.inventoryservice.model.CreateItemRequest;
import com.michal_mm.ois.inventoryservice.model.ItemRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/items")
public class InventoryController {

    @Autowired
    private ItemRepository repository;

 ;   @GetMapping
    public List<ItemRest> getAllItems() {
     // TODO - REFACTOR (extract to service class implementation)
        return repository.findAll().stream()
                .map(itemEntity -> itemEntity2ItemRest(itemEntity))
                .toList();
    }

    @GetMapping("/{itemId}")
    public ItemRest getItemById(@PathVariable UUID itemId) {
        // TODO - REFACTOR (extract to service class implementation)
        return itemEntity2ItemRest(repository.findItemByItemId(itemId));
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
    public ItemRest updateItemDetails(@PathVariable UUID itemId,
                                      @RequestParam("price") Optional<Integer> price,
                                      @RequestParam("amount") Optional<Integer> amount) {
        // TODO - implement method body

        return new ItemRest(itemId,
                "Update API - Item name doesn't change, PRICE CHANGES",
                amount.orElse(9999),
                price.orElse(777));
    }

    private ItemRest itemEntity2ItemRest(ItemEntity itemEntity) {
        return new ItemRest(itemEntity.getItemId(),
                itemEntity.getItemName(),
                itemEntity.getAmount(),
                itemEntity.getPrice());
    }
}
