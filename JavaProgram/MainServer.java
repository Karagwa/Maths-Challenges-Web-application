/**
 * Imports the necessary classes from the Java I/O package to handle input and output operations.
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.sql.SQLException;


public class MainServer {
    public static void main(String[] args) throws SQLException{

        try (ServerSocket serverSocket = new ServerSocket(2020)) {
            System.out.println("Server is listening on port 2020");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                    String inputLine;
                    inputLine = in.readLine();
                    if (inputLine != null) {
                        switch (inputLine) {
                            case "1": // Applicant
                                handleApplicant(in, out);
                                break;
                            case "2": // School Representative
                                SchoolRepresentative.handleSchoolRepresentative(in, out);

                                break;
                            default:
                                out.println("Invalid input. Please try again.");
                                break;
                        }
                    }else{
                        out.println("Invalid input");
                    }
                } catch (IOException e) {
                    System.out.println("Exception caught when trying to listen on port 2020 or listening for a connection");
                    System.out.println(e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the logic for an applicant interacting with the server.
     * Prompts the applicant to indicate if they are already registered, and then
     * either proceeds with login logic or new applicant registration depending on
     * the user's response.
     *
     * @param in The BufferedReader to read input from the applicant.
     * @param out The PrintWriter to write output to the applicant.
     * @throws IOException If there is an error reading or writing to the socket.
     * @throws SQLException 
     */
    private static void handleApplicant(BufferedReader in, PrintWriter out) throws IOException, SQLException {
        out.println("Are you already registered? Enter 1 for yes or 0 for no");
        String isRegistered = in.readLine();

        if ("1".equals(isRegistered)) {
            out.println("Enter your username");
            String username = in.readLine();
            // Add logic to check if the username is in the database

            out.println("Enter the School registration number");
            String registrationNumber = in.readLine();
            // Add logic to check for corresponding school registration number
            // If the details correspond, proceed with further logic
            DatabaseConnection.authenticateApplicant(username, registrationNumber, in, out);
            String ChallengeCommand = in.readLine();
            if ("viewChallenges".equals(ChallengeCommand)) {
                DatabaseConnection.getChallenges(in, out);
                Challenge.attemptChallenge(in, out);

                
            } else {
                out.println("Invalid Command. Please try again.");
                out.println("END");
            }

        } else if ("0".equals(isRegistered)) {
            Applicant.newApplicant(in,out);
        } else {
            out.println("Invalid input. Please try again.");
        }
    }

    
}
