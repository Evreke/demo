create table roles
(
    id    bigint auto_increment primary key,
    title varchar
);

create table users
(
    id           bigint auto_increment primary key,
    username     varchar,
    phone_number varchar,
    created_at   timestamp default now(),
    role_id      bigint,
    constraint fk_role_id foreign key (role_id) references roles (id)
);

