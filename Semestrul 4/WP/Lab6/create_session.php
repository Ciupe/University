<?php
	session_start();
    echo $_GET["fname"];
    echo $_GET["pass"];

	$con = mysqli_connect("localhost", "root", "", "students");
	if (!$con) {
		//die('Could not connect: ' . mysqli_error());
		die('Could not connect');
	}
	$result = mysqli_query($con, "SELECT * FROM loginCredentials");

	while($row = mysqli_fetch_array($result)){
		if($_GET["fname"] == $row['UserName'])
			if($_GET["pass"] == $row['Password'])
				if($row['UserType'] == "Student")
					{
						$_SESSION['validuser'] = 1;
						
						$string = explode("_",$row['Password']);
						$_SESSION['student_id'] = intval($string[1]);
					}
				else
					{
						$_SESSION['validuser'] = 0;
						$_SESSION['group'] = $row['StudentGroup'];
					}
	}

	header("Location: secure_page.php");
?>