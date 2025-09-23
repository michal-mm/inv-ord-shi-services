package com.michal_mm.ois.orderservice.controller;

import com.michal_mm.ois.orderservice.data.OrderEntity;
import com.michal_mm.ois.orderservice.data.OrderRepository;
import com.michal_mm.ois.orderservice.exception.OrderNotFoundException;
import com.michal_mm.ois.orderservice.model.CreateOrderRequest;
import com.michal_mm.ois.orderservice.model.OrderRest;
import com.michal_mm.ois.orderservice.service.OrderServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.RequestHeadersUriSpec;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderControllerTest {

    public static final UUID ORDER_ID = UUID.randomUUID();
    public static final UUID ITEM_ID = UUID.randomUUID();
    public static final String ITEM_NAME = "Unit test item name";
    public static final String ORDER_NAME = "Unit test order name";
    public static final Integer QUANTITY = 125;
    public static final Integer ITEM_PRICE = 5;

    private OrderController orderController;

	@Mock
	private OrderRepository orderRepository;

    @Mock
    private RestClient restClient;
	
	@BeforeEach
	void setUp() {
		orderController = new OrderController(new OrderServiceImpl(orderRepository, restClient));
	}
	
	@Test
	public void testGetAllOrders_simpleGETScenario_returnsValidOrderRest() {
		// Arrange 
		OrderEntity orderEntity = getValidOrderEntity();
		
		// Act
		when(orderRepository.findAll()).thenReturn(List.of(orderEntity));
	
		List<OrderRest> orders = orderController.getAllOrders();
		
		// Assert
		assertEquals(1, orders.size());
		assertEquals(ORDER_ID, orders.getFirst().getOrderId());
	}



    @Test
	public void testGetOrderById_withValidOrderId_returnsValidOrderRest() {
		// Arrange
		OrderEntity orderEntity = getValidOrderEntity();
		
		// Act
		when(orderRepository.findOrderById(ORDER_ID)).thenReturn(orderEntity);
		OrderRest orderRest = orderController.getOrderById(ORDER_ID);
		
		// Assert
        assertThat(orderRest, allOf(
                notNullValue(),
                hasProperty("orderId", equalTo(orderEntity.getId())),
                hasProperty("itemId", equalTo(orderEntity.getItemId())),
                hasProperty("orderName", equalTo(orderEntity.getOrderName())),
                hasProperty("itemName", equalTo(orderEntity.getItemName()))
        ));
	}

    @Test
    public void testGetOrderById_withInvalidOrderId_handlesException() {
        // Arrange
        when(orderRepository.findOrderById(ORDER_ID)).thenReturn(null);

        // Act
        assertThrows(OrderNotFoundException.class,
                () -> orderController.getOrderById(ORDER_ID));
    }
	
	@Test
	public void testCreateOrder_withValidCreateOrderRequest_returnsValidOrderRest() {
		// Arrange
        CreateOrderRequest createOrderRequest = getValidCreateOrderRequest();
		OrderEntity orderEntity = getValidOrderEntity();
        OrderRest orderRestExpected = getValidOrderRest();
        ResponseEntity<OrderRest> responseEntity = ResponseEntity.of(Optional.of(orderRestExpected));
		
		// Act
		when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);

        mockSuccessfulCallToInventoryService(responseEntity);

		OrderRest savedRest = orderController.createOrder(createOrderRequest);
		
		// Assert
        verify(orderRepository).save(argThat(entity ->
                entity.getItemId().equals(createOrderRequest.getItemId()) &&
                        entity.getOrderName().equals(createOrderRequest.getOrderName()) &&
                        entity.getQuantity().equals(createOrderRequest.getQuantity())
        ));

		assertNotNull(savedRest);
		assertEquals(ITEM_ID.toString(), savedRest.getItemId().toString());
		assertEquals(ORDER_NAME, savedRest.getOrderName());
        assertEquals(ITEM_NAME, savedRest.getItemName());
		assertEquals(ORDER_ID.toString(), savedRest.getOrderId().toString());
	}

    @Test
    public void testCreateOrder_withInvalidItemId_throwsHttpClientErrorExceptionNotFound() {
        // Arrange
        CreateOrderRequest createOrderRequest = getValidCreateOrderRequest();
        OrderEntity orderEntity = getValidOrderEntity();

        // we set this mock to prove that order was not saved because of an exception
        when(orderRepository.save(any(OrderEntity.class))).thenReturn(orderEntity);

        mockUnsuccessfulCallToInventoryService_ItemNotFound();

        // Assert
        assertThrows(HttpClientErrorException.NotFound.class, () ->
                orderController.createOrder(createOrderRequest));
        verifyNoInteractions(orderRepository);
    }

    private void mockUnsuccessfulCallToInventoryService_ItemNotFound() {
        RequestHeadersUriSpec reqHeaders = Mockito.mock(RequestHeadersUriSpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);

        when(restClient.get()).thenReturn(reqHeaders);
        when(reqHeaders.uri(any(), (Object) any())).thenReturn(reqHeaders);
        when(reqHeaders.retrieve()).thenReturn(responseSpec);
        when(responseSpec.toEntity(OrderRest.class)).thenThrow(HttpClientErrorException.NotFound.class);
    }

    private void mockSuccessfulCallToInventoryService(ResponseEntity<OrderRest> responseEntity) {
        RequestHeadersUriSpec reqHeaders = mock(RequestHeadersUriSpec.class);
        RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);

        when(restClient.get()).thenReturn(reqHeaders);
        when(reqHeaders.uri(any(), (Object) any())).thenReturn(reqHeaders);
        when(reqHeaders.retrieve()).thenReturn(responseSpec);
        when(responseSpec.toEntity(OrderRest.class)).thenReturn(responseEntity);
    }

    @NotNull
    private static OrderRest getValidOrderRest() {
        return new OrderRest(ORDER_ID, ITEM_ID, ITEM_NAME, QUANTITY, ITEM_PRICE, ORDER_NAME);
    }

    @NotNull
    private static CreateOrderRequest getValidCreateOrderRequest() {
        return new CreateOrderRequest(ITEM_ID, ORDER_NAME, QUANTITY);
    }

    @NotNull
    private static OrderEntity getValidOrderEntity() {
        return new OrderEntity(ORDER_ID, ITEM_ID,
                ITEM_NAME,
                ORDER_NAME,
                QUANTITY,
                ITEM_PRICE);
    }
}
