package com.michal_mm.ois.inventoryservice;

import com.michal_mm.ois.inventoryservice.controller.InventoryController;
import com.michal_mm.ois.inventoryservice.exception.ItemNotFoundException;
import com.michal_mm.ois.inventoryservice.model.ItemRest;
import com.michal_mm.ois.inventoryservice.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@WebMvcTest(InventoryController.class)
class InventoryServiceApplicationTests {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private InventoryService mockedInventoryService;


    @Test
    void getAllItems_thenReturnJsonArray() throws Exception {
        // Arrange
        // prepare expected output
        UUID itemId = UUID.randomUUID();
        String itemName = "Junit item name";
        Integer amount = 111;
        Integer price = 9999;
        ItemRest itemRest = new ItemRest(itemId, itemName, amount, price);
        List<ItemRest> expectedListOfItemRest = List.of(itemRest);

        // mock service calls
        when(mockedInventoryService.getAllItems()).thenReturn(expectedListOfItemRest);

        mvc.perform(get("/items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].itemName", is(itemName)));
    }

    @Test
    void getItemById_itemNotFound_getExceptionHandler() throws Exception {
        // Arrange
        UUID itemId = UUID.randomUUID();

        // mock service call
        when(mockedInventoryService.getItemById(itemId)).thenThrow(new ItemNotFoundException("Junit ItemNotFoundException"));

        mvc.perform(get("/items/" + itemId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testNotExistingUri() throws Exception {
        mvc.perform(get("/not-existing-uri")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testCreateItem_withSuccessfulResponse()  {
        // TODO - implement testCreateItem_withSuccessfulResponse
    }

    @Test
    void testUpdateItem_withSuccessfulResponse()  {
        // TODO - testUpdateItem_withSuccessfulResponse
    }
}
