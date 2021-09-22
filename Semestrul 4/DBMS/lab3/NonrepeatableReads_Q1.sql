
BEGIN TRAN
	WAITFOR DELAY '00:00:05'
	UPDATE Players SET Name = 'Non-Repeateable Read 1'
	where Pid = 1
COMMIT TRAN


BEGIN TRAN
	WAITFOR DELAY '00:00:05'
	UPDATE Players SET Name = 'Non-Repeateable Read 2 '
	where Pid = 1
COMMIT TRAN

