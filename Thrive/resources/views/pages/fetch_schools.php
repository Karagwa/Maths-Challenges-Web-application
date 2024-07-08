<?php
include 'db_connection.php';

$result = $conn->query("SELECT * FROM schools");
$schools = $result->fetch_all(MYSQLI_ASSOC);

echo json_encode($schools);

$result->close();
$conn->close();
?>