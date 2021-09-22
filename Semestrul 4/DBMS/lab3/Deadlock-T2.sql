
BEGIN TRAN
	UPDATE Players set Name = 'DeadlockQ2' where Pid = 1
	WAITFOR DELAY '00:00:10'
	UPDATE Managers set Name = 'DeadlockQ2' where Mid = 1
COMMIT TRAN


SET DEADLOCK_PRIORITY HIGH
BEGIN TRAN
	UPDATE Players set Name = 'DeadlockQ2' where Pid = 1
	WAITFOR DELAY '00:00:10'
	UPDATE Managers set Name = 'DeadlockQ2' where Mid = 1
COMMIT TRAN

select * from Players
select * from Managers

UPDATE Players set Name = 'Ciupe' where Pid = 1
UPDATE Managers set Name = 'Alina' where Mid = 1
