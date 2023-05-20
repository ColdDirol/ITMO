SELECT array_agg(p.name) AS имена
FROM peticantrop p
INNER JOIN creature c
ON p.id = c.peticantrop
INNER JOIN creature_homes c_h
ON c_h.creature = c.id
INNER JOIN home h
ON h.id = c_h.home
INNER JOIN location l
ON l.id = h.location
INNER JOIN size s
ON s.id = l.size
INNER JOIN biome b
ON b.id = l.biome
WHERE s.name = 'Маленькая' OR s.name = 'Очень-маленькая'
OR b.water = false AND b.mountains = true;