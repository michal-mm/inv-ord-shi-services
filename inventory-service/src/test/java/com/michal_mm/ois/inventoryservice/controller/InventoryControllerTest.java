package com.michal_mm.ois.inventoryservice.controller;

import com.michal_mm.ois.inventoryservice.data.ItemEntity;
import com.michal_mm.ois.inventoryservice.data.ItemRepository;
import com.michal_mm.ois.inventoryservice.model.CreateItemRequest;
import com.michal_mm.ois.inventoryservice.model.ItemRest;
import com.michal_mm.ois.inventoryservice.service.InventoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class InventoryControllerTest {

    private InventoryController inventoryController;

    @Mock
    private ItemRepository repository;

    @BeforeEach
    void setUp() {
        inventoryController = new InventoryController(new InventoryServiceImpl(repository));
    }

    @Test
    void getAllItems() {
        // Arrange

        // prepare expected output
        UUID itemId = UUID.randomUUID();
        String itemName = "Junit item name";
        Integer amount = 111;
        Integer price = 9999;
        ItemRest itemRest = new ItemRest(itemId, itemName, amount, price);
        List<ItemRest> expectedListOfItemRest = List.of(itemRest);

        // prepare ItemEntity object
        ItemEntity itemEntity = new ItemEntity(itemId, itemName, amount, price);
        List<ItemEntity> itemEntityList = List.of(itemEntity);

        // mock repo calls
        when(repository.findAll()).thenReturn(itemEntityList);

        // Act
        List<ItemRest> allItems = inventoryController.getAllItems();

        // Assert
        assertNotNull(allItems);
        assertEquals(expectedListOfItemRest.size(), allItems.size());
        for (int i=0; i<expectedListOfItemRest.size(); i++) {
            ItemRest expectedItem = expectedListOfItemRest.get(i);
            ItemRest returnedItem = allItems.get(i);
            assertEquals(expectedItem.getItemId().toString(), returnedItem.getItemId().toString());
            assertEquals(expectedItem.getItemName(), returnedItem.getItemName());
            assertEquals(expectedItem.getAmount(), returnedItem.getAmount());
            assertEquals(expectedItem.getPrice(), returnedItem.getPrice());
            assertEquals(expectedItem.toString(), returnedItem.toString());
        }
    }

    @Test
    void getItemById() {
        // Arrange
        // prepare expected output
        UUID itemId = UUID.randomUUID();
        String itemName = "Junit item name";
        Integer amount = 111;
        Integer price = 9999;
        ItemRest expectedItemRest = new ItemRest(itemId, itemName, amount, price);

        // prepare mocked itemEntity response
        ItemEntity itemEntity = new ItemEntity(itemId, itemName, amount, price);

        // mock repo calls
        when(repository.findItemByItemId(itemId)).thenReturn(itemEntity);

        // Act
        ItemRest outputItemRest = inventoryController.getItemById(itemId);

        // Assert
        assertNotNull(outputItemRest);
        assertEquals(expectedItemRest.getItemId().toString(), outputItemRest.getItemId().toString());
        assertEquals(expectedItemRest.getItemName(), outputItemRest.getItemName());
        assertEquals(expectedItemRest.getAmount(), outputItemRest.getAmount());
        assertEquals(expectedItemRest.getPrice(), outputItemRest.getPrice());
    }

    @Test
    void createNewItem() {
        // Arrange
        // prepare expected output
        UUID itemId = UUID.randomUUID();
        String itemName = "Junit item name";
        Integer amount = 111;
        Integer price = 9999;
        ItemRest expectedItemRest = new ItemRest(itemId, itemName, amount, price);

        // prepare CreateItemRequest object
        CreateItemRequest createItemRequest = new CreateItemRequest(itemName, amount, price);

        // prepare mocked itemEntity to be created
        ItemEntity itemEntity = new ItemEntity(itemId, itemName, amount, price);

        // Act
        when(repository.save(any())).thenReturn(itemEntity);
        ItemRest returnedCreatedItemRest = inventoryController.createNewItem(createItemRequest);

        // Assert
        assertNotNull(returnedCreatedItemRest);
        assertEquals(expectedItemRest.getItemId(), returnedCreatedItemRest.getItemId());
        assertEquals(expectedItemRest.getItemName(), returnedCreatedItemRest.getItemName());
        assertEquals(expectedItemRest.getAmount(), returnedCreatedItemRest.getAmount());
        assertEquals(expectedItemRest.getPrice(), returnedCreatedItemRest.getPrice());
    }

    @Test
    void updateItemDetails_withUpdateOfPriceAndAmount() {
        // Arrange
        Integer forUpdate = 10000;
        // prepare expected output
        UUID itemId = UUID.randomUUID();
        String itemName = "Junit item name";
        Integer amount = 111;
        Integer price = 9999;
        ItemRest expectedItemRest = new ItemRest(itemId, itemName, amount+forUpdate, price+forUpdate);

        // create ItemEntity object for mocked response
        ItemEntity mockedItemEntity = new ItemEntity(itemId, itemName, amount, price);
        ItemEntity mockedItemEntityWithUpdates = new ItemEntity(itemId, itemName, amount+forUpdate, price+forUpdate);

        // optional price and amount
        Optional<Integer> optionalAmount = Optional.of(amount+forUpdate);
        Optional<Integer> optionalPrice = Optional.of(price+forUpdate);

        // Act
        when(repository.findItemByItemId(itemId)).thenReturn(mockedItemEntity);
        when(repository.save(any())).thenReturn(mockedItemEntityWithUpdates);
        ItemRest returnedUpdatedItemRest = inventoryController.updateItemDetails(itemId, optionalPrice, optionalAmount);

        // Assert
        assertNotNull(returnedUpdatedItemRest);
        assertEquals(expectedItemRest.getItemId(), returnedUpdatedItemRest.getItemId());
        assertEquals(expectedItemRest.getItemName(), returnedUpdatedItemRest.getItemName());
        assertEquals(expectedItemRest.getPrice(), returnedUpdatedItemRest.getPrice());
        assertEquals(expectedItemRest.getAmount(), returnedUpdatedItemRest.getAmount());
    }
}