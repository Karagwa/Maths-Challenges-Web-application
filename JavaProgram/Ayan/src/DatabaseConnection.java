import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/challenges";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL JDBC driver", e);
        }
    }

    public ResultSet getQuestions(int challengeNo) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        String sql = "SELECT questionNo, questions FROM Questions WHERE challengeNo = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, challengeNo);
        return pstmt.executeQuery();
    }

    public String getSolution(int questionNo) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        String sql = "SELECT solutions FROM Solutions WHERE questionNo = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, questionNo);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getString("solutions");
        } else {
            throw new SQLException("Solution not found for questionNo: " + questionNo);
        }
    }

    public void updateMarks(int challengeNo, int questionNo, int marks) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        String sql = "INSERT INTO Marks (challengeNo, questionNo, marks) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE marks = VALUES(marks)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, challengeNo);
        pstmt.setInt(2, questionNo);
        pstmt.setInt(3, marks);
        pstmt.executeUpdate();
    }
}







