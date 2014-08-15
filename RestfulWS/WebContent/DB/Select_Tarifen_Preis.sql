select 
t.id as tId, name, DayNight,DreiPeriod,Jahrzeit,
p.id as pId, Preis,HighDemand, 
Anfang, End,Wochentag,Sanstag,Sonntag
from Preis as p, Preis_activen as pa, Tarif as t, Tarif_has_Preis as tp 
where 
tp.Preis_id=p.id and pa.Preis_id=p.id and t.id = tp.Tarif_id and pa.Tarif_id and HighDemand = 0
order by t.id ASC, p.id ASC,Anfang ASC, Wochentag DESC, Sanstag ASC, Sonntag ASC
