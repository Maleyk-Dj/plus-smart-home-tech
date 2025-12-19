CREATE SCHEMA IF NOT EXISTS warehouse_schema;

CREATE TABLE IF NOT EXISTS warehouse_schema.products (
    product_id   VARCHAR(255)   PRIMARY KEY,
    fragile      BOOLEAN        NOT NULL,
    width        NUMERIC(10,3)  NOT NULL,
    height       NUMERIC(10,3)  NOT NULL,
    depth        NUMERIC(10,3)  NOT NULL,
    weight       NUMERIC(10,3)  NOT NULL,
    quantity     BIGINT         NOT NULL
);

CREATE TABLE IF NOT EXISTS warehouse_schema.order_bookings (
    id           BIGSERIAL      PRIMARY KEY,
    order_id     VARCHAR(255)   NOT NULL,
    product_id   VARCHAR(255)   NOT NULL,
    delivery_id  VARCHAR(255),
    quantity     BIGINT         NOT NULL
);