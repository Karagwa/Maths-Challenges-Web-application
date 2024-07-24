/**
 * Represents a connection to a database.
 * This class provides methods for interacting with a database, such as authenticating users.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.PrintWriter;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Thrive";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "yourpassword";

    private static Map<String, Integer> challengeCounts = new HashMap<>(); 
    public static String authenticatedUsername;
    public static String authenticatedRegistrationNumber;


public static String authenticateRepresentative(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to the database successfully!");

            String sql = "SELECT * FROM representatives WHERE username = ? AND password = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

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

           String sql = "SELECT * FROM participant WHERE Username = ? AND School_Registration_Number = ?";
           PreparedStatement pstmt = connection.prepareStatement(sql);
           pstmt.setString(1, username);
           pstmt.setString(2, registrationNumber);
           ResultSet rs = pstmt.executeQuery();

             // Process the results
           if (rs.next()) {
               System.out.println("User authenticated successfully!");
               out.println("1");
              
                DatabaseConnection.authenticatedUsername = username;
                DatabaseConnection.authenticatedRegistrationNumber = registrationNumber;
           } else {

                String sqlRejected = "SELECT * FROM rejected WHERE Username = ? AND School_Registration_Number = ?";
                PreparedStatement pstmtRejected = connection.prepareStatement(sqlRejected);
                pstmtRejected.setString(1, username);
                pstmtRejected.setString(2, registrationNumber);
                ResultSet rsRejected = pstmtRejected.executeQuery();

                if (rsRejected.next()) {
                    System.out.println("User is in the rejected table.");
                    out.println("2");
                } else {
                    System.out.println("Invalid username or registration number.");
                    out.println("0");
                    
                }

           }


           
       } catch (SQLException e) {
           e.printStackTrace();
       }


   }

   public static boolean isUserRejected(String username, String registrationNumber) throws SQLException {
        boolean isRejected = false;

        //Setting up the connection to the database
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
        System.out.println("Connected to the database successfully!");

        // Check if the user is in the rejected table
        String sqlRejected = "SELECT * FROM rejected WHERE Username = ? AND School_Registration_Number = ?";
        PreparedStatement pstmtRejected = connection.prepareStatement(sqlRejected);
        pstmtRejected.setString(1, username);
        pstmtRejected.setString(2, registrationNumber);
        ResultSet rsRejected = pstmtRejected.executeQuery();

        if (rsRejected.next()) {
            isRejected = true;
            
        } 
        rsRejected.close();
        pstmtRejected.close();
        connection.close();

        return isRejected;

    }

    
   public static String checkRepresentativeEmail(String registrationNumber){
        try {

        //Setting up the connection to the database
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
        System.out.println("Connected to the database successfully!");
        

        //Setting up my object to send and excute the sql statements
        //i will use preparedStatements to prevent sql injection

        String sql = "SELECT Email FROM representative WHERE SchRegNo = ?";
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


    public static void addParticipant(String filePath,String targetUsername) throws IOException,SQLException{
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
        System.out.println("Connected to the database successfully!");

        BufferedReader reader=new BufferedReader(new FileReader(filePath));
        String line;
        boolean userFound = false;
        int lineCount =0;

        List<String> lines = new ArrayList<>();

        while((line=reader.readLine()) !=null){
            lineCount++;
            String[] fields = line.split(",");

            //extracting the username
            String Username = fields[0];


            //extracting the remaining values
            
            if(Username.equals(targetUsername)){
                String Firstname = fields[1];
                String Lastname = fields[2];
                String EmailAddress = fields[3];

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate Date_of_birth =LocalDate.parse(fields[4], formatter);

                String schoolRegNumber = fields[5];

                String byteString=fields[6].substring(fields[6].indexOf("[B@")+3);
                // Decode the base64 string to a byte array
                byte[] Image =byteString.getBytes();
                

                //confirm with the database actual fielnames-to  do dont forget!!!!!!!!!
                String sql ="INSERT INTO participant (Username,Firstname,Lastname, EmailAddress,Date_of_birth,School_Registration_Number,Image) VALUES (?,?,?,?,?,?,?)";

                PreparedStatement statement=connection.prepareStatement(sql);

                //set values for placeholders ?
                statement.setString(1, Username);
                statement.setString(2, Firstname);
                statement.setString(3, Lastname);
                statement.setString(4, EmailAddress);
                
                
                statement.setDate(5, java.sql.Date.valueOf(Date_of_birth));
                statement.setString(6, schoolRegNumber);
                statement.setBytes(7, Image);
            

                //executing statement
                statement.executeUpdate();

                //send success message
                System.out.println("Accepted Applicant added to database successfully -"+ lineCount);
                Email.AcceptEmail(EmailAddress);

                userFound=true;
                break; //after user is found and added
            }else{

                /*add non matching lines to the list*/
                lines.add(line);
            }
            
        }
        reader.close();

        if (userFound){
            // Write remaining lines back to the CSV file
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
                for (String remainingLine : lines) {
                    writer.println(remainingLine);
                }
            }
        }else{
            System.out.println("Username " + targetUsername + " not found in the file.");
        }
        connection.close();
          
    }



    public static void addRejected(String filePath,String targetUsername) throws IOException,SQLException{
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
        System.out.println("Connected to the database successfully!");

        BufferedReader reader=new BufferedReader(new FileReader(filePath));
        String line;
        boolean userFound = false;
        int lineCount =0;

        List<String> lines = new ArrayList<>();

        while((line=reader.readLine()) !=null){
            lineCount++;
            String[] fields = line.split(",");

            //extracting the username
            String Username = fields[0];


            //extracting the remaining values
            
            if(Username.equals(targetUsername)){
                String Firstname = fields[1];
                String Lastname = fields[2];
                String EmailAddress = fields[3];

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate Date_of_birth =LocalDate.parse(fields[4], formatter);

                String schoolRegNumber = fields[5];

                String byteString=fields[6].substring(fields[6].indexOf("[B@")+3);
                // Decode the base64 string to a byte array
                byte[] Image =byteString.getBytes();
                

                //confirm with the database actual fielnames-to  do dont forget!!!!!!!!!
                String sql ="INSERT INTO rejected (Username,Firstname,Lastname, EmailAddress,Date_of_birth,School_Registration_Number,Image) VALUES (?,?,?,?,?,?,?)";

                PreparedStatement statement=connection.prepareStatement(sql);

                //set values for placeholders ?
                statement.setString(1, Username);
                statement.setString(2, Firstname);
                statement.setString(3, Lastname);
                statement.setString(4, EmailAddress);
                
                
                statement.setDate(5, java.sql.Date.valueOf(Date_of_birth));
                statement.setString(6, schoolRegNumber);
                statement.setBytes(7, Image);
            

                //executing statement
                statement.executeUpdate();

                //send success message
                System.out.println("Rejected Applicant added to database successfully"+ lineCount);
                Email.AcceptEmail(EmailAddress);

                userFound=true;
                break; //after user is found and added
            }else{

                /*add non matching lines to the list*/
                lines.add(line);
            }
            
        }
        reader.close();

        if (userFound){
            // Write remaining lines back to the CSV file
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
                for (String remainingLine : lines) {
                    writer.println(remainingLine);
                }
            }
        }else{
            System.out.println("Username " + targetUsername + " not found in the file.");
        }
        connection.close();
          
    }

    public static void getChallenges(BufferedReader in, PrintWriter out)throws SQLException,IOException{
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
        System.out.println("Connected to the database successfully!");
        
        LocalDate today = LocalDate.now();
        String sql="SELECT ChallengeNumber,ChallengeName FROM challenge WHERE ClosingDate >= ?";

        PreparedStatement pstmt = connection.prepareStatement(sql); //prevents sql injections
        pstmt.setDate(1, java.sql.Date.valueOf(today));

        ResultSet resultSet = pstmt.executeQuery();
 
              // Process the results
            while(resultSet.next()) {
                int ChallengeNumber =resultSet.getInt("ChallengeNumber");
                String ChallengeName =resultSet.getString("ChallengeName");
                out.println("ChallengeNumber-" + ChallengeNumber+ "  ChallengeName- " + ChallengeName);

            }
            out.println("Enter the command (attemptChallenge challenge_no) to attempt the challenge");
            out.println("END");
        connection.close();
    }

    public static boolean isValidChallenge(String ChallengeNumber) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
        System.out.println("Connected to the database successfully!");

        String sql = "SELECT COUNT(*) FROM challenge WHERE ChallengeNumber = ? ";


        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, ChallengeNumber);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        

        return false;
    }

    public static void retrieveQuestion(String ChallengeNo, List<String> questionsList, List<String> solutionsList,List<Integer> questionNumbers) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT Questions.QuestionNo,Questions.Question_text, Solutions.solutions " +
                         "FROM Questions " +
                         "JOIN Solutions ON Questions.QuestionNo = Solutions.QuestionNo " +
                         "WHERE Questions.ChallengeNumber = ? " +
                         "ORDER BY RAND() LIMIT 10";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, ChallengeNo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int questionNo = rs.getInt("QuestionNo");
                String question = rs.getString("Question_text");
                String solution = rs.getString("solutions");

                questionNumbers.add(questionNo);
                questionsList.add(question);
                solutionsList.add(solution);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Optional: re-throw the exception if you want the caller to handle it
        }
    }

    public static void loadChallengeCountsFromDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
        System.out.println("Connected to the database successfully!");


        String sql = "SELECT ChallengeNumber, ChallengeCount FROM TotalMark";

        
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            String challengeNumber = rs.getString("ChallengeNumber");
            int challengeCount = rs.getInt("ChallengeCount");
            challengeCounts.put(challengeNumber, challengeCount);
            System.out.println("Loaded challenge " + challengeNumber + " with count " + challengeCount);
        }
        
    }

    public static void updateChallengeCountInDatabase(String challenge, int count) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
        System.out.println("Connected to the database successfully!");


        String sql = "UPDATE TotalMarks SET ChallengeCount = ? WHERE ChallengeNumber = ?";

    
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, count);
        stmt.setString(2, challenge);

        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Updated challenge count for " + challenge + " to " + count);
        } else {
            System.out.println("Challenge " + challenge + " not found in the database.");
        }
        
    }

    public static void resetChallengeCountsInDatabase() throws SQLException{
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
        System.out.println("Connected to the database successfully!");


        String sql = "UPDATE TotalMarks  SET ChallengeCount = 0";

        
        Statement stmt = connection.createStatement();

        stmt.executeUpdate(sql);
        System.out.println("Reset all challenge counts to 0");
        
    }
    
    public static void updateQuestionScore(String challengeNumber, int questionNo, int questionScore) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO QuestionScores (challengeNumber, questionNo, questionScore) VALUES (?, ?, ?) " +
                         "ON DUPLICATE KEY UPDATE questionScore = VALUES(questionScore)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, challengeNumber);
                
                pstmt.setInt(2, questionNo);
                pstmt.setInt(3, questionScore);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateMarks(String challengeNumber, int Marks) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Get current challenge count
           // int challengeCount = getChallengeCount(authenticatedUsername, challengeNumber);

            // Increment challenge count
           // challengeCount++;

            String sql = "INSERT INTO TotalMarks (challengeNumber, Marks) VALUES (?, ?) " +
                         "ON DUPLICATE KEY UPDATE marks = VALUES(marks)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, challengeNumber);
                
                
                pstmt.setInt(2, Marks);
                //pstmt.setInt(5, challengeCount);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String checkApplicantEmail(String username) throws SQLException {
        try {


        //Setting up the connection to the database
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
        System.out.println("Connected to the database successfully!");
        

        //Setting up my object to send and excute the sql statements
        //i will use preparedStatements to prevent sql injection

        String sql = "SELECT EmailAddress FROM participant WHERE Username = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();

            // Process the results
        rs.next();
        String email = rs.getString("EmailAddress");

        return email;


        
        } catch (SQLException e) {
        e.printStackTrace();

        return null;
        }

    }



    //  Fetching user information from the database using the username ast the parameter


    public static List<String> getUserInfo(String username) throws SQLException {
        try {
            List<String> userInfo = new ArrayList<>();


        //Setting up the connection to the database
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD); 
        System.out.println("Connected to the database successfully!");
        

        //Setting up my object to send and excute the sql statements
        //i will use preparedStatements to prevent sql injection

        String sql = "SELECT username,firstname,last FROM participant WHERE Username = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, username);
        ResultSet resultSet = pstmt.executeQuery();

            // Process the results
            if (resultSet.next()) {
                userInfo.add(resultSet.getString("username"));
                userInfo.add(resultSet.getString("firstname"));
                userInfo.add(resultSet.getString("lastname"));
            }
            return userInfo;
       


        
        } catch (SQLException e) {
        e.printStackTrace();
        return null;

        
        }
        

    }

    //this is to insert into the incomplete challenges table

    public static void updateInChallenge(String username, String firstName, String lastName) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
     
            String sql = "INSERT INTO incomplete_challenges (username, firstname, lastname) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, firstName);
                pstmt.setString(3, lastName);

                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


   
 

 

}
