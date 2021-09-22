create database Lab5
use Lab5 
go

create table Ta(
aid int primary key,
a2 int unique
)

create table Tb(
bid int primary key, 
b2 int
)

create table Tc(
cid int primary key,
bid int foreign key references Tb(bid),
aid int foreign key references Ta(aid)
)

-- inserting some values
insert into Ta (aid, a2) values (1,2)
insert into Ta (aid, a2) values (2,7)
insert into Ta (aid, a2) values (3,5)
insert into Ta (aid, a2) values (4,6)
insert into Ta (aid, a2) values (5,9)

insert into Tb (bid, b2) values (1,2)
insert into Tb (bid, b2) values (2,7)
insert into Tb (bid, b2) values (3,1)

insert into Tc (cid, bid, aid) values (1, 2, 3)
insert into Tc (cid, bid, aid) values (2, 3, 1)
insert into Tc (cid, bid, aid) values (3, 1, 4)

select * from Ta 
select * from Tb 
select * from Tc

drop table Tc 
drop table Tb 
drop table Ta 

-- a) Write queries on Ta such that their execution plans contain the following operators:

-- clustered index scan
select * from Ta

-- clustered index seek 
select aid from Ta where aid > 2

-- nonclustered index scan 
select a2 from Ta

-- nonclustered index seek
select a2 from Ta where a2 > 5

-- key lookup 
select * from Ta 

-- b) Write a query on table Tb with a WHERE clause of the form WHERE b2 = value and analyze its execution plan. 
--	  Create a nonclustered index that can speed up the query. Examine the execution plan again.

select b2 from Tb where b2 = 1   -- nonclustered index not created: 0.0038253

if exists (select * from sys.indexes where name = 'index_b2')
	drop index index_b2 on Tb 
create nonclustered index index_b2 on Tb(b2)

select b2 from Tb where b2 = 1	 -- nonclustered index created: 0.0032831

-- c. Create a view that joins at least 2 tables. 
--	  Check whether existing indexes are helpful; if not, reassess existing indexes / examine the cardinality of the tables.
go

create or alter view View_Ta_Tc as
	select Tc.cid, Tc.aid 
	from Tc inner join Ta on Tc.aid = Ta.aid 
go

drop view View_Ta_tc

select * from View_Ta_tc -- clustered index scan: 0.0032853, clustered index seek: 0.0035993

if exists (select * from sys.indexes where name = 'index_aid')
	drop index index_aid on Tc 
create nonclustered index index_aid on Tc(aid)

select * from View_Ta_Tc	-- nonclustered index scan: 0.0032853, nonclustered index seek: 0.0035993