insert into ecl.tags (name)
values ('good quality'),
       ('superb'),
       ('food');

select setval('ecl.tags_id_seq', (SELECT MAX(id) FROM ecl.tags));

insert into ecl.users (username)
values ('Itachi'),
       ('Madara');

select setval('ecl.users_id_seq', (SELECT MAX(id) FROM ecl.users));

insert into ecl.gift_certificates ("name", description, price, duration, create_date, last_update_date)
values ('barber-shop', 'good haircut for real men', 25.0, 864000000000000, '2023-04-24T19:14:35.559',
        '2023-04-24T19:14:35.559'),
       ('pizza', 'red price', 16.99, 864000000000000, '2023-04-24T19:14:35.559', '2023-04-24T19:14:35.559');

select setval('ecl.gift_certificates_id_seq', (SELECT MAX(id) FROM ecl.gift_certificates));

insert into ecl.gift_certificates_tags (gift_certificate_id, tag_id)
values ((select id from ecl.gift_certificates where "name" = 'barber-shop'),
        (select id from ecl.tags where "name" = 'good quality')),
       ((select id from ecl.gift_certificates where "name" = 'barber-shop'),
        (select id from ecl.tags where "name" = 'superb')),
       ((select id from ecl.gift_certificates where "name" = 'pizza'),
        (select id from ecl.tags where "name" = 'food'));

select setval('ecl.gift_certificates_tags_id_seq', (SELECT MAX(id) FROM ecl.gift_certificates_tags));

insert into ecl.orders (user_id, "cost", create_date, status)
values ((select id from ecl.users where username = 'Itachi'), 25.0, '2023-04-24T19:14:35.559', 'IN_PROCESS'),
       ((select id from ecl.users where username = 'Madara'), 58.98, '2023-04-24T19:14:35.559', 'IN_PROCESS');

select setval('ecl.orders_id_seq', (SELECT MAX(id) FROM ecl.orders));

insert into ecl.gift_certificates_orders(order_id, gift_certificate_id)
values ((select id from ecl.orders where "cost" = 25.0),
        (select id from ecl.gift_certificates where "name" = 'barber-shop')),
       ((select id from ecl.orders where "cost" = 58.98),
        (select id from ecl.gift_certificates where "name" = 'barber-shop')),
       ((select id from ecl.orders where "cost" = 58.98),
        (select id from ecl.gift_certificates where "name" = 'pizza')),
       ((select id from ecl.orders where "cost" = 58.98),
        (select id from ecl.gift_certificates where "name" = 'pizza'));

select setval('ecl.gift_certificates_orders_id_seq', (SELECT MAX(id) FROM ecl.gift_certificates_orders));