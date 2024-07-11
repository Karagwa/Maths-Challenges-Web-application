/**
 * Represents a connection to a database.
 * This class provides methods for interacting with a database, such as authenticating users.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.io.PrintWriter;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Thrive";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "yourpassword";



public static String authenticateRepresentative(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to the database successfully!");

            String sql = "SELECT * FROM SchoolRepresentative WHERE username = ? AND password = ?";
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


    public static void getChallenges()throws SQLException,IOException{
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
                System.out.println("ChallengeNumber-" + ChallengeNumber+ "  ChallengeName- " + ChallengeName);
                
            }
        connection.close();
    }
 

}
