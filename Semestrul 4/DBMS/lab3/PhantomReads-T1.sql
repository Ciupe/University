--select * from Players

BEGIN TRAN
	WAITFOR DELAY '00:00:05'
	insert into Players  values ('Phantom')
COMMIT TRAN

delete from Players where Pid > 5

