/**
 * Provides functionality for reading input from a buffered stream.
 */
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MainClient {
    /**
     * The main entry point for the client application. Handles the user interaction and communication with the server.
     * 
     * The client first prompts the user to identify as either an applicant or a school representative. Based on the user's
     * response, the client then handles the appropriate registration or login flow, calling the corresponding helper methods.
     * 
     * For applicants, the client handles the registration process, including prompting the user for input and relaying it to
     * the server. For school representatives, the client handles the login process, prompting the user for their system
     * username and relaying it to the server.
     * 
     * The client also handles the responses from the server, printing them to the console and taking appropriate actions
     * based on the responses.
     * @throws SQLException 
     */
    public static void main(String[] args) throws SQLException {
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
                String auth = in.readLine();
                if ("Welcome To Thrive Math Competition".equals(auth)) {
                    System.out.println(auth+"\nEnter the viewApplicant command to view the applicants");
                    //Add logic to view the challenges and everything
                    out.println(sn.nextLine());
                    StringBuilder receivedData = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        if (line.equals("END")) {
                            break;
                        }
                        receivedData.append(line).append("\n");
                    }

                    if ("Invalid command".equals(receivedData.toString())) {
                        System.out.println(receivedData.toString());
                    } else {
                        System.out.println(receivedData.toString());
                        String confirmationResponse = "";
                        do{
                        System.out.println("To confirm any applicant,enter the command (confirm yes/no username) or enter Exit to exit the program");
                        String command=sn.nextLine();
                        out.println(command); 
                        confirmationResponse =in.readLine();
                        System.out.println(confirmationResponse);
                        }while(!confirmationResponse.equals("Exit"));
                        System.out.println("Thank you for using the Thrive Math Competition");


                    }
                } else {
                    System.out.println("Invalid username or password\nTry again");
                }
            } else {
                System.out.println("Unexpected response from server: " + response);
            }
        } catch (IOException ex) {
            System.out.println("Client exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static String handleApplicantRegistration(BufferedReader in, PrintWriter out, Scanner sn) throws IOException, SQLException {
        System.out.println("Are you already registered? Enter 1 for yes or 0 for no");
        String isRegistered = sn.nextLine();
        out.println(isRegistered);

        if ("1".equals(isRegistered)) {
            System.out.println(in.readLine());
            String username = sn.nextLine();
            out.println(username);

            System.out.println(in.readLine()); // Expecting "Enter the School registration number"
            out.println(sn.nextLine());

            String auth_response = in.readLine(); // Final response from server
            if ("1".equals(auth_response)) {
                System.out.println("Welcome to the Thrive Mathematics challenge");
                //Add logic to view the challenges and everything
                System.out.println("Enter the command (viewChallenges) to view the valid Challenges");
                out.println(sn.nextLine());
                while (true) {
                    String response = in.readLine();
                    if ("END".equals(response)) {
                        break;
                    }
                    System.out.println(response);
                    
                     
                }
                out.println(sn.nextLine());
                


                int TotalScore = 0;
                long totalTime = 300000; // Total time for all questions (e.g., 60000 ms = 60 seconds)
                int numberOfQuestions = 10; // Adjust this if you change the limit in the SQL query
                long[] responseTimes = new long[numberOfQuestions];

                String ChallengeNumber = in.readLine();

                if(ChallengeNumber.startsWith("Invalid")){
                    System.out.println(ChallengeNumber);
                    return null;
                }

                List<String> questionsList = new ArrayList<>();
                List<String> solutionsList = new ArrayList<>();
                List<Integer> questionNumbers = new ArrayList<>();
                DatabaseConnection.retrieveQuestion(ChallengeNumber, questionsList, solutionsList,questionNumbers);


                DisplayTiming timerThread = new DisplayTiming();
        timerThread.startTimer(totalTime);

        Scanner scanner = new Scanner(System.in);
        long challengeStartTime = System.currentTimeMillis();


        for (int i = 0; i < questionsList.size(); i++) {
            if (timerThread.getRemainingTime() <= 0) {
                System.out.println("\nTime is up!");
                break;
            }

            int questionNo = questionNumbers.get(i);
            String question = questionsList.get(i);
            String solution = solutionsList.get(i);

            System.out.println("\n\nRemaining Questions " + (numberOfQuestions - (i + 1)));
            System.out.println("Question " + (i + 1) + ": " + question);

            long startTime = System.currentTimeMillis();

            String userAnswer = scanner.nextLine();

            long endTime = System.currentTimeMillis();
            responseTimes[i] = endTime - startTime;

            int questionScore = markAnswer(userAnswer, solution, TotalScore);
            TotalScore += questionScore;

            DatabaseConnection.updateQuestionScore(ChallengeNumber, questionNo, questionScore);
            
        }

        timerThread.stopTimer();
        long challengeEndTime = System.currentTimeMillis();
        long totalTimeTaken = challengeEndTime - challengeStartTime;
        System.out.println("\nFinal Score: " + TotalScore);
        CreatePDF.reportpdf(responseTimes, questionsList, solutionsList, TotalScore, totalTimeTaken);
        
    

       
        DatabaseConnection.updateMarks(ChallengeNumber, TotalScore);





                return null;
                
            } else if ("0".equals(auth_response)) {
                System.out.println("Invalid username or password\nTry again");
                return null;
            } else {
                System.out.println(auth_response); 
                return null;
            }
            

        } else if ("0".equals(isRegistered)) {
            System.out.println(in.readLine());
            System.out.println(in.readLine());
            System.out.println(in.readLine()); // Registration prompt from server
            out.println(sn.nextLine()); // User's registration details

            String registrationResponse;
            while ((registrationResponse = in.readLine()) != null) {
                System.out.println(registrationResponse);
                if (registrationResponse.startsWith("Applicant registered successfully")) {
                    System.out.println("You have registered successfully.\nPlease wait to be confirmed by your school representative\nThank you :)");
                    return null;
                }else{
                    System.out.println(registrationResponse);
                    System.out.println("Please try again:");
                    return null;
                    //we have to make it iterative so the user can try to enter the command again

                }
            }
        } else {
            System.out.println("Invalid input. Please try again.");
            return null;
        }
        return null;

    }
    private static void handleSchoolRepresentativeLogin(BufferedReader in, PrintWriter out, Scanner sn) throws IOException {
        System.out.println("Enter your system username:");
        out.println(sn.nextLine());
    
        System.out.println(in.readLine()); // Expecting "Enter the password"
        String password = getPasswordFromConsole();
        if (password != null) {
            out.println(password);
        } else {
            System.out.println("Unable to read the password.");
        }
    }
    
    public static String getPasswordFromConsole() {
        Console console = System.console();
        if (console == null) {
            System.out.println("No console available");
            return null;
        }
    
        // Read password without printing another prompt
        char[] passwordArray = console.readPassword();
        return new String(passwordArray);
    }
    public static int markAnswer(String userAnswer, String solution, int marks) {

        if (userAnswer.equals(solution)) {
            marks += 10;
        } else if (userAnswer.isEmpty()) {
            marks = marks;
        } else {
            marks -= 3;
        }
        return marks;
    }

}


