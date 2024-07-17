import java.sql.*;
import java.util.*;

public class Questionss {
    public static void main(String[] args) throws SQLException {
        
       // System.out.println("Open");
        retrieveQuestion();
    }

    public static void retrieveQuestion() throws SQLException {
        final String D_URL = "jdbc:mysql://localhost:3306/dummy";
        final String user = "root";
        final String pass = "";

        try (Connection conn = DriverManager.getConnection(D_URL, user, pass)) {
            String sql = "SELECT QuestionText FROM questions ORDER BY RAND() LIMIT 1";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            List<String> questionsList = new ArrayList<>();

            while (rs.next()) {
                
                String qtn = rs.getString("QuestionText");
                int Marks=rs.getInt("Marks");
                
                questionsList.add(qtn);
                System.out.print("marks " +Marks);
            }

            // Convert list to array
            String[] questionsArray = questionsList.toArray(new String[0]);

            // Print the array
            for (String question : questionsArray) {
                System.out.println( "   "+question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
} 
