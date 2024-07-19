import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

public class ChallengeServer {
    private static final String D_URL = "jdbc:mysql://localhost:3306/humphrey";
    private static final String user = "root";
    private static final String pass = "";
    private static Map<String, Integer> challengeCounts = new HashMap<>();

    public static void main(String[] args) throws SQLException {
        try (ServerSocket serverSocket = new ServerSocket(12357)) {
            System.out.println("Server started, waiting for client...");

            // Load initial challenge counts from the database
            loadChallengeCountsFromDatabase();

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    System.out.println("Client connected");

                    String command = in.readLine();
                    if (command != null && command.startsWith("attemptChallenge")) {
                        String[] parts = command.split(" ", 2);
                        if (parts.length == 2) {
                            String ChallengeNumber = parts[1].trim();
                            

                            if (!DatabaseConnection.isValidChallenge(ChallengeNumber)) {
                                out.println("Invalid details. Terminating connection.");
                                continue;
                            }

                            if (canSelectChallenge(ChallengeNumber)) {
                                for (int i = 0; i < 10; i++) {
                                    String question = retrieveQuestion(ChallengeNumber);
                                    if (question != null) {
                                        out.println("Question: " + question);
                                        in.readLine(); // Read the user's answer, but do nothing with it
                                    } else {
                                        out.println("No questions found for challenge: " + ChallengeNumber);
                                    }
                                }
                                incrementChallengeCount(ChallengeNumber);
                                out.println("Challenge completed.");
                            } else {
                                out.println("You have exceeded the maximum attempts for challenge: " + ChallengeNumber);
                            }
                        } else {
                            out.println("Invalid command format. Terminating connection.");
                        }
                    } else {
                        out.println("Invalid command. Terminating connection.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String retrieveQuestion(String challenge) {
        try (Connection conn = DriverManager.getConnection(D_URL, user, pass)) {
            String sql = "SELECT Question FROM Questionsss WHERE ChallengeNumber = ? ORDER BY RAND() LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, challenge);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("Question");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean canSelectChallenge(String challenge) {
        int count = challengeCounts.getOrDefault(challenge, 0);
        return count < 3; // Allow selecting challenge up to 3 times
    }

    public static void incrementChallengeCount(String challenge) {
        int count = challengeCounts.getOrDefault(challenge, 0);
        challengeCounts.put(challenge, count + 1);
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

    /*public static boolean isValidChallenge(String challengeNumber, String challengeName) {
        String sql = "SELECT COUNT(*) FROM challenge WHERE ChallengeNumber = ? AND ChallengeName = ?";

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
    }*/
}