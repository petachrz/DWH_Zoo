--Získání informací o ošetřovatelích s nejvíce provedenými vyšetření--
SELECT Osetrovatel.idOsetrovatel,
    Osetrovatel.Jmeno,
    Osetrovatel.Prijmeni,
    Osetrovatel.Telefon,
    Osetrovatel.Smena,
    Osetrovatel.Uvazek,
    COUNT(Vysetreni.idVysetreni) AS PocetVysetreni
FROM Osetrovatel
JOIN Vysetreni ON Osetrovatel.idOsetrovatel = Vysetreni.Osetrovatel_idOsetrovatel
JOIN Datum ON Vysetreni.Datum_idDatum = Datum.idDatum
WHERE Datum.Mesic = 4
GROUP BY Osetrovatel.*
ORDER BY PocetVysetreni DESC;