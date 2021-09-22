<?php
    session_start();
    echo $_SESSION['validuser'];
    if (!isset($_SESSION['validuser'])) {
		header('Location: login.html');
	}
    //professor
    else if($_SESSION['validuser'] == 0){
        
        header('Location: ajax-get.php');
    }
    //student
    else{
        header('Location: ajax-get-student.php');
    }
?>