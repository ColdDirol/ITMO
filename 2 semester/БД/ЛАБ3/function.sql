CREATE FUNCTION selectCountFromMainlend (SexParam VARCHAR, Mainland VARCHAR)
RETURNS int AS
'SELECT COUNT(peticantrop.sex)
FROM peticantrop
LEFT JOIN creature ON peticantrop.id = creature.peticantrop
LEFT JOIN creature_homes ON creature.id = creature_homes.creature
LEFT JOIN home ON creature_homes.home = home.id
LEFT JOIN location ON home.location = location.id
WHERE Mainland = location.mainland and SexParam = peticantrop.sex;'
LANGUAGE SQL;