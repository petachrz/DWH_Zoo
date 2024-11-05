--GROUP BY ROLLUP, analýza doplněné potravy--
SELECT z.Druh,
       z.Rad,
       o.Uvazek,
       AVG(v.DoplnenaPotrava) AS PrumernaDoplnenaPotrava
FROM Zvire z
JOIN Vysetreni v ON z.idZvire = v.Zvire_idZvire
JOIN Osetrovatel o ON v.Osetrovatel_idOsetrovatel = o.idOsetrovatel
GROUP BY ROLLUP(z.Druh, z.Rad, o.Uvazek)
HAVING COUNT(v.idVysetreni) > 2
ORDER BY z.Druh, z.Rad;