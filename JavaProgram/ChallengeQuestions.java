import java.sql.*;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.*;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ChallengeQuestions {

    private static final String D_URL = "jdbc:mysql://localhost:3306/challenges";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    private static Map<String, Integer> challengeCounts = new HashMap<>(); // hashmap keeps track of how many times a challenge has been selected
    private static String authenticatedUsername;
    private static String authenticatedRegistrationNumber;

    public static void main(String[] args) throws SQLException {
        // Reset challenge counts in the database for testing
        resetChallengeCountsInDatabase();

        // Load initial challenge counts from the database
        loadChallengeCountsFromDatabase();

        Scanner scanner = new Scanner(System.in);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out), true);

        // Prompt for username and registration number
        System.out.print("Enter your username: ");
        authenticatedUsername = scanner.nextLine();
        System.out.print("Enter your registration number: ");
        authenticatedRegistrationNumber = scanner.nextLine();

        // Authenticate the user
        if (!authenticateApplicant(authenticatedUsername, authenticatedRegistrationNumber, in, out)) {
            System.out.println("Authentication failed. Exiting.");
            return;
        }

        while (true) {
            System.out.print("Enter a challenge number (or 'exit' to quit): ");
            String selectedChallengeNumber = scanner.nextLine();

            if (selectedChallengeNumber.equalsIgnoreCase("exit")) {
                break;
            }

            System.out.print("Enter the challenge name: ");
            String selectedChallengeName = scanner.nextLine();

            if (!isValidChallenge(selectedChallengeNumber, selectedChallengeName)) {
                System.out.println("Invalid details. Please try again.");
                continue;
            }

            if (canSelectChallenge(selectedChallengeNumber)) {
                // Update the Marks table with the challenge number and initialize the attempt
                initializeChallengeAttempt(selectedChallengeNumber);

                List<String> arrayQuestions = new ArrayList<>();
                List<String> arrayAnswers = new ArrayList<>();
                List<Integer> questionNumbers = new ArrayList<>();

                // Retrieve questions and solutions for the challenge
                retrieveQuestionsAndSolutions(selectedChallengeNumber, arrayQuestions, arrayAnswers, questionNumbers);

                int marks = 0;
                for (int i = 0; i < arrayQuestions.size(); i++) {
                    String question = arrayQuestions.get(i);
                    String correctAnswer = arrayAnswers.get(i);
                    int questionNo = questionNumbers.get(i);

                    // Present the question to the user
                    System.out.println("Question " + (i + 1) + ": " + question);

                    // Get the user's answer
                    System.out.print("Your answer: ");
                    String userAnswer = scanner.nextLine();

                    // Mark the answer and update the score
                    int questionScore = markAnswer(userAnswer, correctAnswer);
                    marks += questionScore;

                    // Show the result for the current question
                    System.out.println("You scored: " + questionScore + " on this question.");
                    
                    // Update the score in the database
                    updateQuestionScore(selectedChallengeNumber, questionNo, questionScore);
                }

                // Print the final score
                System.out.println("Final Score: " + marks);

                // Update the total score and challenge count in the Marks table
                updateMarks(selectedChallengeNumber, marks);

                incrementChallengeCount(selectedChallengeNumber);
            } else {
                System.out.println("You have exceeded the maximum attempts for challenge: " + selectedChallengeNumber);
            }
        }

        scanner.close();
    }

    public static void retrieveQuestionsAndSolutions(String challenge, List<String> arrayQuestions, List<String> arrayAnswers, List<Integer> questionNumbers) throws SQLException {
        try (Connection conn = DriverManager.getConnection(D_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT questions.questionNo, questions.questions, solutions.solutions " +
                         "FROM questions  " +
                         "JOIN solutions  ON questions.questionNo = solutions.questionNo " +
                         "WHERE questions.ChallengeNumber = ? " +
                         "ORDER BY RAND() LIMIT 10";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, challenge);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int questionNo = rs.getInt("questionNo");
                String question = rs.getString("questions");
                String solution = rs.getString("solutions");

                questionNumbers.add(questionNo);
                arrayQuestions.add(question);
                arrayAnswers.add(solution);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int markAnswer(String userAnswer, String correctAnswer) {
        if (userAnswer.equals(correctAnswer)) {
            return 10;
        } else if (userAnswer.isEmpty()) {
            return 0;
        } else {
            return -3;
        }
    }

    public static boolean canSelectChallenge(String challenge) {
        int count = challengeCounts.getOrDefault(challenge, 0);
        return count < 3; // Allow selecting challenge up to 3 times
    }

    public static void incrementChallengeCount(String challenge) {
        int count = challengeCounts.getOrDefault(challenge, 0);
        challengeCounts.put(challenge, count + 1);
        // Update database or file with new count
        updateChallengeCountInDatabase(challenge, count + 1);
    }

    public static void loadChallengeCountsFromDatabase() {
        String sql = "SELECT ChallengeNumber, ChallengeCount FROM ChallengeTable";

        try (Connection conn = DriverManager.getConnection(D_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String challengeNumber = rs.getString("ChallengeNumber");
                int challengeCount = rs.getInt("ChallengeCount");
                challengeCounts.put(challengeNumber, challengeCount);
                System.out.println("Loaded challenge " + challengeNumber + " with count " + challengeCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateChallengeCountInDatabase(String challenge, int count) {
        String sql = "UPDATE ChallengeTable SET ChallengeCount = ? WHERE ChallengeNumber = ?";

        try (Connection conn = DriverManager.getConnection(D_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, count);
            stmt.setString(2, challenge);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Updated challenge count for " + challenge + " to " + count);
            } else {
                System.out.println("Challenge " + challenge + " not found in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidChallenge(String challengeNumber, String challengeName) {
        String sql = "SELECT COUNT(*) FROM ChallengeTable WHERE ChallengeNumber = ? AND ChallengeName = ?";

        try (Connection conn = DriverManager.getConnection(D_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, challengeNumber);
            stmt.setString(2, challengeName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static void resetChallengeCountsInDatabase() {
        String sql = "UPDATE ChallengeTable SET ChallengeCount = 0";

        try (Connection conn = DriverManager.getConnection(D_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("Reset all challenge counts to 0");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateMarks(String challengeNumber, int marks) throws SQLException {
        try (Connection connection = DriverManager.getConnection(D_URL, DB_USER, DB_PASSWORD)) {
            // Get current challenge count
            int challengeCount = getChallengeCount(authenticatedUsername, challengeNumber);

            // Increment challenge count
            challengeCount++;

            String sql = "INSERT INTO Marks (challengeNumber, username, registrationNumber, marks, challengeCount) VALUES (?, ?, ?, ?, ?) " +
                         "ON DUPLICATE KEY UPDATE marks = VALUES(marks), challengeCount = VALUES(challengeCount)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, challengeNumber);
                pstmt.setString(2, authenticatedUsername);
                pstmt.setString(3, authenticatedRegistrationNumber);
                pstmt.setInt(4, marks);
                pstmt.setInt(5, challengeCount);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getChallengeCount(String username, String challengeNumber) {
        int count = 0;
        String sql = "SELECT challengeCount FROM Marks WHERE username = ? AND challengeNumber = ?";
        try (Connection connection = DriverManager.getConnection(D_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, challengeNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("challengeCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static void updateQuestionScore(String challengeNumber, int questionNo, int questionScore) throws SQLException {
        try (Connection connection = DriverManager.getConnection(D_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO QuestionScores (challengeNumber, username, questionNo, questionScore) VALUES (?, ?, ?, ?) " +
                         "ON DUPLICATE KEY UPDATE questionScore = VALUES(questionScore)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, challengeNumber);
                pstmt.setString(2, authenticatedUsername);
                pstmt.setInt(3, questionNo);
                pstmt.setInt(4, questionScore);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean authenticateApplicant(String username, String registrationNumber, BufferedReader in, PrintWriter out) {
        boolean isAuthenticated = false;
        try {
            // Setting up the connection to the database
            Connection connection = DriverManager.getConnection(D_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to the database successfully!");

            // Setting up my object to send and execute the SQL statements
            // I will use PreparedStatement to prevent SQL injection
            String sql = "SELECT * FROM participant WHERE Username = ? AND School_Registration_Number = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, registrationNumber);
            ResultSet rs = pstmt.executeQuery();

            // Process the results
            if (rs.next()) {
                System.out.println("User authenticated successfully!");
                out.println("1");
                isAuthenticated = true;
                authenticatedUsername = username;
                authenticatedRegistrationNumber = registrationNumber;
            } else {
                System.out.println("Invalid username or registration number.");
                out.println("0");
            }

            rs.close();
            pstmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAuthenticated;
    }

    public static void initializeChallengeAttempt(String challengeNumber) {
        try (Connection connection = DriverManager.getConnection(D_URL, DB_USER, DB_PASSWORD)) {
            // Insert or update the Marks table with the initial challenge number and attempt
            String sql = "INSERT INTO Marks (challengeNumber, username, registrationNumber, marks, challengeCount) VALUES (?, ?, ?, 0, 0) " +
                         "ON DUPLICATE KEY UPDATE challengeNumber = VALUES(challengeNumber)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, challengeNumber);
                pstmt.setString(2, authenticatedUsername);
                pstmt.setString(3, authenticatedRegistrationNumber);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}




