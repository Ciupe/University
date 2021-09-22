use SportsClubs
go

drop table Test_Table
create table Test_Table (
Tid int not null,
name varchar(50)
)
go

create or alter procedure dbo_version1
as
	alter table Tournaments
	alter column Prize varchar(50)
	print N'Changed "Prize" column type to varchar. (Version 1)'
go

create or alter procedure dbo_version1_undo
as
	alter table Tournaments
	alter column Prize int
	print N'Changed "Prize" column type back to int. (Version 1 undo)'
go

create or alter procedure dbo_version2
as
	alter table Players
	add Age int
	print N'Added column "Age" into table "Players". (Version 2)'
go

create or alter procedure dbo_version2_undo
as
	alter table Players
	drop column Age
	print N'Deleted column "Age" from table "Players". (Version 2 undo)'
go

create or alter procedure dbo_version3
as
	alter table Teams
	add constraint df_TeamName default 'Team name' for Name;
	print N'Set "Team name" as default name for table "Teams". (Version 3)'
go

create or alter procedure dbo_version3_undo
as 
	alter table Teams
	drop constraint df_TeamName
	print N'Dropped default constraint of column "Name" from table "Teams". (Version 3 undo)'
go
	
create or alter procedure dbo_version4
as 
	alter table Test_Table
	add constraint pk_Tid primary key (Tid)
	print N'Added primary key into table "Test_Table". (Version 4)'
go

create or alter procedure dbo_version4_undo
as 
	alter table Test_Table
	drop constraint pk_Tid
	print N'Removed primary key from table "Test_Table". (Version 4 undo)'
go

create or alter procedure dbo_version5
as
	alter table Teams
	add constraint club_college unique (clid, coid)
	print N'Added a candidate key into table "Teams". (Version 5)'
go

create or alter procedure dbo_version5_undo
as
	alter table Teams
	drop constraint club_college
	print N'Removed candidate key from table "Teams". (Version 5 undo)'
go

create or alter procedure dbo_version6
as
	alter table Teams
	add constraint FK_Mid foreign key (Mid) references Managers(Mid)
	print N'Added a foreign key into table "Teams". (Version 6)'
go

create or alter procedure dbo_version6_undo
as
	alter table Teams
	drop constraint FK_Mid
	print N'Removed foreign key from table "Teams". (Version 6 undo)'
go

create or alter procedure dbo_version7
as
	create table New_Table (Tid int not null)
	print N'Created the table "New_Table". (Version 7)'
go

create or alter procedure dbo_version7_undo
as
	drop table New_Table 
	print N'Dropped table "New_Table". (Version 7 undo)'
go

drop table Versions
create table Versions (
	current_version int
)
insert into Versions (current_version) values (0)
go

create or alter procedure change_version (@new_version int)
as
	if @new_version < 0 or @new_version > 7
	begin 
		print N'Error! Wrong version!'
	end

	else 
	begin
		declare @current_version int 
		select @current_version = current_version from Versions
		--print N'Started at version: '
		--print @current_version

		if (@current_version = @new_version)
		begin
			print N'Database is already at this version!'
		end

		if (@current_version < @new_version)
		begin
			print N'Increasing...'
			set @current_version = @current_version + 1
			while (@current_version <= @new_version)
			begin
				if @current_version = 1
					exec dbo_version1;
				else if @current_version = 2
					exec dbo_version2;
				else if @current_version = 3
					exec dbo_version3;
				else if @current_version = 4
					exec dbo_version4;
				else if @current_version = 5
					exec dbo_version5;
				else if @current_version = 6
					exec dbo_version6;
				else if @current_version = 7
					exec dbo_version7;
				set @current_version = @current_version + 1
			end
			set @current_version = @current_version - 1
		end

		if (@current_version > @new_version)
		begin
			print N'Decreasing...'
			while @current_version > @new_version
			begin
				if @current_version = 1
					exec dbo_version1_undo;
				else if @current_version = 2
					exec dbo_version2_undo;
				else if @current_version = 3
					exec dbo_version3_undo;
				else if @current_version = 4
					exec dbo_version4_undo;
				else if @current_version = 5
					exec dbo_version5_undo;
				else if @current_version = 6
					exec dbo_version6_undo;
				else if @current_version = 7
					exec dbo_version7_undo;
				set @current_version = @current_version - 1
			end
		end
		--print N'Stopped at version: '
		--print @current_version
		update Versions set current_version = @new_version
	end
go

update Versions set current_version = 0

exec change_version 0

exec change_version 1
exec change_version 2
exec change_version 3
exec change_version 4
exec change_version 5
exec change_version 6
exec change_version 7

exec change_version -1
exec change_version  8
