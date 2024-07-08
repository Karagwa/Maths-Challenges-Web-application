// add_school.php
<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    include 'db_connection.php';

    $name = $_GET['name'];
    $district = $_GET['district'];
    $regno = $_GET['regno'];

    $stmt = $conn->prepare("INSERT INTO schools (name, district, regno) VALUES (?, ?, ?)");
    $stmt->bind_param("sss", $name, $district, $regno);

    if ($stmt->execute()) {
        echo json_encode(['success' => 'School added successfully']);
    } else {
        echo json_encode(['error' => 'Failed to add school']);
    }

    $stmt->close();
    $conn->close();
}
?>
