CREATE SCHEMA IF NOT EXISTS payment_schema;

CREATE TABLE IF NOT EXISTS payment_schema.payments (
    payment_id      VARCHAR(255)   PRIMARY KEY,
    total_payment   NUMERIC(10, 2) NOT NULL,
    product_price   NUMERIC(10, 2) NOT NULL,
    delivery_total  NUMERIC(10, 2) NOT NULL,
    fee_total       NUMERIC(10, 2) NOT NULL,
    status_payment  VARCHAR(10)    NOT NULL,
    order_id        VARCHAR(255)   NOT NULL
);