// add_representative.php
<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    include 'db_connection.php';

    $name = $_GET['name'];
    $email = $_GET['email'];

    $stmt = $conn->prepare("INSERT INTO representatives (name, email) VALUES (?, ?)");
    $stmt->bind_param("ss", $name, $email);

    if ($stmt->execute()) {
        echo json_encode(['success' => 'Representative added successfully']);
    } else {
        echo json_encode(['error' => 'Failed to add representative']);
    }

    $stmt->close();
    $conn->close();
}
?>



