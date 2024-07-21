<?php

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    include 'db_connection.php';
}



// Get data from request
$ChallengeNo = $_POST['ChallengeNumber'];
$ChallengeName = $_POST['ChallengeName'];
$OpeningDate = $_POST['OpeningDate'];
$ClosingDate = $_POST['ClosingDate'];
$ChallengeDuration = $_POST['ChallengeDuration'];
$NumberOfPresentedQuestions = $_POST['NumberOfPresentedQuestions'];

// Prepare and bind
$stmt = $conn->prepare("INSERT INTO challenges (ChallengeNumber,ChallengeName,OpeningDate,ClosingDate,ChallengeDuration,NumberOfPresentedQuestions) VALUES (?, ?, ?, ?, ?, ?)");
$stmt->bind_param("ssss", $ChallengeNumber, $ChallengeName, $OpeningDate, $ClosingDate, $ChallengeDuration, $NumberOfPresentedQuestions);
$stmt->execute();
// Execute and check
$response = "";
        // Check if insertion was successful
        
        if ($stmt->affected_rows > 0) {
            $response = "success|Challenge added successfully";
        } else {
            $response = "error|Failed to add challenge";
        }
    
    // Close prepared statement and database connection
    $stmt->close();
    $conn->close();

    // Output the response
    echo $response;

?>