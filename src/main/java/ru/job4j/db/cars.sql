create table body (
  id serial primary key,
  name varchar(255)
);

create table engine (
  id serial primary key,
  name varchar(255)
);

create table transmission (
  id serial primary key,
  name varchar(255)
);

create table car (
  id serial primary key,
  name varchar(255),
  body_id int references body(id),
  engine_id int references engine(id),
  transmission_id int references transmission(id)
);

insert into body (id, name) values (1, 'BODY1');
insert into body (id, name) values (2, 'BODY2');
insert into body (id, name) values (3, 'BODY3');

insert into engine (id, name) values (1, 'ENGINE1');
insert into engine (id, name) values (2, 'ENGINE2');
insert into engine (id, name) values (3, 'ENGINE3');

insert into transmission (id, name) values (1, 'TRANSMISSION1');
insert into transmission (id, name) values (2, 'TRANSMISSION2');
insert into transmission (id, name) values (3, 'TRANSMISSION3');

insert into car (id, name, body_id, engine_id, transmission_id)
values (1, 'CAR1', 1, 1, 1);

insert into car (id, name, body_id, engine_id, transmission_id)
values (2, 'CAR2', 2, 2, 2);

-- Вывести список всех машин и все привязанные к ним детали.
SELECT c.name машина, b.name кузов, e.name двигатель, t.name коробка_передач
FROM car c
INNER JOIN body b on c.body_id = b.id
INNER JOIN engine e on c.engine_id = e.id
INNER JOIN transmission t on c.transmission_id = t.id;

-- Вывести отдельно детали (1 деталь - 1 запрос), которые не используются НИ в одной машине, кузова, двигатели, коробки передач.
SELECT b.name кузов
FROM body b
LEFT JOIN car c on b.id = c.body_id
WHERE c IS NULL;

SELECT e.name двигатель
FROM engine e
LEFT JOIN car c on e.id = c.engine_id
WHERE c IS NULL;

SELECT t.name коробка_передач
FROM transmission t
LEFT JOIN car c on t.id = c.transmission_id
WHERE c IS NULL;