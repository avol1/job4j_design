-- Написать запрос получение всех продуктов с типом "СЫР"
SELECT product.*
FROM product
INNER JOIN type ON product.type_id = type.id
WHERE type.name = 'СЫР';
-- Написать запрос получения всех продуктов, у кого в имени есть слово "мороженое"
SELECT *
FROM product
WHERE name LIKE '%мороженое%';
-- Написать запрос, который выводит все продукты, срок годности которых уже истек
SELECT *
FROM product
WHERE expired_date < CURRENT_DATE;
-- Написать запрос, который выводит самый дорогой продукт.
SELECT name
FROM product
WHERE price = (SELECT MAX(price) FROM product );
-- Написать запрос, который выводит для каждого типа количество продуктов к нему принадлежащих. В виде имя_типа, количество
SELECT t.name as имя_типа, COUNT(*) as количество
FROM type as t
INNER JOIN product as p on t.id = p.type_id
GROUP by t.name;
-- Написать запрос получение всех продуктов с типом "СЫР" и "МОЛОКО"
SELECT product.*
FROM product
INNER JOIN type ON product.type_id = type.id
WHERE type.name IN ('СЫР', 'МОЛОКО');
-- Написать запрос, который выводит тип продуктов, которых осталось меньше 10 штук.
SELECT t.name as имя_типа, COUNT(*) as количество
FROM type as t
INNER JOIN product as p on t.id = p.type_id
GROUP by t.name
HAVING COUNT(*) < 10;
-- Вывести все продукты и их тип.
SELECT p.name as Продукт, t.name as Тип
FROM product as p
INNER JOIN type AS t ON p.type_id = t.id;








