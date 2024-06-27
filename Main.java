import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
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
                System.out.println("Enter a username:");
                String username = sn.next();
                if (username.isEmpty()) {
                    System.out.println("Username cannot be empty.");
                    return;
                }

                System.out.println("Enter your Firstname:");
                String firstname = sn.next();
                if (firstname.isEmpty()) {
                    System.out.println("First name cannot be empty.");
                    return;
                }

                System.out.println("Enter your Last name:");
                String lastname = sn.next();
                if (lastname.isEmpty()) {
                    System.out.println("Last name cannot be empty.");
                    return;
                }

                System.out.println("Enter your Email Address:");
                String email = sn.next();
                if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                    System.out.println("Invalid email address.");
                    return;
                }

                System.out.println("Enter your date of Birth (yyyy-MM-dd):");
                String dateOfBirth = sn.next();

                System.out.println("Enter your school Registration Number:");
                String registrationNumber = sn.next();

                System.out.println("Enter the directory of the image:");
                String imagePath = sn.next();

                try {
                    Applicant applicant = new Applicant(username, firstname, lastname, email, dateOfBirth, registrationNumber, imagePath);
                    System.out.println("Application request submitted successfully.\nPlease wait for the application to be reviewed and you will recieve an email.");
                    // Add logic to save the applicant to the database
                } catch (IOException e) {
                    System.out.println("Failed to save image as BLOB.");
                    e.printStackTrace();
                } catch (DateTimeParseException e) {
                    System.out.println("Failed to parse date of birth.");
                    e.printStackTrace();
                }
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
