create table halls
(
    id       bigint auto_increment primary key,
    name     varchar,
    capacity int
);

alter table if exists movie_sessions
    add column if not exists hall_id bigint;

alter table if exists movie_sessions
    add column if not exists occupancy int default 0;

alter table if exists movie_sessions
    add constraint fk_hall_id foreign key (hall_id) references halls (id);