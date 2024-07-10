<?php
include 'db_connection.php';

// Fetch representatives from the database
$result = $conn->query("SELECT * FROM representatives");
$representatives = $result->fetch_all(MYSQLI_ASSOC);

$result->close();
$conn->close();

$output = "";
foreach ($representatives as $representative) {
    $output .= implode("|", $representative) . "\n";
}

echo rtrim($output); // Output the delimited string


?>
