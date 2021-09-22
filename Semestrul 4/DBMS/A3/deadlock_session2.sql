begin transaction
	update Client
		set Name = 'Sergiu'
	where CId = 2;

	update Client
		set Name = 'Paul'
	where CId = 1;

commit transaction

select @@spid;

select @@TRANCOUNT;

BEGIN TRAN
  fdguhg
  kdjfghkdfjhg
  BEGIN TRAN
  fgjdlfkgj
  fgkjhdfkjghd
  CT
  dkfgjkfjgh
  jghkjgf
RT