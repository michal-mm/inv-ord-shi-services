/*
CREATE TABLE orders 
(
	order_id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
	item_id  UUID,
	order_name VARCHAR(50) NOT NULL,
	quantity BIGINT NOT NULL
);
*/

INSERT INTO orders (order_id, item_id, order_name, quantity, item_price) 
VALUES ('005fd704-8a42-456a-90aa-8e4168eb3375', '005fd704-8a42-456a-90aa-8e4168eb3375', 'Order number zero', '5', '200');

INSERT INTO orders (order_id, item_id, order_name, quantity, item_price) 
VALUES ('005fd704-8a42-456a-90aa-8e4168eb3376', '005fd704-8a42-456a-90aa-8e4168eb3375', 'Order number zero-B', '15', '200');

