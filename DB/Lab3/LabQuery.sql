use labor_sql

/** 1 **/
SELECT model, speed, hd, price FROM dbo.laptop WHERE hd < 12 ORDER BY price DESC;
/** 2 **/
SELECT * FROM dbo.outcome WHERE MONTH(date) = 3;
/** 3 **/
SELECT product.maker, laptop.model FROM product
INNER JOIN laptop ON laptop.model = product.model
WHERE laptop.price > 600;
/** 4 **/
SELECT DISTINCT [maker] FROM product p
WHERE p.[maker] =  ANY (SELECT DISTINCT [maker] FROM product WHERE [type] = 'pc')
AND p.[maker] = ANY (SELECT DISTINCT [maker] FROM product WHERE [type] = 'laptop');
/** 5 **/
/** 6 **/
/** 7 **/
/** 8 **/
/** 9 **/
/** 10 **/


