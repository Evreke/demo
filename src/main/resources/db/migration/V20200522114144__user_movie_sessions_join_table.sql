alter table movie_sessions
    add column date date;

create table users_movie_sessions
(
    user_id          bigint,
    movie_session_id bigint,
    constraint user_movie_session_pk primary key (user_id, movie_session_id),
    constraint fk_user_id foreign key (user_id) references users (id),
    constraint fk_movie_sessions_id foreign key (movie_session_id) references movie_sessions (id)
);