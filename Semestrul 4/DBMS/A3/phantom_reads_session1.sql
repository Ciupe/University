select @@TRANCOUNT;
select * from Animal;
update Animal
	set Age = 18
where Name = 'Anne';

DBCC useroptions;
-- issue:
set transaction isolation level READ COMMITTED;
-- solution:
set transaction isolation level SERIALIZABLE;

-- read committed level - shared lock released immediately
-- rc: query 1 => query 3 => query 2

-- serializable level
-- shared locks on sets of objects are released at the end
-- rr: query 1 => query 2 => query 3

-- run first
-- make sure Anne has age under 19
begin transaction
	-- query 1
	select * from Animal
		where Age > 19;

	waitfor delay '00:00:06';

	-- query 2
	select * from Animal
		where Age > 19;
commit transaction