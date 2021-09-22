DBCC useroptions;
set transaction isolation level SNAPSHOT;
select @@TRANCOUNT;

-- run second
-- query 2
update Animal
	set Name = 'Emanuela'
where AId = 1;