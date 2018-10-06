use labor_sql;
/**1**/
SELECT DISTINCT maker, type FROM product 
WHERE type = 'laptop' 
ORDER BY maker;
/**2**/
SELECT substring_index(passenger.name, ' ', -1) AS lastname 
FROM passenger
WHERE substring_index(passenger.name, ' ', -1) NOT LIKE 'J%'; 
/**3**/
SELECT DISTINCT maker FROM product 
JOIN laptop ON product.model = laptop.model
WHERE laptop.speed > 500
ORDER BY maker;
/**4**/
SELECT DISTINCT maker FROM product 
WHERE maker IN (SELECT maker FROM product WHERE type = 'pc')
AND maker IN(SELECT maker FROM product WHERE type = 'laptop');
/**5**/
SELECT product.maker, MAX(pc.speed) FROM (SELECT maker FROM product WHERE type = 'Printer') AS p, 
(product JOIN pc ON product.model = pc.model);    
/**6**/
SELECT CONCAT(DAY(date), '.', MONTH(date), '.', YEAR(date)) datee FROM income;
/**7**/
SELECT product.model, AVG(price) cost, COUNT(price) quantity FROM product
JOIN pc ON product.model = pc.model
GROUP BY model
HAVING cost < 800;
/**8**/
SELECT maker, COUNT(pc.model) pc_num,
COUNT(laptop.model) laptop_num,
COUNT(printer.model) printer_num 
FROM product
LEFT JOIN pc ON product.model = pc.model
LEFT JOIN laptop ON product.model = laptop.model
LEFT JOIN printer ON product.model = printer.model
GROUP BY maker;
/**9**/
SELECT * FROM ships, classes WHERE (
(CASE numGuns WHEN 9 THEN 1 ELSE 0 END)+
(CASE bore WHEN 16 THEN 1 ELSE 0 END)+
(CASE displacement WHEN 46000 THEN 1 ELSE 0 END)+
(CASE type WHEN 'bb' THEN 1 ELSE 0 END)+
(CASE country WHEN 'Japan' THEN 1 ELSE 0 END)+
(CASE launched WHEN 1916 THEN 1 ELSE 0 END)+
(CASE ships.class WHEN 'Revenge' THEN 1 ELSE 0 END)
) >= 3;
/**10**/
SELECT maker, product.model, type, price FROM product 
LEFT JOIN pc ON product.model = pc.model
WHERE maker = 'B' AND price IS NOT NULL
UNION
SELECT maker, product.model, type, price FROM product 
LEFT JOIN laptop ON product.model = laptop.model
WHERE maker = 'B' AND price IS NOT NULL;