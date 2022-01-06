-- 1. Создать таблицы и заполнить их начальными данными
create table departments (
  id serial primary key,
  name varchar(255)
);

create table emploers (
  id serial primary key,
  name varchar(255),
  department_id int references departments(id)
);

insert into departments(id, name) values (1, 'IT');
insert into departments(id, name) values (2, 'Logistic');
insert into departments(id, name) values (3, 'Accounting');

insert into emploers(id, name, department_id) values (1, 'Peter', 1);
insert into emploers(id, name, department_id) values (2, 'Pavel', 2);
insert into emploers(id, name, department_id) values (3, 'Aleksandr', 1);

-- 2. Выполнить запросы с left, rigth, full, cross соединениями
SELECT * FROM departments d left join emploers e on e.department_id = d.id;
SELECT * FROM departments d right join emploers e on e.department_id = d.id;
SELECT * FROM departments d full join emploers e on e.department_id = d.id;
SELECT * FROM departments cross join emploers;
-- 3. Используя left join найти департаменты, у которых нет работников
SELECT d.* FROM departments d left join emploers e on e.department_id = d.id
where e is null;
-- 4. Используя left и right join написать запросы, которые давали бы одинаковый результат (порядок вывода колонок в эти запросах также должен быть идентичный). 
SELECT d.*, e.* FROM departments d left join emploers e on e.department_id = d.id;
SELECT d.*, e.* FROM emploers e right join departments d on e.department_id = d.id; 
-- 5. Создать таблицу teens с атрибутами name, gender и заполнить ее. Используя cross join составить все возможные разнополые пары
create table teens (
  id serial primary key,
  name varchar(255),
  gender varchar(1)
);

insert into teens(id, name, gender) values (1, 'Peter', 'M');
insert into teens(id, name, gender) values (2, 'Ann', 'F');
insert into teens(id, name, gender) values (3, 'Anton', 'M');

SELECT * FROM teens t1 cross join teens t2;




