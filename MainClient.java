import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MainClient {
    public static void main(String[] args) {
        String hostname = "localhost";
        int port = 2020;

        try (Socket socket = new Socket(hostname, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner sn = new Scanner(System.in)) {

            System.out.println("Are you an applicant or a school representative?");
            System.out.println("Enter 1 if you are an applicant or Enter 2 if you are a School Representative");
            String userType = sn.nextLine();
            out.println(userType);

            // Read response from server
            String response = in.readLine();
            if ("Are you already registered? Enter 1 for yes or 0 for no".equals(response)) {
                handleApplicantRegistration(in, out, sn);
            } else if ("Enter your system username:".equals(response)) {
                handleSchoolRepresentativeLogin(in, out, sn);
            } else {
                System.out.println("Unexpected response from server: " + response);
            }
        } catch (IOException ex) {
            System.out.println("Client exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static void handleApplicantRegistration(BufferedReader in, PrintWriter out, Scanner sn) throws IOException {
        System.out.println("Are you already registered? Enter 1 for yes or 0 for no");
        String isRegistered = sn.nextLine();
        out.println(isRegistered);

        if ("1".equals(isRegistered)) {
            System.out.println("Enter your username:");
            String username = sn.nextLine();
            out.println(username);

            System.out.println(in.readLine()); // Expecting "Enter the School registration number"
            out.println(sn.nextLine());

            System.out.println(in.readLine()); // Final response from server

        } else if ("0".equals(isRegistered)) {
            System.out.println(in.readLine());
            System.out.println(in.readLine());
            System.out.println(in.readLine()); // Registration prompt from server
            out.println(sn.nextLine()); // User's registration details

            String registrationResponse;
            while ((registrationResponse = in.readLine()) != null) {
                System.out.println(registrationResponse);
                if (registrationResponse.startsWith("Applicant registered successfully")) {
                    System.out.println("You have registered successfully.\nPlease to be confirmed by your school representative\nThank you :)");
                    break;
                }else{
                    System.out.println(registrationResponse);
                    System.out.println("Please try again:");
                    break;
                    //we have to make it iterative so the user can try to enter the command again

                }
            }
        } else {
            System.out.println("Invalid input. Please try again.");
        }
    }

    private static void handleSchoolRepresentativeLogin(BufferedReader in, PrintWriter out, Scanner sn) throws IOException {
        System.out.println("Enter your system username:");
        out.println(sn.nextLine());

        System.out.println(in.readLine()); // Prompt for password
        out.println(sn.nextLine());

        System.out.println(in.readLine()); // Final response from server
    }
}
