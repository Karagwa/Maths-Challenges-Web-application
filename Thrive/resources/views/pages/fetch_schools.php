<?php
include 'db_connection.php';

$result = $conn->query("SELECT * FROM schools");
$schools = $result->fetch_all(MYSQLI_ASSOC);

$result->close();
$conn->close();

$output = "";
foreach ($schools as $school) {
    $output .= implode("|", $school) . "\n";
}

echo rtrim($output); // Output the delimited string


?>
