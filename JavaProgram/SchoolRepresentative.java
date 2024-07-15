import java.io.BufferedReader;

import java.io.FileReader;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.SQLException;


public class SchoolRepresentative {
    String username;
    String password;


    public static void handleSchoolRepresentative(BufferedReader in, PrintWriter out) throws IOException, SQLException {
        out.println("Enter your system username:");
        String username = in.readLine();
        if (username.isEmpty()) {
            out.println("Username cannot be empty.");
            return;
        }
        out.println("Enter your password:");
        String password = in.readLine();
        if (password.isEmpty()) {
            out.println("Password cannot be empty.");
            return;
        }


        String databaseresponse=DatabaseConnection.authenticateRepresentative(username, password);
        if ("1".equals(databaseresponse)) {
            out.println("Welcome To Thrive Math Competition");
    
            String repcommand= in.readLine();
            if ("viewApplicant".equals(repcommand)) {
                String applicantData = viewApplicant("Temp.csv");
                out.println(applicantData);
                out.println("END");    
                String confirm_command = in.readLine();   
                while (!"Exit".equals(confirm_command)){
                    
                    confirmApplicant(confirm_command, in, out);
                    confirm_command = in.readLine();
                }
                out.println("Exit");
                
            }else{
                out.println("Invalid command");
                out.println("END");
            }
            
            
        } else {
            out.println("Invalid username or password \nPlease try again");
        }
        // Add logic to check if the username is in the database
    }

    public static String viewApplicant(String CSV_FILE_PATH) {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
                String line;
                
                while ((line = reader.readLine()) != null) {
                    if (line.isEmpty()){
                        continue;
                    }
                    // Split CSV line into parts
                    String[] parts = line.split(",");
                    if (parts.length == 7) { // Ensure there are enough fields

                        String userName=parts[0].trim();
                        String firstName = parts[1].trim();
                        String lastName = parts[2].trim();
                        String regNumber = parts[5].trim(); // Assuming school registration number is in the 6th column
                        sb.append(firstName).append(" ").append(lastName).append("\t\t").append(" - Registration Number: ").append(regNumber).append("\t\t").append(" -Username: ").append(userName).append("\n");

                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading pupils data: " + e.getMessage());
            }
            
            return sb.toString();
        }


      

    public static void confirmApplicant(String command,BufferedReader in,PrintWriter out) throws  IOException,SQLException{
        //String command = in.readLine();
        String[] parts =  command.split(" ");

        if (parts.length == 3 && parts[0].equals("confirm")) {
            String action = parts[1];
            String username = parts[2];

            if (action.equals("yes")) {
                DatabaseConnection.addParticipant("Temp.csv",username);
                out.println("User "+username+" confirmed and added to participants");

                
            }else if(action.equals("no")){
                DatabaseConnection.addRejected("Temp.csv",username);
                out.println("Username "+ username+" rejected and added to rejected table ");

            }else{
                out.println("Invalid action. Use 'yes' or 'no'");
            }

            
        } else {
            out.println("Invalid command format. Use 'confirm yes/no username' .");
            
        }
    }


}
