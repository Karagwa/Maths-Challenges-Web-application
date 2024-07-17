import java.sql.*;
import java.util.*;

    public class Questionss {
        public static void main(String[] args) throws SQLException {
            // Initialize the score
            int score = 0;

            // Retrieve questions and solutions
            List<String> questionsList = new ArrayList<>();
            List<String> solutionsList = new ArrayList<>();
            retrieveQuestion(questionsList, solutionsList);

            // Convert list to array
            //String[] questionsArray = questionsList.toArray(new String[0]);
            //String[] solutionsArray = solutionsList.toArray(new String[0]);


            // Print the array
           // for (String question : questionsArray) {
               // System.out.println("Question: " + question);
           // }



            // Simulate user answers (this would normally come from user input)
            Scanner scanner = new Scanner(System.in);

            for (int i = 0; i < questionsList.size(); i++) {
                String question = questionsList.get(i);
                String solution = solutionsList.get(i);

                // Present the question to the user
                System.out.println("Question " + (i + 1) + ": " + question);

                // Get the user's answer
                System.out.print("Your answer: ");
                String userAnswer = scanner.nextLine();

                // Mark the answer and update the score
                int previousScore = score;
                score = markAnswer(userAnswer, solution, score);

                // Show the result for the current question
                int questionScore = score - previousScore;
                System.out.println("You scored: " + questionScore + " on this question.");

                // Mark the answer and update the score
                score = markAnswer(userAnswer, solution, score);
            }

            // Print the final score
            System.out.println("Final Score: " + score);

            // Update the total score in the database
            updateMarks(score);
        }

        public static void retrieveQuestion(List<String> questionsList, List<String> solutionsList) throws SQLException {
            final String D_URL = "jdbc:mysql://localhost:3306/challenges";
            final String user = "root";
            final String pass = "";

            try (Connection conn = DriverManager.getConnection(D_URL, user, pass)) {
                String sql = "SELECT questions.questions, solutions.solutions " +
                        "FROM questions  " +
                        "JOIN solutions  ON questions.QuestionNo = solutions.QuestionNo " +
                        "ORDER BY RAND() LIMIT 10";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    String question = rs.getString("questions");
                    String solution = rs.getString("solutions");

                    questionsList.add(question);
                    solutionsList.add(solution);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
        public static void updateMarks(int marks) throws SQLException {
            final String D_URL = "jdbc:mysql://localhost:3306/challenges";
            final String user = "root";
            final String pass = "";

            try (Connection connection = DriverManager.getConnection(D_URL, user, pass)) {
                String sql = "INSERT INTO Marks (marks) VALUES (?) " +
                        "ON DUPLICATE KEY UPDATE marks = VALUES(marks)";
                try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                    pstmt.setInt(1, marks);
                    pstmt.executeUpdate();
                }
            }
        }
       /** public void updateMarks(int questionNo, int score, int questionscore) throws SQLException {
            final String D_URL = "jdbc:mysql://localhost:3306/challenges";
            final String user = "root";
            final String pass = "";
            Connection connection = DriverManager.getConnection(D_URL, user, pass);
            String sql = "INSERT INTO Marks (challengeNo, questionNo, marks) VALUES (?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE marks = VALUES(marks)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, questionNo );
            pstmt.setInt(2,score);
            pstmt.setInt(3, questionscore);
            pstmt.executeUpdate();
        }**/
    }
