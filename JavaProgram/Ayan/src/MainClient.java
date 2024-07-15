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
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter challenge number: ");
            int challengeNo = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Request questions from server
            out.println("GET_QUESTIONS");
            out.println(challengeNo);

            // Read and display questions
            String question;
            while (!(question = in.readLine()).equals("END_OF_QUESTIONS")) {
                if (question.startsWith("Error:")) {
                    System.out.println(question);
                    return;
                }
                System.out.println(question);

                // Get answer from user
                System.out.print("Enter your answer: ");
                String userAnswer = scanner.nextLine();

                // Send answer to server for marking
                int questionNo = Integer.parseInt(question.split(":")[0].trim());
                out.println("SUBMIT_ANSWER");
                out.println(challengeNo);
                out.println(questionNo);
                out.println(userAnswer);

                // Read response from server
                String response = in.readLine();
                System.out.println("Marks awarded: " + response);
            }

        } catch (IOException ex) {
            System.out.println("Client exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}







