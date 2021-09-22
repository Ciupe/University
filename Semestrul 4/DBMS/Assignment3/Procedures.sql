use SportsClubs
go


create or alter procedure fullRollback 
as
	begin try
		begin transaction
		
		-- inserting valid data
		insert into Players (Name) values ('Test1')

		-- inserting invalid data
		insert into Transfers (Tid, Pid, Cost) values (100, 5, 2500)

		commit transaction
		select 'Transaction committed'
	end try

	begin catch 
		if @@TRANCOUNT > 0
			begin 
				rollback transaction
				select 'Transaction Rollbacked', ERROR_MESSAGE() as Error
			end
	end catch

execute fullRollback

select * from Players
select * from Transfers

delete from Transfers where Cost > 2000
delete from Players where Pid > 5


create or alter procedure partialRollback 
as
	begin try
		begin transaction
		
		-- inserting valid data
		insert into Players (Name) values ('Test1')
		save transaction validTransaction

		-- inserting invalid data
		insert into Transfers (Tid, Pid, Cost) values (100, 5, 2500)

		commit transaction
		select 'Transaction committed'
	end try

	begin catch 
		if @@TRANCOUNT > 0
			begin 
				rollback transaction validTransaction
				-- we commit the transaction in order to decrease TRANCOUNT
				commit transaction	
				select 'Transaction Rollbacked', ERROR_MESSAGE() as Error
			end
	end catch

execute partialRollback

select * from Players 
select * from Transfers

delete from Transfers where Cost > 2000
delete from Players where Pid > 5

select @@TRANCOUNT