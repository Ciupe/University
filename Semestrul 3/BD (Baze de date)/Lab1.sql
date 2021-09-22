--Create database SportsClubs
--go
use SportsClubs
go

--select @@SERVERNAME

-- deleting tables
DROP TABLE MVP 
DROP TABLE Players_Injuries
DROP TABLE Injuries
DROP TABLE Transfers 
DROP TABLE Players
DROP TABLE Tournaments
DROP TABLE Teams
DROP TABLE Arenas
DROP TABLE College 
DROP TABLE Clubs
DROP TABLE Managers

--creating tables
CREATE TABLE Clubs
(Cid INT PRIMARY KEY IDENTITY,
Name varchar(50),
Players int)

CREATE TABLE Players
(Pid INT PRIMARY KEY IDENTITY,
Name varchar(50))

CREATE TABLE College
(Cid INT PRIMARY KEY IDENTITY,
City varchar(50))

CREATE TABLE Teams
(Tid INT PRIMARY KEY IDENTITY,
CLid INT FOREIGN KEY REFERENCES Clubs(Cid),
COid INT FOREIGN KEY REFERENCES College(Cid),
Name varchar(50))

CREATE TABLE Arenas
(Aid INT PRIMARY KEY IDENTITY,
Capacity int NOT NULL)

CREATE TABLE Tournaments
(Tid INT PRIMARY KEY IDENTITY,
Aid INT FOREIGN KEY REFERENCES Arenas(Aid),
Winners INT FOREIGN KEY REFERENCES Teams(Tid),
Prize int NOT NULL)

CREATE TABLE Transfers
(Pid INT FOREIGN KEY REFERENCES Players(Pid),
Tid INT FOREIGN KEY REFERENCES Teams(Tid),
Cost int NOT NULL,
CONSTRAINT pk_Transfers primary key (Pid,Tid))

CREATE TABLE MVP
(Mid INT PRIMARY KEY IDENTITY,
Prize int)
-- made 1-1 relationship between MVP and Players
ALTER TABLE MVP ADD Pid INT UNIQUE REFERENCES Players(Pid)

CREATE TABLE Injuries
(Iid INT PRIMARY KEY IDENTITY,
Pid INT FOREIGN KEY REFERENCES Players(Pid),
DaysBenched int NOT NULL)

CREATE TABLE Managers
(Mid INT PRIMARY KEY IDENTITY,
Name varchar(50))
-- made 1-1 relationship between Teams and Managers 
ALTER TABLE Teams ADD Mid INT UNIQUE REFERENCES Managers(Mid)

CREATE TABLE Players_Injuries
(Pid INT FOREIGN KEY REFERENCES Players(Pid),
Iid INT FOREIGN KEY REFERENCES Injuries(Iid),
CONSTRAINT pk_Players_Injuries primary key (Pid, Iid))

