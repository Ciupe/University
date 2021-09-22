SET TRANSACTION ISOLATION LEVEL SNAPSHOT
BEGIN TRAN
	Select * from Stars where Stid = 1
	WAITFOR DELAY '00:00:10'
	select * from Stars where Stid=100
	update Stars set Name = 'Brrrrrrr' where Stid=1
COMMIT TRAN

