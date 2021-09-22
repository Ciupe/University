select @@TRANCOUNT;
select * from Animal;
update Animal
	set Name = 'Theodor'
where Name = 'Eugene';

DBCC useroptions;
-- issue:
set transaction isolation level READ COMMITTED;
-- solution:
set transaction isolation level REPEATABLE READ;

-- read committed level - shared lock released immediately
-- rc: query 1 => query 3 => query 2

-- repeatable read level - shared lock released at the end
-- rr: query 1 => query 2 => query 3

-- run first
-- make sure you have Theodor
begin transaction
	-- query 1
	select * from Animal
		where Name = 'Theodor';

	waitfor delay '00:00:06';

	-- query 2
	select * from Animal
		where Name = 'Theodor';
commit transaction