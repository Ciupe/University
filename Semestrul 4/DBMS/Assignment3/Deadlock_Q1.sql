use SportsClubs
go 

BEGIN TRAN
	UPDATE Managers set Name = 'DeadlockQ1' where Mid = 1
	WAITFOR DELAY '00:00:10'
	UPDATE Players set Name = 'DeadlockQ1' where Pid = 1
COMMIT TRAN

select * from Players
select * from Managers