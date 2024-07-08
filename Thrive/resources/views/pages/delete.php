// delete.php
<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    include 'db_connection.php';

    $id = $_POST['id'];
    $type = $_POST['type'];

    if ($type == 'school') {
        $stmt = $conn->prepare("DELETE FROM schools WHERE id = ?");
    } elseif ($type == 'representative') {
        $stmt = $conn->prepare("DELETE FROM representatives WHERE id = ?");
    }

    $stmt->bind_param("i", $id);

    if ($stmt->execute()) {
        echo json_encode(['success' => 'Record deleted successfully']);
    } else {
        echo json_encode(['error' => 'Failed to delete record']);
    }

    $stmt->close();
    $conn->close();
}
?>
