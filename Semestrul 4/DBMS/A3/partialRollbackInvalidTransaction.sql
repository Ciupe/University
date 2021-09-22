create or alter procedure partialRollbackInvalidTransaction
as
	begin try
		begin transaction
			-- valid data
			insert into Animal(AId, Species, Name, Gender, Age, DId) 
				values (100000, 'Big Cat', 'Theodor', 'M', 5, 1), 
					   (1053, 'Ape', 'Anne', 'F', 7, 1)
			-- save transaction until here
			save transaction validData

			-- invalid animal id
			insert into AnimalFood(AId, FId, Month, Year)
				values (99999, 1, 'December', 2019)

		commit transaction
	end try
	begin catch
		if @@TRANCOUNT > 0
		begin
			-- rollback to saved point
			rollback transaction validData
			-- commit needed in order to decrease @@TRANCOUNT
			commit transaction

			declare @errorMessage nvarchar(4000) = ERROR_MESSAGE()
			declare @ErrorSeverity int = ERROR_SEVERITY()
			declare @ErrorState int = ERROR_STATE()
 
		end
		raiserror(@errorMessage, @ErrorSeverity, @ErrorState);
	end catch

execute partialRollbackInvalidTransaction;

select @@TRANCOUNT;

select * from Animal;
