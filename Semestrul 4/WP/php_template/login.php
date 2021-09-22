<?php
include('connection.php');
include('item.php');

session_start();
try {
    if ($_SERVER['REQUEST_METHOD'] === 'POST') {
        $connection = OpenConnection();
        $username = $_POST["username"];
        $password = $_POST["password"];
        $sql = "SELECT id FROM etemplate1.user WHERE username = '".$username."' AND password = '".$password."'";
        $result_set = mysqli_query($connection, $sql);
        if (mysqli_num_rows($result_set)) {
            $_SESSION["id"] = mysqli_fetch_row($result_set)[0];
            $_SESSION["username"] = $username;
            header("Location: http://localhost/php_template/display.html", true, 301);
            exit();
            //echo json_encode(array("username" => $username));
        } else {
            header("Location: http://localhost/php_template/index.html", true, 301);
            exit();
            //echo json_encode(array("error" => "Invalid username/password"));
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