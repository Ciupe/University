select * from Planets
select * from Stars
select * from PlanetsIlluminatedByStars

delete from PlanetsIlluminatedByStars

delete from Planets where Pid > 13
delete from Stars where Stid > 1200

create FUNCTION ValidPlanet( @planetname varchar(50),@radius int) RETURNS INT AS
BEGIN
	declare @is_valid INT
	set @is_valid = 1
	if(@radius < 1000) set @is_valid = 0
	if (@planetname COLLATE Latin1_General_BIN  LIKE '[a-z]%')set @is_valid = 0
	return @is_valid
END

create FUNCTION ValidStar(@starname varchar(50)) RETURNS INT AS
BEGIN
	declare @is_valid INT
	set @is_valid = 1
	if (@starname COLLATE Latin1_General_BIN  LIKE '[a-z]%')set @is_valid = 0
	return @is_valid
END


create or alter procedure AddWithRollBack @planetname varchar(50),@radius int,@starname varchar(50) as
begin
	BEGIN TRAN

	BEGIN TRY
		declare @ssid INT
		select @ssid = Ssid from SolarSystems
		if(dbo.ValidPlanet(@planetname, @radius) <> 1)
			RAISERROR('Planet is invalid',14,1)
		insert into Planets values (@planetname,@radius,@ssid)


		declare @cid int
		select @cid = Cid from Constellations
		if(dbo.ValidStar(@starname) <> 1)
			RAISERROR('Star is invalid',14,1)
		insert into Stars values (@starname, @cid)

		declare @pid int
		declare @stid int

		select @pid = MAX(Pid) from Planets
		select @stid = MAX(Stid) from Stars

		insert into PlanetsIlluminatedByStars values(@pid, @stid)

		COMMIT TRAN
		SELECT 'Transaction committed'
	END TRY

	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked', ERROR_MESSAGE() as Error
	END CATCH
end

exec AddWithRollBack 'Kepler', 2534, 'Briana'
exec AddWithRollBack 'kepler', 2534, 'Briana'
exec AddWithRollBack 'Kepler', 234, 'Briana'
exec AddWithRollBack 'Kepler', 2534, 'briana'

create procedure AddWithRecover @planetname varchar(50),@radius int,@starname varchar(50) as
begin
	declare @ok1 int
	declare @ok2 int
	set @ok1 = 0
	set @ok2 = 0

	BEGIN TRAN --Planet

	BEGIN TRY
		declare @ssid INT
		select @ssid = Ssid from SolarSystems
		if(dbo.ValidPlanet(@planetname, @radius) <> 1)
			RAISERROR('Planet is invalid',14,1)
		insert into Planets values (@planetname,@radius,@ssid)
		
		COMMIT TRAN
		SELECT 'Planet Transaction committed'
		set @ok1 = 1
	END TRY

	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked', ERROR_MESSAGE() as Error
	END CATCH

	BEGIN TRAN --Star

	BEGIN TRY
		declare @cid int
		select @cid = Cid from Constellations
		if(dbo.ValidStar(@starname) <> 1)
			RAISERROR('Star is invalid',14,1)
		insert into Stars values (@starname, @cid)
		
		COMMIT TRAN
		SELECT 'Star Transaction committed'
		set @ok2 = 1
	END TRY

	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked', ERROR_MESSAGE() as Error
	END CATCH


	BEGIN TRAN --MN

	BEGIN TRY
		if(@ok1 = 0 or @ok2 = 0)
			RAISERROR('One member failed to be added into DB',14,1)

		declare @pid int
		declare @stid int

		select @pid = MAX(Pid) from Planets
		select @stid = MAX(Stid) from Stars
		
	
		insert into PlanetsIlluminatedByStars values(@pid, @stid)
		
		COMMIT TRAN
		SELECT 'MN Transaction committed'
	END TRY

	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked', ERROR_MESSAGE() as Error
	END CATCH
end

exec AddWithRecover 'Ceres', 2534, 'Eridanus'
exec AddWithRecover 'kepler', 2534, 'Acamar'
exec AddWithRecover 'Makemake', 3234, 'absolutno'
exec AddWithRecover 'kepler', 2534, 'briana'