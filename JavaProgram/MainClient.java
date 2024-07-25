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
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


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
            String regno = sn.nextLine();
            out.println(regno);

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
                out.println(sn.nextLine());//this is attemptChallenge + challengeNumber response
                String ChallengeNumber = in.readLine();
            
                if (ChallengeNumber.startsWith("Invalid")) {
                    System.out.println(ChallengeNumber);
                    return null;
                }if(DatabaseConnection.hasAttemptedChallenge(username, regno, ChallengeNumber)){
                    System.out.println("You have already attempted this challenge. Terminating connection");
                    return null;

                }else{
                    int attemptCount = 0;
                int maxAttempts = 3;
                List<Set<String>> attemptQuestions = new ArrayList<>();
                Scanner scanner = new Scanner(System.in);

                while (attemptCount < maxAttempts) {
                    attemptCount++;
                    Set<String> currentAttemptQuestions = new HashSet<>();

                    int TotalScore = 0;
                    long totalTime = 60000; // Total time for all questions (e.g., 60000 ms = 60 seconds)
                    int numberOfQuestions = 10; // Adjust this if you change the limit in the SQL query
                    long[] responseTimes = new long[numberOfQuestions];

                    List<String> questionsList = new ArrayList<>();
                    List<String> solutionsList = new ArrayList<>();
                    List<Integer> questionNumbers = new ArrayList<>();
                    List<String> userAnswers = new ArrayList<>();
                    DatabaseConnection.retrieveQuestion(ChallengeNumber, questionsList, solutionsList, questionNumbers);

                    DisplayTiming timerThread = new DisplayTiming();
                    timerThread.startTimer(totalTime);

                    long challengeStartTime = System.currentTimeMillis();

                    for (int i = 0; i < questionsList.size(); i++) {
                        if (timerThread.getRemainingTime() <= 0) {
                            System.out.println("\nTime is up!");
                            List<String> userInfo = DatabaseConnection.getUserInfo(username);
                            String Username = userInfo.get(0);
                            String firstName = userInfo.get(1);
                            String lastName = userInfo.get(2);
                            DatabaseConnection.updateInChallenge(Username, firstName, lastName, ChallengeNumber);
                            break;
                        }

                        int questionNo = questionNumbers.get(i);
                        String question = questionsList.get(i);
                        String solution = solutionsList.get(i);

                        currentAttemptQuestions.add(question);

                        System.out.println("\n\nRemaining Questions " + (numberOfQuestions - (i + 1)));
                        System.out.println("Question " + (i + 1) + ": " + question);

                        long startTime = System.currentTimeMillis();

                        String userAnswer = scanner.nextLine();
                        userAnswers.add(userAnswer);

                        long endTime = System.currentTimeMillis();
                        responseTimes[i] = endTime - startTime;
                        int previousScore = TotalScore;
                        TotalScore = markAnswer(userAnswer, solution, TotalScore);
                        int questionScore = TotalScore - previousScore;

                        DatabaseConnection.updateQuestionScore(ChallengeNumber, questionNo, questionScore);
                        String registrationNumber = DatabaseConnection.checkregno(username);
                        DatabaseConnection.updateMarks(username, registrationNumber, ChallengeNumber, TotalScore);
                    }

                    timerThread.stopTimer();

                    long challengeEndTime = System.currentTimeMillis();
                    long totalTimeTaken = challengeEndTime - challengeStartTime;
                    System.out.println("\nFinal Score: " + TotalScore);
                    String endTime = DatabaseConnection.checkEndDate(ChallengeNumber);
                    CreatePDF.reportpdf(responseTimes, questionsList, solutionsList, TotalScore, totalTimeTaken, username, userAnswers, endTime);

                    attemptQuestions.add(currentAttemptQuestions);

                    if (attemptCount >= maxAttempts) {
                        System.out.println("You have reached the maximum number of attempts.");
                        break;
                    }

                    System.out.println("Do you want to attempt the challenge again? (yes/no)");
                    String userResponse = scanner.nextLine();
                    if (userResponse.equalsIgnoreCase("no")) {
                        break;
                    }
                    System.out.println("\n\n\nNew Attempt\n\n");
                }

                calculateRecurringQuestions(attemptQuestions,ChallengeNumber);
                        return null;
                }


                
                
            } else if ("2".equals(auth_response)) {
                System.out.println("Sadly,you were rejected :(");
                return null;
            } else if("0".equals(auth_response)) {
                System.out.println("Invalid username or password\nTry again");
                return null;

                
            }else{
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
                //System.out.println(registrationResponse);
                if (registrationResponse.startsWith("Applicant registered successfully")) {
                    System.out.println("You have registered successfully.\nPlease wait to be confirmed by your school representative\nThank you :)");
                    return null;
                    
                }else if(registrationResponse.equals("You have been rejected before  and cannot register again with this same school.")){
                    System.out.println(registrationResponse);
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
    private static void calculateRecurringQuestions(List<Set<String>> attemptQuestions, String ChallengeNumber) throws IOException, SQLException {
        if (attemptQuestions.size() < 2) {
            System.out.println("Not enough attempts to calculate recurring questions.");
            return;
        }

        Set<String> recurringQuestions = new HashSet<>(attemptQuestions.get(0));
        for (int i = 1; i < attemptQuestions.size(); i++) {
            recurringQuestions.retainAll(attemptQuestions.get(i));
        }
        int totalRecurringQuestions = recurringQuestions.size();

        double percentageRecurring = (double) totalRecurringQuestions / 10 * 100;
        DatabaseConnection.updateRecuring(percentageRecurring, ChallengeNumber);

    }

}


