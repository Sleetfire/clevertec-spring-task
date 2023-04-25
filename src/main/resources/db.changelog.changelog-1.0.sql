--liquibase formatted sql

--changeset create_tags_table:add-tag-name-constraint

CREATE SCHEMA ecl;

CREATE TABLE ecl.tags
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

--changeset create_users_table:add-user-name-constraint

CREATE TABLE ecl.users
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(20) NOT NULL
);

--changeset create_gift_certificates_table:add-gift-certificates-name-constraint

CREATE TABLE ecl.gift_certificates
(
    id               BIGSERIAL PRIMARY KEY,
    "name"           VARCHAR(20) NOT NULL UNIQUE,
    description      VARCHAR(50) NOT NULL,
    price            NUMERIC     NOT NULL,
    duration         BIGINT      NOT NULL,
    create_date      VARCHAR(30) NOT NULL,
    last_update_date VARCHAR(30) NOT NULL
);

--changeset create_gift_certificates_tags_table:add-gift-certificates-tags-name-constraint

CREATE TABLE ecl.gift_certificates_tags
(
    id                  BIGSERIAL PRIMARY KEY,
    gift_certificate_id BIGSERIAL NOT NULL,
    tag_id              BIGSERIAL NOT NULL,
    CONSTRAINT gift_certificates_fk FOREIGN KEY (gift_certificate_id) REFERENCES ecl.gift_certificates (id),
    CONSTRAINT tags_fk FOREIGN KEY (tag_id) REFERENCES ecl.tags (id)
);

--changeset create_orders_table:add-orders-name-constraint

CREATE TABLE ecl.orders
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT      NOT NULL,
    cost        NUMERIC     NOT NULL,
    create_date VARCHAR(30) NOT NULL,
    status      VARCHAR(10) NOT NULL,
    CONSTRAINT orders_fk FOREIGN KEY (user_id) REFERENCES ecl.users (id)
);

--changeset create_gift_certificates_orders_table:add-gift-certificates-orders-name-constraint

CREATE TABLE ecl.gift_certificates_orders
(
    id                  BIGSERIAL PRIMARY KEY,
    order_id            BIGSERIAL,
    gift_certificate_id BIGSERIAL,
    CONSTRAINT gift_certificates_fk FOREIGN KEY (gift_certificate_id) REFERENCES ecl.gift_certificates (id),
    CONSTRAINT orders_fk FOREIGN KEY (order_id) REFERENCES ecl.orders (id)
);