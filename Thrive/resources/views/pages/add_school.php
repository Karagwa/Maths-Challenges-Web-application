// add_school.php
<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    include 'db_connection.php';

   
    $name = $_POST['name'];
    $district = $_POST['district'];
    $regno = $_POST['regno'];

    $stmt = $conn->prepare("INSERT INTO schools (name, district, regno) VALUES (?, ?, ?)");
    $stmt->bind_param("sss", $name, $district, $regno);
    $stmt->execute();

    $response = "";
    if ($stmt->affected_rows > 0) {
        $response = "success|School added successfully";
    } else {
        $response = "error|Failed to add school";
    }

    $stmt->close();
    $conn->close();

    // Output the response as an array
    echo $response;
}
?>

