CREATE SCHEMA IF NOT EXISTS order_schema;

CREATE TABLE IF NOT EXISTS order_schema.orders (
    order_id          VARCHAR(255)     NOT NULL       PRIMARY KEY,
    state             VARCHAR(20)      NOT NULL,
    shopping_cart_id  VARCHAR(255)     NOT NULL,
    delivery_id       VARCHAR(255)     NOT NULL,
    payment_id        VARCHAR(255)     NOT NULL,
    delivery_volume   NUMERIC(15, 3),
    delivery_weight   NUMERIC(10, 3),
    fragile           BOOLEAN          DEFAULT FALSE,
    total_price       NUMERIC(10, 2),
    product_price     NUMERIC(10, 2),
    delivery_price    NUMERIC(10, 2),
    user_name         VARCHAR(255)     NOT NULL
);

CREATE TABLE IF NOT EXISTS order_schema.product_quantity (
    id            BIGSERIAL PRIMARY KEY,
    product_id    VARCHAR(255) NOT NULL,
    quantity      BIGINT       NOT NULL,
    order_id      VARCHAR(255) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES order_schema.orders(order_id)
);