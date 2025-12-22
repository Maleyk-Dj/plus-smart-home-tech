CREATE SCHEMA IF NOT EXISTS delivery_schema;

CREATE TABLE IF NOT EXISTS delivery_schema.address (
    id        BIGSERIAL      PRIMARY KEY,
    country   VARCHAR(100)   NOT NULL,
    city      VARCHAR(100)   NOT NULL,
    street    VARCHAR(100),
    house     VARCHAR(50),
    flat      VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS delivery_schema.deliveries (
    delivery_id     VARCHAR(255)    NOT NULL   PRIMARY KEY,
    from_address_id BIGSERIAL       REFERENCES delivery_schema.address(id),
    to_address_id   BIGSERIAL       REFERENCES delivery_schema.address(id),
    delivery_volume NUMERIC(15, 3)  NOT NULL,
    delivery_weight NUMERIC(10, 3)  NOT NULL,
    fragile         BOOLEAN         NOT NULL,
    delivery_state  VARCHAR(20)     NOT NULL,
    order_id        VARCHAR(255)    NOT NULL
);