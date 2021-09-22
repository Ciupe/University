<?php
include('connection.php');
include('item.php');

try {
    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $connection = OpenConnection();
        $id = $_POST["id"];
        $column1 = $_POST["column1"];
        $column2 = $_POST["column2"];
        $column3 = $_POST["column3"];
        $column4 = $_POST["column4"];
        $sql = "UPDATE etemplate1.item SET column1 = '".$column1."', column2 = '".$column2."', column3 = '".$column3."', column4 = '".$column4."' WHERE id = '".$id."'";
        $connection->query($sql);
        if ($connection->affected_rows == 1) {
            echo json_encode(array("value" => "Success: item updated"));
        } else {
            echo json_encode(array("value" => "Error: item not updated"));
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