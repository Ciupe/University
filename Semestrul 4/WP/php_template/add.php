<?php
include('connection.php');
include('item.php');

try {
    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $connection = OpenConnection();
        $column1 = $_POST["column1"];
        $column2 = $_POST["column2"];
        $column3 = $_POST["column3"];
        $column4 = $_POST["column4"];
        $sql = "INSERT INTO etemplate1.item(column1, column2, column3, column4) VALUES('".$column1."', '".$column2."', '".$column3."', '".$column4."')";
        $connection->query($sql);
        if ($connection->affected_rows == 1) {
            #echo json_encode(array("value" => "Success: item added"));
            header("Location: http://localhost/php_template/display.html");
        } else {
            echo json_encode(array("value" => "Error: item not added"));
        }

        CloseConnection($connection);
        exit;
    }
} catch (Exception $e) {
    echo json_encode(
        array(
            'status' => false,
            'error' => $e->getMessage(),
            'error_code' => $e->getCode()
        )
    );
    exit;
}