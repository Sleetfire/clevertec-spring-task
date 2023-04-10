CREATE SCHEMA IF NOT EXISTS ecl;

CREATE TABLE IF NOT EXISTS ecl.gift_certificates (
    id bigserial PRIMARY KEY,
    name character varying (20) NOT NULL,
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
    gift_certificate_id bigserial,
    tag_id bigserial,
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
     gift_certificate_id bigserial,
     order_id bigserial,
     CONSTRAINT gift_certificates_fk FOREIGN KEY (gift_certificate_id) REFERENCES ecl.gift_certificates(id),
     CONSTRAINT orders_fk FOREIGN KEY (order_id) REFERENCES ecl.orders(id)
    );

CREATE OR REPLACE FUNCTION pattern_find_certificates(argument CHARACTER) RETURNS TABLE(id BIGINT, name CHARACTER VARYING, description CHARACTER VARYING,
                                                                                       price DECIMAL, duration BIGINT, create_date CHARACTER VARYING,
                                                                                       last_update_date CHARACTER VARYING, tag_id BIGINT,
                                                                                       tag_name CHARACTER VARYING)
    COST 10
    ROWS 100
    LANGUAGE plpgsql
as $$
BEGIN
    RETURN QUERY
        SELECT gift_certificates.id, gift_certificates.name, ecl.gift_certificates.description, ecl.gift_certificates.price,
               ecl.gift_certificates.duration, ecl.gift_certificates.create_date, ecl.gift_certificates.last_update_date,
               tags.id as tag_id, tags.name as tag_name
        FROM ecl.gift_certificates
                 LEFT JOIN ecl.gift_certificates_tags ON ecl.gift_certificates.id = ecl.gift_certificates_tags.gift_certificate_id
                 LEFT JOIN ecl.tags ON ecl.gift_certificates_tags.tag_id = ecl.tags.id
        WHERE (gift_certificates.name LIKE (argument) OR ecl.gift_certificates.description LIKE (argument));
END
$$;

CREATE OR REPLACE FUNCTION find_certificates() RETURNS TABLE(id BIGINT, name CHARACTER VARYING, description CHARACTER VARYING,
                                                             price DECIMAL, duration BIGINT, create_date CHARACTER VARYING,
                                                             last_update_date CHARACTER VARYING, tag_id BIGINT,
                                                             tag_name CHARACTER VARYING)
    COST 10
    ROWS 100
    LANGUAGE plpgsql
as $$
BEGIN
    RETURN QUERY
        SELECT gift_certificates.id, gift_certificates.name, ecl.gift_certificates.description, ecl.gift_certificates.price,
               ecl.gift_certificates.duration, ecl.gift_certificates.create_date, ecl.gift_certificates.last_update_date,
               tags.id as tag_id, tags.name as tag_name
        FROM ecl.gift_certificates
                 LEFT JOIN ecl.gift_certificates_tags ON ecl.gift_certificates.id = ecl.gift_certificates_tags.gift_certificate_id
                 LEFT JOIN ecl.tags ON ecl.gift_certificates_tags.tag_id = ecl.tags.id;
END
$$;





