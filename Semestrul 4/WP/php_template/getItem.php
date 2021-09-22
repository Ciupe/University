<?php
include('connection.php');
include('item.php');

try {
    if ($_SERVER['REQUEST_METHOD'] === 'GET') {
        $connection = OpenConnection();
        $id = $_GET["id"];
        //$genre = json_decode(file_get_contents('php://input'), true)['genre'];
        $sql = "SELECT * FROM etemplate1.item WHERE id = '".$id."' LIMIT 1";
        $result_set = $connection->query($sql);
        if ($row = mysqli_fetch_array($result_set)) {
            $result = new Item($row['id'], $row['column1'], $row['column2'], $row['column3'], $row['column4']);
        }
        
        echo json_encode($result);
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