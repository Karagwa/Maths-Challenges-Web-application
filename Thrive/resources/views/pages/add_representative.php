// add_representative.php
<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    include 'db_connection.php';

    // Extract data from POST request
    $username = $_POST['username'];
    $name = $_POST['name2'];
    $email = $_POST['email'];
    $regno = $_POST['regno'];
    $password = $_POST['password'];

    // Prepare SQL statement to insert data into representatives table
    $stmt = $conn->prepare("INSERT INTO representatives (username, name, email, regno, password) VALUES (?, ?, ?, ?)");
    $stmt->bind_param("ssss", $username, $name, $email, $regno, $password);
    


    // Execute the SQL statement
    $response = "";
        // Check if insertion was successful
        if($stmt->execute()){
        if ($stmt->affected_rows > 0) {
            $response = "success|Representative added successfully";
        } else {
            $response = "error|Failed to add representative";
        }
    }else{
        echo "failed to add";
    }
    // Close prepared statement and database connection
    $stmt->close();
    $conn->close();

    // Output the response
    echo $response;
}
?>






