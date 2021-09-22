select @@TRANCOUNT;
select * from Animal;
update Animal
	set Name = 'Ana'
where AId = 1;

DBCC useroptions;
-- issue:
set transaction isolation level READ COMMITTED;
-- solution:
ALTER DATABASE Zoo SET ALLOW_SNAPSHOT_ISOLATION ON;
set transaction isolation level SNAPSHOT;

-- snapshot level - row versioning
-- modified rows are copied to tempdb
-- when another session reads the same data, the data
--before the first transaction begain is returned
--which leads to a conflict similar to deadlock
-- run first
begin transaction
	-- query 1
	update Animal
		set Name = 'Emanuel'
	where AId = 1;

	waitfor delay '00:00:06';
commit transaction