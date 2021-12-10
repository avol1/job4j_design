SELECT * FROM fauna WHERE name LIKE '%fish%'

SELECT * FROM fauna WHERE avg_age BETWEEN 10000 AND 21000;

SELECT * FROM fauna WHERE discovery_date IS NULL;

SELECT * FROM fauna WHERE discovery_date < '1950-01-01';