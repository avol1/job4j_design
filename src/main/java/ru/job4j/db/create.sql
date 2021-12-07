create table role (
  id serial primary key,
  name varchar(255)	
);

create table rule (
  id serial primary key,
  name varchar(255)
);

create table rules_for_roles (
  id serial primary key,
  role_id int references role(id),
  rule_id int references rule(id)
);

create table users (
  id serial primary key,
  username varchar(255),
  role_id int references role(id)
);

create table category (
  id serial primary key,
  name varchar(255)
);

create table state (
  id serial primary key,
  name varchar(255)
);

create table item (
  id serial primary key,
  name varchar(255),
  user_id int references users(id),
  category_id int references category(id),
  state_id int references state(id)
);

create table comment (
  id serial primary key,
  item_id int references item(id),
  text text
);

create table attach (
  id serial primary key,
  item_id int references item(id),
  file BYTEA
);






