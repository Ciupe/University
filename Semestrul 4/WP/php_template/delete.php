<?php
include('connection.php');
include('item.php');

try {
    if ($_SERVER['REQUEST_METHOD'] === 'DELETE') {
        $connection = OpenConnection();
        $id = $_GET["id"];
        $sql = "DELETE FROM etemplate1.item WHERE id = '".$id."'";
        $connection->query($sql);
        if ($connection->affected_rows == 1) {
            echo json_encode(array("value" => "Success: item deleted"));
        } else {
            echo json_encode(array("value" => "Error: item not deleted"));
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