create table categories
(
    id       bigint auto_increment primary key,
    title    varchar,
    discount double precision
);

alter table if exists users
    add column category_id bigint;