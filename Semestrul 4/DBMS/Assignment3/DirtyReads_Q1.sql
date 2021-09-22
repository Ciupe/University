use SportsClubs
go

select * from Players

BEGIN TRAN
	UPDATE Players SET Name = 'Dirty Read'
	where Pid = 1
	WAITFOR DELAY '00:00:10'
ROLLBACK TRAN
