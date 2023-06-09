SELECT DISTINCT Н_УЧЕНИКИ.ГРУППА AS result

FROM Н_УЧЕНИКИ
JOIN Н_ПЛАНЫ ON Н_УЧЕНИКИ.ПЛАН_ИД = Н_ПЛАНЫ.ИД
JOIN Н_ОТДЕЛЫ ON Н_ПЛАНЫ.ОТД_ИД = Н_ОТДЕЛЫ.ИД
AND Н_ОТДЕЛЫ.КОРОТКОЕ_ИМЯ = 'КТиУ'

WHERE Н_ПЛАНЫ.УЧЕБНЫЙ_ГОД = '2010/2011'
OR Н_ПЛАНЫ.УЧЕБНЫЙ_ГОД = '2011/2012';