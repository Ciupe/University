<?php
	require 'DBUtils.php';

	$db = new DBUtils ();
	
    $groupid = $_POST["groupId"];
    $id = $_POST["Id"];
    $grade1 = $_POST["Grade1"];
    $grade2 = $_POST["Grade2"];
    $page = $_POST["page"];

    $resultset = $db->selectGroup($groupid);

    for($i = 0; $i < count($resultset); $i++){
        if($resultset[$i]['Id'] == $id){
            if($grade1 != "")
                $db->update_grade1($id,$grade1);
            if($grade2 != "")
                $db->update_grade2($id,$grade2);
        }
    }

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