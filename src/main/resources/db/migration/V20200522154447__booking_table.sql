create table bookings
(
    id               bigint auto_increment primary key,
    movie_session_id bigint,
    user_id          bigint,
    created_at       timestamp default now(),
    constraint fk_movie_session_id foreign key (movie_session_id) references movie_sessions (id),
    constraint fk_user_id_1 foreign key (user_id) references users (id)
);