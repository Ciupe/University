use SportsClubs
go

-- Delete data and reset identity:
delete from MVP where Mid >= 0
dbcc checkident('MVP', reseed, 0)

delete from Players_Injuries where Pid >= 0

delete from Injuries where Iid >= 0
dbcc checkident('Injuries', reseed, 0)

delete from Transfers where Pid >= 0
-- doesn't have an identity

delete from Players where Pid >= 0
dbcc checkident('Players', reseed, 0)

delete from Tournaments where Tid >= 0
dbcc checkident('Tournaments', reseed, 0)

delete from Teams where Tid >= 0
dbcc checkident('Teams', reseed, 0)

delete from Arenas where Aid >= 0
dbcc checkident('Arenas', reseed, 0)

delete from College where Cid >= 0
dbcc checkident('College', reseed, 0)

delete from Clubs where Cid >= 0
dbcc checkident('Clubs', reseed, 0)

delete from Managers where Mid >= 0
dbcc checkident('Managers', reseed, 0)


--inserting into Players
INSERT INTO Players(Name) VALUES ('Ciupe')
INSERT INTO Players(Name) VALUES ('Sergiu')
INSERT INTO Players(Name) VALUES ('Calin')
INSERT INTO Players(Name) VALUES ('Silvia')
INSERT INTO Players(Name) VALUES ('Puiu')


--inserting into Clubs
INSERT INTO Clubs(Name, Players) VALUES ('Football', 16)
INSERT INTO Clubs(Name, Players) VALUES ('Basketball', 11)

--inserting into College
INSERT INTO College(City) VALUES ('Cluj-Napoca')
INSERT INTO College(City) VALUES ('Bucuresti')
INSERT INTO College(City) VALUES ('Suceava')
INSERT INTO College(City) VALUES ('Ploiesti')

--inserting into Managers
INSERT INTO Managers(Name) VALUES ('Alina')
INSERT INTO Managers(Name) VALUES ('Mihai')
INSERT INTO Managers(Name) VALUES ('Andreea')
INSERT INTO Managers(Name) VALUES ('Simion')
INSERT INTO Managers(Name) VALUES ('Lucian')
INSERT INTO Managers(Name) VALUES ('Manager1')
INSERT INTO Managers(Name) VALUES ('Manager2')
INSERT INTO Managers(Name) VALUES ('Manager3')


--inserting into Teams
INSERT INTO Teams(CLid, COid, Name, Mid) VALUES (1, 2, 'Warriors', 1)
INSERT INTO Teams(CLid, COid, Name, Mid) VALUES (2, 1, 'Hawks', 3)
INSERT INTO Teams(CLid, COid, Name, Mid) VALUES (2, 3, 'Prodigies', 2)
INSERT INTO Teams(CLid, COid, Name, Mid) VALUES (1, 1, 'Bulls', 4)
INSERT INTO Teams(CLid, COid, Name, Mid) VALUES (2, 3, 'Bulldogs', 5)

--inserting into Arenas
INSERT INTO Arenas(Capacity) VALUES (245)
INSERT INTO Arenas(Capacity) VALUES (179)
INSERT INTO Arenas(Capacity) VALUES (355)

--inserting into Tournaments
INSERT INTO Tournaments(Aid, Winners, Prize) VALUES (1, 3, 2500)
INSERT INTO Tournaments(Aid, Winners, Prize) VALUES (2, 1, 1500)
INSERT INTO Tournaments(Aid, Winners, Prize) VALUES (1, 4, 1000)
INSERT INTO Tournaments(Aid, Winners, Prize) VALUES (2, 1, 1750)

--inserting into Transfers
INSERT INTO Transfers(Tid, Pid, Cost) VALUES (1, 2, 1700)
INSERT INTO Transfers(Tid, Pid, Cost) VALUES (2, 4, 500)
INSERT INTO Transfers(Tid, Pid, Cost) VALUES (4, 3, 390)
INSERT INTO Transfers(Tid, Pid, Cost) Values (1, 3, 300)

--inserting into MVP
INSERT INTO MVP(Pid, Prize) VALUES (1, 1750)
INSERT INTO MVP(Pid, Prize) VALUES (3, NULL)

--inserting into Injuries
INSERT INTO Injuries(Pid, DaysBenched) VALUES (3, 14)
INSERT INTO Injuries(Pid, DaysBenched) VALUES (4, 8)
INSERT INTO Injuries(Pid, DaysBenched) VALUES (3, 11)

-- Update data:
SELECT * FROM Arenas
UPDATE Arenas 
SET Capacity = 250 WHERE Capacity < 200
SELECT * FROM Arenas 

SELECT * FROM Tournaments
UPDATE Tournaments
SET Prize = Prize + 150
SELECT * FROM Tournaments

SELECT * FROM Transfers
UPDATE Transfers
SET Cost = Cost / 2 WHERE Pid between 1 and 3
SELECT * FROM Transfers

SELECT * FROM MVP 
UPDATE MVP 
SET Prize = 5000 WHERE Prize is null
SELECT * FROM MVP

-- Select all tables:
SELECT * FROM Arenas
SELECT * FROM Clubs
SELECT * FROM College
SELECT * FROM Injuries 
SELECT * FROM Managers 
SELECT * FROM MVP 
SELECT * FROM Players 
SELECT * FROM Teams 
SELECT * FROM Tournaments
SELECT * FROM Transfers 

-- a. 2 queries with the union operation; use UNION [ALL] and OR;
-- Displays all the managers and players sorted alphabetically
SELECT Name FROM Players
UNION 
SELECT Name FROM Managers
ORDER BY Name ASC

-- Displays all distinct players whose names start with "S" or end in "u" ordered descendingly
SELECT DISTINCT Name from Players WHERE ( Name like 'S%' or Name like '%u' ) ORDER BY Name DESC

-- b. 2 queries with the intersection operation; use INTERSECT and IN;
-- Displays all the MVP's that were injured
SELECT Pid FROM MVP 
INTERSECT
SELECT Pid FROM Injuries

-- Selects all the teams from certain colleges
SELECT Name, COid FROM Teams 
Where COid IN (2, 3, 4)

-- c. 2 queries with the difference operation; use EXCEPT and NOT IN;
-- Select all the players that weren't injured
SELECT Pid, Name FROM Players
WHERE Pid in ( SELECT Pid FROM Players EXCEPT SELECT Pid FROM Injuries)

--Select all managers that aren't part of the football club
SELECT Name from Managers
WHERE Mid not in ( SELECT Mid from Teams WHERE CLid != 1 )

-- d. 4 queries with INNER JOIN, LEFT JOIN, RIGHT JOIN, and FULL JOIN (one query per operator);
-- one query will join at least 3 tables, while another one will join at least two many-to-many relationships;

-- INNER JOIN: Select the players that have been MVP, suffered an injury and transfered to another team
	SELECT Name FROM Players WHERE Players.Pid in ( SELECT MVP.Pid FROM ( MVP INNER JOIN Injuries ON MVP.Pid = Injuries.Pid ) INNER JOIN Transfers ON MVP.Pid = Transfers.Pid )

-- LEFT JOIN: Select the teams that have won a tournament
	SELECT Teams.Name, Tournaments.Prize
	FROM Teams 
	LEFT JOIN Tournaments ON Teams.Tid = Tournaments.Winners

-- RIGHT JOIN: Select the teams that are registered in a club
	SELECT Teams.Name
	FROM Teams
	RIGHT JOIN Clubs on Clubs.Cid = Teams.CLid

-- FULL JOIN: Full joining at least 2 many-to-many relationships
	SELECT Players.Name, Teams.Name
	FROM Players
	FULL JOIN Transfers on Players.Pid = Transfers.Pid
	FULL JOIN Teams on Transfers.Tid = Teams.Tid
	FULL JOIN Tournaments on Teams.Tid = Tournaments.Winners
	FULL JOIN Arenas on Tournaments.Aid = Arenas.Aid

-- e. 2 queries with the IN operator and a subquery in the WHERE clause; in at least one case, the subquery should include a subquery in its own WHERE clause;
	--Display the name of the clubs which have at least one team that has won a tournament ordered ascendingly
SELECT C.Name FROM Clubs C
WHERE C.Cid in (SELECT T.Tid from Teams T
WHERE T.Tid in (SELECT Tou.Tid from Tournaments Tou))
ORDER BY C.Name asc

	-- Display the name of the players that haven't suffered an injury
SELECT TOP 2 Players.Name FROM Players 
WHERE Players.Pid not in ( SELECT Injuries.Pid from Injuries)

-- f. 2 queries with the EXISTS operator and a subquery in the WHERE clause;
	-- Displays the name of the teams that have won a tournament with a prize higher than 2000
SELECT Teams.Name FROM Teams
WHERE exists (SELECT * FROM Tournaments
WHERE Tournaments.Winners = Teams.Tid and (Tournaments.Prize > 2000))

	-- Displays the name of the players that were injured
SELECT Players.Name FROM Players
WHERE exists (SELECT * FROM MVP  
WHERE Players.Pid = MVP.Pid)

-- g. 2 queries with a subquery in the FROM clause; 
	-- Displays the ID of the unique players that were injured for more than 10 days
SELECT DISTINCT Injuries.DaysBenched FROM Injuries
WHERE ( SELECT avg(Injuries.DaysBenched) FROM Injuries ) > 10

	-- Displays the details of the first 3 players whose name start with S or C
SELECT TOP 3 * FROM Players 
WHERE Players.Name LIKE 'S%'or Players.Name LIKE 'C%'

-- h. 4 queries with the GROUP BY clause, 3 of which also contain the HAVING clause; 
-- 2 of the latter will also have a subquery in the HAVING clause; use the aggregation operators: COUNT, SUM, AVG, MIN, MAX;

	-- Displays the maximum injury duration of each player and their name
SELECT max(Injuries.DaysBenched), Players.Name FROM Injuries
INNER JOIN Players on Injuries.Pid = Players.Pid
GROUP BY Players.Name

	-- Displays the number of the teams that belong to each club having more than 2 teams and the club's ID
SELECT count(Tid), CLid FROM Teams
GROUP BY CLid
HAVING count(CLid) >= 2

	-- Displays the prize and the ID of the teams that have won a tournament with a prize greater than average
SELECT Tournaments.Winners, Tournaments.Prize FROM Tournaments
GROUP BY Tournaments.Winners, Tournaments.Prize
HAVING avg(Tournaments.Prize) > ( SELECT avg(Tournaments.Prize) FROM Tournaments)

	-- Displays the player's ID and the cost of transfer of the player that's been transfered with a Cost/MinCost ratio higher than the SumCost/MaxCost Ratio
SELECT DISTINCT Transfers.Pid, Transfers.Cost FROM Transfers
GROUP BY Transfers.Pid, Transfers.Cost
HAVING Transfers.Cost / ( SELECT min(Cost) FROM Transfers ) > ( SELECT sum(Cost) FROM Transfers ) / ( SELECT max(Cost) FROM Transfers )

-- i. 4 queries using ANY and ALL to introduce a subquery in the WHERE clause (2 queries per operator); 
-- rewrite 2 of them with aggregation operators, and the other 2 with IN / [NOT] IN.

	-- Displays the Tournament ID and Prize of the tournaments that have a higher prize than the cost of all transfers
SELECT Tournaments.Tid, Tournaments.Prize FROM Tournaments
WHERE Tournaments.Prize > all ( SELECT Transfers.Cost FROM Transfers )
-- rewrite using 'max'
SELECT Tournaments.Tid, Tournaments.Prize FROM Tournaments
WHERE Tournaments.Prize > ( SELECT max(Transfers.Cost) FROM Transfers )

	-- Displays the Transfer ID and the Cost of the transfers that have a higher cost than any tournament's prize
SELECT Transfers.Pid, Transfers.Cost FROM Transfers
WHERE Transfers.Cost > any ( SELECT Tournaments.Prize FROM Tournaments )
-- rewrite using 'min'
SELECT Transfers.Pid, Transfers.Cost FROM Transfers
WHERE Transfers.Cost > ( SELECT min(Tournaments.Prize) FROM Tournaments )


	-- Displays the name and college of the teams that have won a tournament
SELECT Teams.Name, Teams.COid FROM Teams 
WHERE Teams.Tid = any ( SELECT Tournaments.Winners FROM Tournaments )
-- rewrite using 'in'
SELECT Teams.Name, Teams.COid FROM Teams 
WHERE Teams.Tid in ( SELECT Tournaments.Winners FROM Tournaments )

SELECT Players.Name FROM Players
WHERE Players.Pid != all ( SELECT Injuries.Pid FROM Injuries )
-- rewrite using 'not in'
SELECT Players.Name FROM Players
WHERE Players.Pid not in ( SELECT Injuries.Pid FROM Injuries )



