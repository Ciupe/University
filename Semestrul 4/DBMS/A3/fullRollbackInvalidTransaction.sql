create or alter procedure fullyRollbackInvalidTransaction
as
	begin try
		begin transaction
			-- valid data
			insert into Animal(AId, Species, Name, Gender, Age, DId) 
				values (100000, 'Big Cat', 'Theodor', 'M', 5, 1), 
					   (1053, 'Ape', 'Anne', 'F', 7, 1)
			-- invalid animal id
			insert into AnimalFood(AId, FId, Month, Year)
				values (99999, 1, 'December', 2019)

		commit transaction
	end try
	begin catch
		if @@TRANCOUNT > 0
		begin
			-- also decreases @@TRANCOUNT
			rollback transaction

			declare @errorMessage nvarchar(4000) = ERROR_MESSAGE()
			declare @ErrorSeverity int = ERROR_SEVERITY()
			declare @ErrorState int = ERROR_STATE()
 
		end
		raiserror(@errorMessage, @ErrorSeverity, @ErrorState);
	end catch

execute fullyRollbackInvalidTransaction;

select * from Animal;

delete from Animal where AId = 100000 or AId = 1053;

select @@TRANCOUNT;


select * from Food;

delete from Food where Food.FId > 10;

select * from AnimalFood;

delete from AnimalFood where AnimalFood.FId > 10;
