SELECT @@SERVERNAME

USE Northwind
GO

DROP TABLE Pirates
DROP TABLE Ships

Create table Ships
(ShipID int primary key,
SName varchar(100))

Create table Pirates
(PirateID int primary key,
PNam varchar(100),
ShipID int references Ships(ShipID))

insert Ships values (1,'s1'), (2,'s2')
insert Pirates values (1,'p1',1), (2,'p2',1)

select * from Ships
select * from Pirates