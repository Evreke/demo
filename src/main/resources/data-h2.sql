insert into movies (title, duration)
values ('From Dusk Till Dawn', '02:00'),
       ('Pulp Fiction', '01:30'),
       ('Афоня', '1:30');

insert into roles (title)
values ('Клиент'),
       ('Администратор');

insert into categories (title, discount)
values ('Обычная', 0),
       ('Социальная-1', 10),
       ('Социальная-2', 20),
       ('Социальная-3', 30);

insert into halls(name, capacity)
values ('Зелёный', 200),
       ('VIP', 10);

insert into movie_sessions (started_at, ended_at, date, movie_id, hall_id)
values ('10:00', '12:00', '2020-02-22', 1, 1),
       ('13:00', '14:30', '2020-02-22', 2, 1),
       ('15:00', '17:30', '2020-02-22', 3, 2);

insert into users (username, role_id, category_id)
values ('Вася', 1, 2),
       ('Петя', 1, 1);

insert into users_movie_sessions
values (1, 1),
       (1, 2),
       (2, 3);

insert into bookings (movie_session_id, user_id, payed)
values (2, 1, false),
       (1, 1, true),
       (3, 2, true);