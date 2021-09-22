    <?php
	$con = mysqli_connect("localhost", "root", "", "lab6");
	if (!$con) {
		die('Could not connect');
	}
	$result = mysqli_query($con, "SELECT * FROM Students");
    $student_id = $_GET["student_id"];


    echo "<table border='1'><tr><th>Id</th><th>First Name</th><th>Last Name</th><th>Student Group</th><th>Grade_C1</th><th>Grade_C2</th></tr>";

	while($row = mysqli_fetch_array($result)){
        if($row['Id'] == $student_id)
		{
            echo "<tr>";
            echo "<td>" . $row['Id'] . "</td>";
            echo "<td>" . $row['FirstName'] . "</td>";
            echo "<td>" . $row['LastName'] . "</td>";
            echo "<td>" . $row['StudentGroup'] . "</td>";
            echo "<td>" . $row['Grade_C1'] . "</td>";
            echo "<td>" . $row['Grade_C2'] . "</td>";
            echo "</tr>";
        }
	}
	echo "</table>";
?>