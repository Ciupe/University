alter database OuterSpace set ALLOW_SNAPSHOT_ISOLATION on

waitfor delay '00:00:10'
BEGIN TRAN
	UPDATE Stars SET Name = 'Arrrrrrrrrrr' where Stid = 1
	WAITFOR DELAY '00:00:10'
COMMIT TRAN

alter database OuterSpace set ALLOW_SNAPSHOT_ISOLATION off
