select * from Client;

sp_helpindex Client;

begin transaction
	update Client
		set Name = 'Sergiu'
	where CId = 1;

	update Client
		set Name = 'Paul'
	where CId = 2;
commit transaction


