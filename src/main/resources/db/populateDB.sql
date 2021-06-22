DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, TIMESTAMP '2020-01-30 10:00:00', 'Завтрак чемпиона', 400),
       (100000, TIMESTAMP '2020-01-30 13:00:00', 'Котлетки', 1000),
       (100000, TIMESTAMP '2020-01-30 20:00:00', 'Салатик', 500),
       (100000, TIMESTAMP '2020-01-31 00:00:00', 'Студень Загадочный', 200),
       (100000, TIMESTAMP '2020-01-31 19:30:00', 'Много всего', 1000),
       (100000, TIMESTAMP '2020-01-31 13:25:00', 'Обед, да', 500),
       (100000, TIMESTAMP '2020-01-31 20:00:00', 'Ужин из пива', 410),
       (100000, TIMESTAMP '2020-02-01 20:00:00', 'Ужин юзера 1 февраля', 510),
       (100000, TIMESTAMP '2020-02-01 10:25:00', 'Завтрак юзера 1 февраля', 650),
       (100001, TIMESTAMP '2020-01-30 10:00:00', 'Завтрак админа', 500),
       (100001, TIMESTAMP '2020-01-30 13:00:00', 'Обед админа', 1000),
       (100001, TIMESTAMP '2020-01-30 20:00:00', 'Ужин админа', 500),
       (100001, TIMESTAMP '2020-01-31 00:00:00', 'Еда на граничное значение админа', 100),
       (100001, TIMESTAMP '2020-01-31 10:00:00', 'Завтрак админа 2', 1000),
       (100001, TIMESTAMP '2020-01-31 20:00:00', 'Ужин админа 2', 410);