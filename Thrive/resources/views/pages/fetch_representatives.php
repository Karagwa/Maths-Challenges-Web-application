<?php
include 'db_connection.php';

$result = $conn->query("SELECT * FROM representatives");
$representatives = $result->fetch_all(MYSQLI_ASSOC);

echo json_encode($representatives);

$result->close();
$conn->close();
?>