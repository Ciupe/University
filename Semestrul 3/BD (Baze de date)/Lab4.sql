use SportsClubs
go

-- select all players
create or alter view viewPlayers 
as
	select * from Players
go
--select * from viewPlayers
go

-- selects players that were injured
create or alter view viewPlayersInjuries
as
	select Players.Name, Injuries.DaysBenched from Players
	inner join Injuries on Players.Pid = Injuries.Pid
go
--select * from viewPlayersInjuries
go

-- groups players grouped by id, name and injury duration
create or alter view viewPlayersInjuriesGrouped
as
	select Players.Pid, Players.Name, Injuries.DaysBenched from Players 
	inner join Injuries on Players.Pid = Injuries.Pid
	group by Players.Pid, Players.Name, Injuries.DaysBenched
go
--select * from viewPlayersInjuriesGrouped
go

-- adding tests
create or alter procedure addEverything
as
	insert into Tables values('Players')				-- 1 PK, 0 FK
	insert into Tables values('Injuries')				-- 1 PK, 1 FK
	insert into Tables values('Players_Injuries')		-- two PKs
	
	insert into Views values('viewPlayers')
	insert into Views values('viewPlayersInjuries')
	insert into Views values('viewPlayersInjuriesGrouped')

	insert into Tests values('test 100 values')
	insert into Tests values('test 1000 values')
	insert into Tests values('test 10000 values')

	insert into TestTables values(1, 1, 100, 1)
	insert into TestTables values(1, 2, 100, 2)
	insert into TestTables values(1, 3, 100, 3)
	insert into TestTables values(2, 1, 1000, 1)
	insert into TestTables values(2, 2, 1000, 2)
	insert into TestTables values(2, 3, 1000, 3)
	insert into TestTables values(3, 1, 10000, 1)
	insert into TestTables values(3, 2, 10000, 2)
	insert into TestTables values(3, 3, 10000, 3)

	insert into TestViews values(1, 1)
	insert into TestViews values(1, 2)
	insert into TestViews values(1, 3)
	insert into TestViews values(2, 1)
	insert into TestViews values(2, 2)
	insert into TestViews values(2, 3)
	insert into TestViews values(3, 1)
	insert into TestViews values(3, 2)
	insert into TestViews values(3, 3)
go
select * from Tables
select * from Views
select * from Tests
select * from TestTables 
select * from TestViews 
go

-- inserting into players 
create or alter procedure insertPlayers(@rows int)
as
	print 'Inserting ' + convert(varchar(50), @rows) + ' rows into Players...'
	declare @n int
	set @n = 0
	declare @name varchar(50)

	while @n < @rows
	begin 
		set @name = 'Player ' + convert(varchar(5), @n)
		insert into Players values (@name)
		set @n = @n + 1
	end 
go

--select * from Players
--exec insertPlayers 100
--delete from Players
go

-- insert into Injuries
create or alter procedure insertInjuries(@rows int)
as
	print 'Inserting ' + convert(varchar(50), @rows) + ' rows into Injuries...'
	declare @n int
	set @n = 0
	declare @pid int
	declare @duration int 

	while @n < @rows
	begin 
		set @pid = @n + 1
		set @duration = @n % 14 + 4
		insert into Injuries values (@pid, @duration)
		set @n = @n + 1
	end 
go

--select * from Injuries
--exec insertInjuries 100
--delete from Injuries
go

-- insert into Players_Injuries
create or alter procedure insertPlayers_Injuries(@rows int)
as 
	print 'Inserting ' + convert(varchar(50), @rows) + ' rows into Players_Injuries...'
	declare @n int
	set @n = 0
	declare @pid int
	declare @iid int

	while @n < @rows 
	begin 
		set @pid = @n + 1 
		set @iid = @n + 1
		insert into Players_Injuries values (@pid, @iid)
		set @n = @n + 1
	end
go

--select * from Players_Injuries
--exec insertPlayers_Injuries 100
--delete from Players_Injuries
go

create or alter procedure insertValues(@tableID int, @rows int)
as
	print 'Inserting ' + convert(varchar(50), @rows) + ' rows into table with id ' + convert(varchar(50), @tableID)
	declare @tableName varchar(50)
	declare @procedureName varchar(50)
	declare @query varchar(100)

	set @tableName = (select Tables.Name from Tables where Tables.TableID = @tableID)
	set @procedureName = 'insert' + @tableName

	set @query = 'exec ' + @procedureName + ' @rows = ' + convert(varchar(50), @rows)
	exec (@query)
go

create or alter procedure deleteValues(@tableID int)
as
 	print 'Deleting the values from table with id ' + convert(varchar(5), @tableID)

	declare @tableName varchar(50)
	declare @query varchar(100)

	set @tableName = (select Tables.Name from Tables where Tables.TableID = @tableID)

	set @query = 'delete from ' + @tableName
	exec (@query)

	set @query = 'delete from '
	if @tableName <> 'Players_Injuries'
	begin
		set @query = 'dbcc checkident(' + @tableName + ', reseed, 0)'
		exec (@query)
	end
go

create or alter procedure evaluateView @viewID int
as
	declare @viewName varchar(50)
	declare @query varchar(100)
	set @viewName = (select Views.Name from Views where Views.ViewID = @viewID)
	
	set @query = 'select * from ' + @viewName
	exec (@query)
go


create or alter procedure runTest @testID int
as

	declare @testName varchar(50)
	set @testName = (select Tests.Name from Tests where Tests.TestID = @testID)
	insert into TestRuns values (@testName, NULL, NULL) -- inserting to get id for tables and views tests
	declare @testRunID int
	set @testRunID = (select top 1 TestRunID from TestRuns order by TestRunID desc)

	-- declaring timestamps
	declare @startTime datetime
	declare @endTime datetime
	declare @tableStart datetime
	declare @tableEnd datetime
	declare @viewStart datetime
	declare @viewEnd datetime

	-- setting the start time of execution
	set @startTime = getdate() 

	-- check if testID is valid
	declare @tests int
	set @tests = (select count(*) from Tests)
	if @testID > @tests
	begin
		print 'TestID too big!'
		return
	end

	print 'Running test ' + convert(varchar(5), @testID)

	print 'Deleting from tables...' 
	declare @n2 int
	declare @tableID2 int
	declare @rows2 int
	
	-- we put into deleteCursor all table IDs for the given test ID
	declare deleteCursor cursor for select TestTables.TableID from TestTables where TestTables.TestID = @testID order by Position desc
	open deleteCursor
	select @n2 = (select count(*) from TestTables where TestTables.TestID = @testID)

	-- we delete the values from all the tables with IDs selected
	while @n2 > 0
	begin
		fetch deleteCursor into @tableID2
		exec deleteValues @tableID = @tableID2
		set @n2 = @n2 - 1
	end

	close deleteCursor
	deallocate deleteCursor

	print 'Inserting into tables...' 
	declare @n1 int
	declare @tableID1 int
	declare @rows1 int
	
	-- we put into insertCursor all table IDs and number of rows for the given test ID
	declare insertCursor cursor for select TestTables.TableID, TestTables.NoOfRows from TestTables where TestTables.TestID = @testID order by Position 
	open insertCursor
	select @n1 = (select count(*) from TestTables where TestTables.TestID = @testID)

	-- we insert values into the tables with IDs selected
	while @n1 > 0
	begin
		fetch insertCursor into @tableID1, @rows1

		set @tableStart = getdate()
		exec insertValues @tableID = @tableID1, @rows = @rows1
		set @tableEnd = getdate()
		insert into TestRunTables values(@testRunID, @tableID1, @tableStart, @tableEnd)
		print 'Inserted...'

		set @n1 = @n1 - 1
	end

	close insertCursor
	deallocate insertCursor

	print 'Evaluating the views...'
	declare @n3 int
	declare @viewID int

	-- we put into viewCursor the view IDs for the given test ID
	declare viewCursor cursor for select TestViews.ViewID from TestViews where TestViews.TestID = @testID
	open viewCursor
	select @n3 = (select count(*) from TestViews where TestViews.TestID = @testID)
	
	-- we evaluate the view of view IDs selected
	while @n3 > 0
	begin
		fetch viewCursor into @viewID

		set @viewStart = getdate()
		exec evaluateView @viewID = @viewID		
		set @viewEnd = getdate()
		insert into TestRunViews values(@testRunID, @viewID, @viewStart, @viewEnd)

		set @n3 = @n3 - 1
	end

	close viewCursor
	deallocate viewCursor

	set @endTime = getdate()
	update TestRuns
	set StartAt = @startTime, EndAt = @endTime
	where TestRunID = @testRunID
go

create or alter procedure runAllTests
as
	exec resetAll
	declare @n int
	declare @testID int
	declare @testName varchar(50)

	-- we put into testCursor all tests
	declare testCursor cursor for select Tests.TestID, Tests.Name from Tests
	open testCursor

	select @n = (select count(*) from Tests)

	-- we run all tests in the testCursor
	while @n > 0
	begin
		fetch testCursor into @testID, @testName
		print 'Running test with id ' + convert(varchar(5), @testID) + ' and test name ' + @testName
		exec runTest @testID = @testID
		set @n = @n - 1
	end

	close testCursor
	deallocate testCursor
go

create or alter procedure resetAll
as
	delete from TestRunTables
	delete from TestRunViews
	delete from TestRuns
	dbcc checkident('TestRuns', reseed, 0)

	delete from TestTables
	delete from TestViews
	delete from Tests
	dbcc checkident('Tests', reseed, 0)

	delete from Tables
	dbcc checkident('Tables', reseed, 0)

	delete from Views
	dbcc checkident('Views', reseed, 0)

	exec addEverything
go

exec runAllTests
select * from TestRuns
select * from TestRunTables
select * from TestRunViews




