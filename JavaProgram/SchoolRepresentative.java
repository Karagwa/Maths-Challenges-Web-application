import java.io.BufferedReader;

import java.io.FileReader;

import java.io.IOException;
import java.io.PrintWriter;

public class SchoolRepresentative {
    String username;
    String password;


    public static void handleSchoolRepresentative(BufferedReader in, PrintWriter out) throws IOException {
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
                String applicantData = viewApplicant("Temp.csv",in,out);
                out.println(applicantData);       
            }else{
                out.println("Invalid command");
            }
            
            
        } else {
            out.println("Invalid username or password \nPlease try again");
        }
        // Add logic to check if the username is in the database
    }

    public static String viewApplicant(String CSV_FILE_PATH,BufferedReader in,PrintWriter out) {
            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Split CSV line into parts
                    String[] parts = line.split(",");
                    if (parts.length >= 6) { // Ensure there are enough fields
                        String firstName = parts[1].trim();
                        String lastName = parts[2].trim();
                        String regNumber = parts[5].trim(); // Assuming school registration number is in the 6th column
                        sb.append(firstName).append(" ").append(lastName).append(" - Registration Number: ").append(regNumber).append("\n");
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading pupils data: " + e.getMessage());
            }
            return sb.toString();
        }

}
