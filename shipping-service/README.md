# Shipping Service

A Spring Boot microservice for managing shipments and delivery tracking.

## Overview

The Shipping Service is part of the inventory-order-shipping microservices architecture. It handles:
- Creating shipments for orders
- Calculating shipping costs
- Estimating delivery dates
- Tracking shipment status

## Features

- **Shipment Management**: Create and track shipments
- **Cost Calculation**: Automatic shipping cost calculation (10% of order total, minimum $5.00)
- **Delivery Estimation**: 5-day delivery estimate from creation
- **Status Tracking**: PENDING → PROCESSING → SHIPPED → DELIVERED
- **REST API**: Full CRUD operations via REST endpoints
- **CORS Support**: Configured for WebUI integration

## API Endpoints

### Shipments
- `GET /shipments` - Get all shipments
- `GET /shipments/{shippingId}` - Get shipment by ID
- `GET /shipments/order/{orderId}` - Get shipment by order ID
- `POST /shipments` - Create new shipment
- `PATCH /shipments/{shippingId}/status` - Update shipment status

## Data Model

### ShippingEntity
- `shippingId` (UUID) - Primary key
- `orderId` (UUID) - Reference to order
- `orderTotal` (Integer) - Order total in cents
- `shippingCost` (Integer) - Calculated shipping cost in cents
- `estimatedDelivery` (LocalDate) - Estimated delivery date
- `shippingAddress` (String) - Delivery address
- `status` (ShippingStatus) - Current status
- `createdAt` (LocalDateTime) - Creation timestamp
- `updatedAt` (LocalDateTime) - Last update timestamp

### ShippingStatus Enum
- `PENDING` - Shipment created, awaiting processing
- `PROCESSING` - Shipment being prepared
- `SHIPPED` - Shipment in transit
- `DELIVERED` - Shipment delivered

## Configuration

- **Port**: 8091
- **Database**: H2 in-memory (shippingdb)
- **CORS**: Allows requests from http://localhost:8999

## Running the Service

```bash
mvn spring-boot:run
```

## Testing

Run all tests:
```bash
mvn test
```

Test coverage includes:
- Unit tests for service layer (11 tests)
- Controller tests (9 tests)
- Entity tests (3 tests)
- Application context test (1 test)

Total: 24 tests

## Business Logic

### Shipping Cost Calculation
- Base rate: 10% of order total
- Minimum cost: $5.00 (500 cents)
- Example: $10 order = $5.00 shipping (minimum applied)
- Example: $100 order = $10.00 shipping (10% rate)

### Delivery Estimation
- Fixed 5-day delivery window from shipment creation
- Future enhancement: Consider business days, shipping method, location

## Integration Points

Designed to integrate with:
- **Order Service**: Receives shipment creation requests after order confirmation
- **WebUI**: Provides shipment tracking information to customers

## Future Enhancements

- Multiple shipping methods (standard, express, overnight)
- Real-time tracking integration with carriers
- Address validation
- Shipping cost optimization based on location/weight
- Email notifications for status changes
- Async messaging integration (RabbitMQ/Kafka)