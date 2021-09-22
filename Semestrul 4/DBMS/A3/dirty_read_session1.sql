select @@TRANCOUNT;
select * from Animal;

DBCC useroptions;
-- issue:
set transaction isolation level READ UNCOMMITTED;
-- solution:
set transaction isolation level READ COMMITTED;

-- read uncommitted level 
-- no shared lock needed to read data => query 2 reads uncommitted data
-- rc: query 1 => query 2 => T1 rollback

-- read committed level - modified data can be read only after its process is finished
-- rr: query 1 => T1 rollback => query 2

-- run first
begin transaction -- T1
	-- query 1
	update Animal
		set Name = 'Emanuel'
	where AId = 1;

	waitfor delay '00:00:06';
rollback transaction