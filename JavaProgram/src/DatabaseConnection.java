/**
 * Represents a connection to a database.
 * This class provides methods for interacting with a database, such as authenticating users.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Thrive";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "yourpassword";



    public static String authenticateRepresentative(String username, String password,BufferedReader in, PrintWriter out){

         try {

            //Setting up the connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
            System.out.println("Connected to the database successfully!");
            

            //Setting up my object to send and excute the sql statements
            //i will use preparedStatements to prevent sql injection

            String sql = "SELECT * FROM SchoolRepresentative WHERE username = ? AND password = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

              // Process the results
            if (rs.next()) {
                System.out.println("User authenticated successfully!");
                return "1";
            } else {
                System.out.println("Invalid username or password.");
               return "0";
            }  
        } catch (SQLException e) {
            e.printStackTrace();
            return "0";
        }


    }


    public static void authenticateApplicant(String username, String registrationNumber,BufferedReader in, PrintWriter out){
        try {

           //Setting up the connection to the database
           Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
           System.out.println("Connected to the database successfully!");
           

           //Setting up my object to send and excute the sql statements
           //i will use preparedStatements to prevent sql injection

           String sql = "SELECT * FROM Accepted WHERE username = ? AND RegNo = ?";
           PreparedStatement pstmt = connection.prepareStatement(sql);
           pstmt.setString(1, username);
           pstmt.setString(2, registrationNumber);
           ResultSet rs = pstmt.executeQuery();

             // Process the results
           if (rs.next()) {
               System.out.println("User authenticated successfully!");
               out.println("1");
           } else {
               System.out.println("Invalid username or password.");
               out.println("0");
           }


           
       } catch (SQLException e) {
           e.printStackTrace();
       }


   }
   public static String checkRepresentativeEmail(String registrationNumber){
        try {

        //Setting up the connection to the database
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
        System.out.println("Connected to the database successfully!");
        

        //Setting up my object to send and excute the sql statements
        //i will use preparedStatements to prevent sql injection

        String sql = "SELECT Email FROM SchoolRepresentative WHERE SchRegNo = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, registrationNumber);
        ResultSet rs = pstmt.executeQuery();

            // Process the results
        rs.next();
        String email = rs.getString("Email");

        return email;


        
        } catch (SQLException e) {
        e.printStackTrace();

        return null;
        }

    }

    public static void validateSchool(String registrationNumber,BufferedReader in, PrintWriter out){
        try {

            //Setting up the connection to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
            System.out.println("Connected to the database successfully!");
            
 
            //Setting up my object to send and excute the sql statements
            //i will use preparedStatements to prevent sql injection
 
            String sql = "SELECT SchName FROM School WHERE username = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, registrationNumber);
            ResultSet rs = pstmt.executeQuery();
 
              // Process the results
            if (rs.next()) {
                System.out.println("Valid school.");
                out.println("1");
            } else {
                System.out.println("invalid school registration number");
                out.println("0");
            }
 
 
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 

}
