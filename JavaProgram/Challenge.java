/*import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalTime;*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Challenge {
    private static Map<String, Integer> challengeCounts = new HashMap<>(); 

    /*public static void retrieveQuestion(PrintWriter printWriter, BufferedReader br) throws SQLException, IOException {
        final String DB_URL = "jdbc:mysql://localhost:3306/dummy_db";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try (Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD)) {
            String questionSql = "SELECT QuestionText from Question ORDER BY RAND() LIMIT 2";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(questionSql)) {

                // Fetch challenge duration
                String durationSql = "SELECT Duration from Challenge WHERE ChallengeID = 'CH01'";
                int durationMinutes;
                try (Statement pstmt = conn.createStatement(); ResultSet rst = pstmt.executeQuery(durationSql)) {
                    if (rst.next()) {
                        durationMinutes = rst.getInt("Duration");
                    } else {
                        throw new SQLException("Challenge duration not found.");
                    }
                }

                LocalTime startTime = LocalTime.now();
                int questionCount = 10;

                while (rs.next()) {
                    if (questionCount <= 2) {
                        printWriter.println("THANK YOU FOR ATTEMPTING THIS CHALLENGE");
                        break;
                    }

                    Duration timeElapsed = Duration.between(startTime, LocalTime.now());
                    Duration timeLimit = Duration.ofMinutes(durationMinutes);

                    if (timeElapsed.compareTo(timeLimit) > 0) {
                        printWriter.println("TIME UP, THANK YOU FOR ATTEMPTING THIS CHALLENGE");
                        break;
                    }

                    Duration timeRemaining = timeLimit.minus(timeElapsed);
                    printWriter.println("Time left: " + timeRemaining.toMinutes() + ":" + timeRemaining.toSecondsPart());
                    printWriter.println("Questions left: " + (questionCount - 1));

                    String questionText = rs.getString("QuestionText");
                    printWriter.println(questionText);
                    String ans = br.readLine(); // User answer (not used currently)
                    questionCount--;
                }

                printWriter.println("THANK YOU FOR ATTEMPTING THIS CHALLENGE");
            }
        }
    }

    public static void main(String[] args) {
        // Placeholder for main method implementation.
    }*/

    

    public static void attemptChallenge(BufferedReader in,PrintWriter out)throws IOException,SQLException{
        // Initialize the score
        int score = 0;
        long totalTime = 300000; // Total time for all questions (e.g., 60000 ms = 60 seconds)
        int numberOfQuestions = 10; // Adjust this if you change the limit in the SQL query
        long[] responseTimes = new long[numberOfQuestions];

        // Reset challenge counts in the database for testing
        DatabaseConnection.resetChallengeCountsInDatabase();

        // Load initial challenge counts from the database
        DatabaseConnection.loadChallengeCountsFromDatabase();
        Scanner scanner=new Scanner(System.in);
        String command = in.readLine();
        if (command != null && command.startsWith("attemptChallenge")) {
            String[] parts = command.split(" ", 2);

            if (parts.length == 2) {
                String ChallengeNumber = parts[1].trim();

                if (!DatabaseConnection.isValidChallenge(ChallengeNumber)) {
                    System.out.println("Invalid details. Please try again.");
                }

                if (canSelectChallenge(ChallengeNumber)) {
                    List<String> questionsList = new ArrayList<>();
                    List<String> solutionsList = new ArrayList<>();
                
                    // Retrieve questions and solutions for the challenge
                    DatabaseConnection.retrieveQuestion(questionsList, solutionsList);;
                     // Start the timer
                    DisplayTiming timerThread = new DisplayTiming();
                    timerThread.startTimer(totalTime);

                    int marks = 0;
                    for (int i = 0; i < questionsList.size(); i++) {
                        if (timerThread.getRemainingTime() <= 0) {
                            System.out.println("\nTime is up!");
                            break;
                        }
            
                        String question = questionsList.get(i);
                        String solution = solutionsList.get(i);
            
                        // Present the question to the user
                        System.out.println("Remaining Questions "+(numberOfQuestions-(i+1)));
                        System.out.println("\nQuestion " + (i + 1) + ": " + question);
            
                        // Get the user's answer
                        long startTime = System.currentTimeMillis();
                        System.out.print("Your answer: ");
                        String userAnswer = scanner.nextLine();
                        long endTime = System.currentTimeMillis();
                        responseTimes[i] = endTime - startTime; // Store the response time
            
                        // Mark the answer and update the score
                        int previousScore = score;
                        score = markAnswer(userAnswer, solution, score);
            
                        // Show the result for the current question
                        int questionScore = score - previousScore;
                        System.out.println("You scored: " + questionScore + " on this question.");

                    }
                    // Stop the timer
                    timerThread.stopTimer();
                    // Print the final score
                    System.out.println("Final Score: " + marks);

                    // Update the total score in the Marks table
                    DatabaseConnection.updateMarks(ChallengeNumber, marks);

                    incrementChallengeCount(ChallengeNumber);
                    out.println("Challenge completed.");
                }else{
                    System.out.println("You have exceeded the maximum attempts for challenge: " + ChallengeNumber);
                }

            }else{
                out.println("Invalid command format. Terminating connection.");
            }

        }else{
            out.println("Invalid command. Terminating connection."); 
        }
        scanner.close();
        
    }

    public static boolean canSelectChallenge(String challenge) {
        int count = challengeCounts.getOrDefault(challenge, 0);
        return count < 3; // Allow selecting challenge up to 3 times
    }

    public static int markAnswer(String userAnswer, String solution, int score) {

        if (userAnswer.equals(solution)) {
            score += 10;
        } else if (userAnswer.isEmpty()) {
            score = score;
        } else {
            score -= 3;
        }
        return score;
    }

    public static void incrementChallengeCount(String challenge)throws SQLException {
        int count = challengeCounts.getOrDefault(challenge, 0);
        challengeCounts.put(challenge, count + 1);
        // Update database or file with new count
        DatabaseConnection.updateChallengeCountInDatabase(challenge, count + 1);
    }


}
