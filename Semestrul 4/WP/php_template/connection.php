<?php

function OpenConnection(): mysqli
{
    $host = "127.0.0.1";
    $username = "root";
    $password = "";
    $name = "etemplate1";

    return mysqli_connect($host, $username, $password, $name);
}

function CloseConnection(mysqli $connection) {
    $connection->close();
}