create table movies
(
    id       bigint auto_increment primary key,
    title    varchar,
    duration time
);

create table movie_sessions
(
    id         bigint auto_increment primary key,
    movie_id   bigint,
    started_at time,
    ended_at   time,
    constraint fk_movie_id foreign key (movie_id) references movies (id)
);