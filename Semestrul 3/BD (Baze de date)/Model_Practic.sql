use Model_DB_Trains
go

-- a

create table Train_Types(
TTid int primary key identity,
Name varchar(50),
Description varchar(50)
)

create table Trains(
Tid int primary key identity,
Name varchar(50),
TypeID int foreign key references Train_Types(TTid) 
)

create table Stations(
Sid int primary key identity,
Name varchar(50) unique
)

create table Routes(
Rid int primary key identity,
Name varchar(50) unique,
TrainID int foreign key references Trains(Tid)
)

create table Routes_Stations(
StationID int foreign key references Stations(Sid),
RouteID int foreign key references Routes(Rid),
Departure time,
Arrival time,
constraint pK_Routes_Stations primary key (StationID, RouteID)
)

drop table Routes_Stations

insert into Train_Types values ('a', 'xd')
insert into Train_Types values ('x', 'uwu')
insert into Train_Types values ('d', 'owo')

insert into Trains values ('t1', 3)
insert into Trains values ('t2', 2)
insert into Trains values ('t3', 2)
insert into Trains values ('t4', 1)
insert into Trains values ('t5', 3)

insert into Routes values ('r1', 4)
insert into Routes values ('r2', 2)
insert into Routes values ('r3', 5)
 
insert into Stations values ('a')
insert into Stations values ('b')
insert into Stations values ('c')
insert into Stations values ('d')

insert into Routes_Stations values (1, 2, '5:40', '5:49')
insert into Routes_Stations values (4, 3, '2:29', '3:15')
insert into Routes_Stations values (1, 3, '2:17', '2:22')
insert into Routes_Stations values (2, 3, '2:15', '4:25')
insert into Routes_Stations values (3, 3, '2:16', '6:19')
insert into Routes_Stations values (3, 1, '4:10', '10:15')
insert into Routes_Stations values (3, 2, '4:10', '10:15')


go 

select * from Routes_Stations

-- b

create or alter procedure Add_Route @station int, @route int, @departure time, @arrival time
as
	declare @n int = 0
	select @n = count(*) from Routes_Stations RS where RS.RouteID = @route and RS.StationID = @station
	if ( @n != 0 ) 
		begin
			update Routes_Stations 
				set Departure = @departure, Arrival = @arrival
			where 
				StationID = @station and RouteID = @route
		end
	else 
		begin
			insert into Routes_Stations values (@station, @route, @departure, @arrival)
		end
go

exec Add_Route 1, 2, '5:50', '6:15'
exec Add_Route 2, 3, '3:44', '7:19'
exec Add_Route 1, 2, '6:40', '9:12'

select * from Routes_Stations

-- c

go

create or alter view View_Names
as
	/*declare @max_stations int
	select @max_stations = count (*) from Stations
	
	declare @route_index int = 1
	while */

	select R.Name, R.Rid, count (*) as NoOfStations 
	from Routes_Stations RS inner join Routes R on RS.RouteID = R.Rid
	group by R.Rid, R.Name
	having count (*) =  (select count (*) from Stations)
go

select * from View_Names


-- d

go

create or alter function Route_Names (@r int) returns table
as return
	SELECT DISTINCT S.Sid, S.Name, count(S.Name) as NumberOfRoutes
FROM Stations S INNER JOIN Routes_Stations RS ON S.Sid = RS.StationID
group by S.Sid, S.Name
having count(S.Name)>@r
go

SELECT * FROM Route_Names(1)

select * from Routes_Stations