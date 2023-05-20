SELECT Н_УЧЕНИКИ.ГРУППА AS Группа, 
FLOOR(AVG(DATE_PART('year', AGE(Н_ЛЮДИ.ДАТА_РОЖДЕНИЯ)))) AS "Средний возраст"

FROM Н_ЛЮДИ
JOIN Н_УЧЕНИКИ ON Н_УЧЕНИКИ.ЧЛВК_ИД = Н_ЛЮДИ.ИД
GROUP BY Н_УЧЕНИКИ.ГРУППА

HAVING FLOOR(AVG(DATE_PART('year', AGE(Н_ЛЮДИ.ДАТА_РОЖДЕНИЯ)))) = (
SELECT FLOOR(AVG(DATE_PART('year', AGE(Н_ЛЮДИ.ДАТА_РОЖДЕНИЯ))))
FROM Н_ЛЮДИ
JOIN Н_УЧЕНИКИ ON Н_УЧЕНИКИ.ЧЛВК_ИД = Н_ЛЮДИ.ИД
WHERE Н_УЧЕНИКИ.ГРУППА = '1101');