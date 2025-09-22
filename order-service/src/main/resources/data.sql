/*
CREATE TABLE orders 
(
	order_id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
	item_id  UUID,
	item_name VARCHAR(50) NOT NULL,
	order_name VARCHAR(50) NOT NULL,
	quantity BIGINT NOT NULL
);
*/

INSERT INTO orders (order_id, item_id, item_name, order_name, quantity, item_price)
VALUES ('005fd704-8a42-456a-90aa-8e4168eb3375', '005fd704-8a42-456a-90aa-8e4168eb3375', 'Item #1', 'Order number zero', '5', '200');

INSERT INTO orders (order_id, item_id, item_name, order_name, quantity, item_price)
VALUES ('005fd704-8a42-456a-90aa-8e4168eb3376', '005fd704-8a42-456a-90aa-8e4168eb3375', 'Item #2 zero-B', 'Order number zero-B', '15', '200');

