import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalTime;

public class Challenge {

    public static void retrieveQuestion(PrintWriter printWriter, BufferedReader br) throws SQLException, IOException {
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
    }
}
