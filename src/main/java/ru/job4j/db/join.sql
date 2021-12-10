create table order_header (
	id serial primary key,
	name varchar(255),
	orderDate date
);

create table order_item (
   id serial primary key,
   order_id int references order_header(id),	
   name varchar(255),
   count int,
   sum decimal
);


insert into order_header(id, name, orderDate) values
(1, 'ООО ВкусныйБургер', '2021-12-01'),
(2, 'ИП Иванов', '2021-12-02');

insert into order_item(id, order_id, name, count, sum) values
(1, 1, 'Ватрушка', 3, 100),
(2, 1, 'Петрушка', 1, 20),
(3, 2, 'Морковь', 5, 200);


SELECT head.name as Имя
  FROM order_header as head
  INNER JOIN order_item as item ON head.id = item.order_id
  WHERE item.sum > 100;
  
SELECT item.name as Продукт
  FROM order_header as head
  INNER JOIN order_item as item ON head.id = item.order_id
  WHERE head.name LIKE 'ООО%';
  
SELECT head.id as НомерЗаказа, head.name as Имя, item.name as Продукт
  FROM order_header as head
  INNER JOIN order_item as item ON head.id = item.order_id;