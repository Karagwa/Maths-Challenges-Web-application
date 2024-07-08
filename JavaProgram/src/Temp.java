import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Temp {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Thrive";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "yourpassword";


    public static void authenticateRepresentative(String username, String password, BufferedReader in, PrintWriter out) {
        String sql = "SELECT * FROM SchoolRepresentative WHERE username = ? AND password = ?";
        
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            System.out.println("Connected to the database successfully!");

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("User authenticated successfully!");
                out.println("1");
            } else {
                System.out.println("Invalid username or password.");
                out.println("0");
            }

        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
            out.println("Database connection error.");
        }
    }

    public static void main(String[] args) {
        // For demonstration purposes, the BufferedReader and PrintWriter are not connected to any client.
        // Replace them with actual streams when integrating with the client-server setup.
        BufferedReader in = null;
        PrintWriter out = new PrintWriter(System.out, true);

        // Example usage
        authenticateRepresentative("testUser", "testPassword", in, out);
    }
}
