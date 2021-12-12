SELECT AVG(price) FROM devices;

SELECT p.name, AVG(d.price)
FROM people as p
INNER JOIN devices_people as dp ON p.id = dp.people_id
INNER JOIN devices as d ON dp.device_id = d.id
GROUP BY p.name;

SELECT p.name, AVG(d.price)
FROM people as p
INNER JOIN devices_people as dp ON p.id = dp.people_id
INNER JOIN devices as d ON dp.device_id = d.id
GROUP BY p.name
HAVING AVG(d.price) > 5000;