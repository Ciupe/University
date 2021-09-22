<?php
include('connection.php');
include('item.php');

try {
    if ($_SERVER['REQUEST_METHOD'] === 'GET') {
        $connection = OpenConnection();
        //$genre = json_decode(file_get_contents('php://input'), true)['genre'];
        $sql = "SELECT * FROM etemplate1.item";
        $result_set = $connection->query($sql);
        $rows = array();
        while ($row = mysqli_fetch_array($result_set)) {
            $rows[] = new Item($row['id'], $row['column1'], $row['column2'], $row['column3'], $row['column4']);
        }
        
        echo json_encode(array("items" => $rows));
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