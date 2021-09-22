use SportsClubs
go

create or alter procedure insertion
as

begin transaction;
set autocommit = off;

begin try

insert into Teams(CLid, COid, Name, Mid) values (1, 2, 'Example1', 5)
insert into Teams(CLid, COid, Name, Mid) values (2, 3, 'Example1', 6)

end try


begin catch 
	rollback
end catch


go