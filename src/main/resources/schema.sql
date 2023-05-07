CREATE SCHEMA IF NOT EXISTS ecl;

CREATE TABLE IF NOT EXISTS ecl.gift_certificates (
    id bigserial PRIMARY KEY,
    name character varying (20) NOT NULL UNIQUE,
    description character varying (50) NOT NULL,
    price numeric NOT NULL,
    duration bigint NOT NULL,
    create_date character varying (30) NOT NULL,
    last_update_date character varying (30) NOT NULL
);

CREATE TABLE IF NOT EXISTS ecl.tags (
    id bigserial PRIMARY KEY,
    name character varying (20) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS ecl.gift_certificates_tags (
    id bigserial PRIMARY KEY,
    gift_certificate_id bigserial NOT NULL,
    tag_id bigserial NOT NULL,
    CONSTRAINT gift_certificates_fk FOREIGN KEY (gift_certificate_id) REFERENCES ecl.gift_certificates(id),
    CONSTRAINT tags_fk FOREIGN KEY (tag_id) REFERENCES ecl.tags(id)
);

CREATE TABLE IF NOT EXISTS ecl.users (
    id bigserial PRIMARY KEY,
    username character varying (20) NOT NULL
);

CREATE TABLE IF NOT EXISTS ecl.orders (
    id bigserial PRIMARY KEY,
    user_id bigint NOT NULL,
    cost numeric NOT NULL,
    create_date character varying (30) NOT NULL,
    status character varying (10) NOT NULL,
    CONSTRAINT orders_fk FOREIGN KEY (user_id) REFERENCES ecl.users(id)
);

CREATE TABLE IF NOT EXISTS ecl.gift_certificates_orders (
     id bigserial PRIMARY KEY,
     order_id bigserial,
     gift_certificate_id bigserial,
     CONSTRAINT gift_certificates_fk FOREIGN KEY (gift_certificate_id) REFERENCES ecl.gift_certificates(id),
     CONSTRAINT orders_fk FOREIGN KEY (order_id) REFERENCES ecl.orders(id)
    );






