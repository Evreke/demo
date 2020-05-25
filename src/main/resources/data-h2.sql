insert into movies (title, duration)
values ('From Dusk Till Dawn', '02:00'),
       ('Pulp Fiction', '01:30'),
       ('Афоня', '1:30');

insert into roles (title)
values ('Клиент'),
       ('Администратор');

insert into categories (title, discount)
values ('Обычная', 0),
       ('Социальная-1', 0.1),
       ('Социальная-2', 0.2),
       ('Социальная-3', 0.3);

insert into halls(name, capacity)
values ('Зелёный', 200),
       ('VIP', 10);

insert into movie_sessions (started_at, ended_at, date, movie_id, hall_id, price)
values ('10:00', '12:00', '2020-02-22', 1, 1, 400),
       ('13:00', '14:30', '2020-02-22', 2, 1, 500),
       ('15:00', '17:30', '2020-02-22', 3, 2, 450);

insert into users (username, role_id, category_id)
values ('Вася', 1, 2),
       ('Петя', 1, 1);