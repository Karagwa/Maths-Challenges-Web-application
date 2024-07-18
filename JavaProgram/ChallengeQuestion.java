import java.sql.*;
import java.util.*;

public class ChallengeQuestion {

    private static final String D_URL = "jdbc:mysql://localhost:3306/humphrey";
    private static final String user = "root";
    private static final String pass = "";
    private static Map<String, Integer> challengeCounts = new HashMap<>(); // hashmap keeps track of how many times a challenge has been selected

    public static void main(String[] args) throws SQLException {
        // Reset challenge counts in the database for testing
        resetChallengeCountsInDatabase();

        // Load initial challenge counts from the database
        loadChallengeCountsFromDatabase();

        Scanner scanner = new Scanner(System.in);

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
                for (int i = 0; i < 10; i++) {
                    retrieveAndDisplayQuestion(selectedChallengeNumber, scanner);
                }
                incrementChallengeCount(selectedChallengeNumber);
            } else {
                System.out.println("You have exceeded the maximum attempts for challenge: " + selectedChallengeNumber);
            }
        }

        scanner.close();
    }

    public static void retrieveAndDisplayQuestion(String challenge, Scanner scanner) throws SQLException {
        try (Connection conn = DriverManager.getConnection(D_URL, user, pass)) {
            String sql = "SELECT Question FROM Questionsss WHERE ChallengeNumber = ? ORDER BY RAND() LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, challenge);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String question = rs.getString("Question");
                System.out.println("Question: " + question);

                System.out.print("Your answer: ");
                String pupilAnswer = scanner.nextLine();
                // Move on to the next question without providing feedback
            } else {
                System.out.println("No questions found for challenge: " + challenge);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

        try (Connection conn = DriverManager.getConnection(D_URL, user, pass);
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

        try (Connection conn = DriverManager.getConnection(D_URL, user, pass);
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

        try (Connection conn = DriverManager.getConnection(D_URL, user, pass);
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

        try (Connection conn = DriverManager.getConnection(D_URL, user, pass);
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(sql);
            System.out.println("Reset all challenge counts to 0");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}