
SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
BEGIN TRAN
	select * from Stars
	WAITFOR DELAY '00:00:15'
	select * from Stars
COMMIT TRAN


-- SOLUTION

SET TRANSACTION ISOLATION LEVEL READ COMMITTED
BEGIN TRAN
	select * from Stars
	WAITFOR DELAY '00:00:15'
	select * from Stars
COMMIT TRAN