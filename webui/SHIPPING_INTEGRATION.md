# Shipping Service Integration

This document describes the shipping service integration added to the WebUI.

## New Features Added

### 1. API Configuration
- Added shipping service base URL configuration (default: http://localhost:8091)
- All three microservices can now be configured independently

### 2. Shipment Management
- **Create Shipment**: Create shipments for existing orders
- **View Shipments**: Display all shipments with status, costs, and delivery information
- **Search Shipments**: Find shipments by ID or order ID
- **Status Updates**: Update shipment status through the workflow
- **Status Filtering**: Filter shipments by status (PENDING, PROCESSING, SHIPPED, DELIVERED)

### 3. Order Integration
- **Create Shipment Button**: Direct shipment creation from order cards
- **Check Shipment Button**: View shipment status for specific orders
- **Pre-filled Forms**: Order details automatically populate shipment creation form

### 4. Visual Enhancements
- **Status Badges**: Color-coded status indicators
- **Cost Display**: Proper dollar formatting for order totals and shipping costs
- **Date Formatting**: User-friendly date displays for delivery estimates
- **Address Display**: Clear shipping address presentation

## How to Use

### Creating a Shipment
1. **From Order**: Click "🚚 Create Shipment" on any order card
2. **Manual**: Use the "Create New Shipment" section
   - Enter Order ID (UUID)
   - Enter Order Total (in cents)
   - Enter Shipping Address
   - Click "Create Shipment"

### Managing Shipments
1. **View All**: Click "🔄 Refresh Shipments"
2. **Search by ID**: Click "🔍 Get Shipment by ID"
3. **Search by Order**: Click "📋 Get by Order ID"
4. **Filter by Status**: Use the status dropdown filter
5. **Update Status**: Click "📝 Update Status" on any shipment card

### Status Workflow
Shipments follow this status progression:
- **PENDING** → **PROCESSING** → **SHIPPED** → **DELIVERED**

### Error Handling
- Service unavailability notifications
- Invalid data validation
- Clear error messages for failed operations
- Retry mechanisms for network issues

## API Integration

The WebUI integrates with the shipping service using these endpoints:
- `GET /shipments` - List all shipments
- `GET /shipments/{id}` - Get shipment by ID
- `GET /shipments/order/{orderId}` - Get shipment by order ID
- `POST /shipments` - Create new shipment
- `PATCH /shipments/{id}/status` - Update shipment status

## Testing the Integration

1. Start all services:
   - Inventory Service (port 8090)
   - Order Service (port 8080)
   - Shipping Service (port 8091)
   - WebUI (port 8999)

2. Complete workflow test:
   - Create inventory items
   - Create orders for items
   - Create shipments for orders
   - Update shipment status
   - Verify cross-service integration

## Business Logic

### Shipping Cost Calculation
- Automatically calculated as 10% of order total
- Minimum shipping cost: $5.00
- Displayed in user-friendly dollar format

### Delivery Estimation
- Fixed 5-day delivery window from shipment creation
- Displayed as estimated delivery date

This integration demonstrates complete microservices coordination where users can manage the entire order fulfillment lifecycle from a single interface.