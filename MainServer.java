import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class MainServer {
    public static void main(String[] args) {
        Scanner sn = new Scanner(System.in);
        System.out.println("Are you an applicant or a school representative?\nEnter 1 if you are an applicant or Enter 2 if you are a School Representative");
        int userChoice = sn.nextInt();

        if (userChoice == 1) {
            System.out.println("Are you already registered?\nEnter 1 for yes or 0 for no");
            int isRegistered = sn.nextInt();
            if (isRegistered == 1) {
                System.out.println("Enter your username");
                String username = sn.next();
                // Add logic to check if the username is in the database

                System.out.println("Enter the School registration number");
                String registrationNumber = sn.next();
                // Add logic to check for corresponding school registration number
                // If the details correspond, proceed with further logic

            } else if (isRegistered == 0) {
                Applicant.newApplicant();
            }else{
                System.out.println("Invalid input. Please try again.");
                return;
            }
        }else if (userChoice == 2) {
            System.out.println("Enter your username:");
            String username = sn.next();
            if (username.isEmpty()) {
                System.out.println("Username cannot be empty.");
                return;
            }
            System.out.println("Enter your password:");
            String password = sn.next();
            if (password.isEmpty()) {
                System.out.println("Password cannot be empty.");
                return;
            }
            // Add logic to check if the username is in the database


        }else{
            System.out.println("Invalid input. Please try again.");
            return;
        }
    }
    

}
