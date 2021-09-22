<?php
	require 'DBUtils.php';

	$db = new DBUtils ();
	//$resultset = $db->select();
    $groupid = $_GET["groupId"];
	$page = $_GET["page"];
    $resultset = $db->selectGroup($groupid);

	//var_dump($resultset);
	//echo $resultset[0]['FirstName'];
    echo "<table border='1'><tr><th>Id</th><th>First Name</th><th>Last Name</th><th>Student Group</th><th>Grade_C1</th><th>Grade_C2</th></tr>";

	for($i = $page; $i < count($resultset) && $i < $page + 4 ; $i++){
		echo "<tr>";
        echo "<td>" . $resultset[$i]['Id'] . "</td>";
        echo "<td>" . $resultset[$i]['FirstName'] . "</td>";
		echo "<td>" . $resultset[$i]['LastName'] . "</td>";
		echo "<td>" . $resultset[$i]['StudentGroup'] . "</td>";
		echo "<td>" . $resultset[$i]['Grade_C1'] . "</td>";
        echo "<td>" . $resultset[$i]['Grade_C2'] . "</td>";
		echo "</tr>";
	}
	echo "</table>";
?>