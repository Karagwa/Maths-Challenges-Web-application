import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;
import java.util.Scanner;

public class ChallengeClient {
    public static void main(String[] args) {
        try (
            Socket socket = new Socket("localhost", 12357);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in)
        ) {

            System.out.println("Enter command - attemptChallenge ChallengeNumber ");
            String command = scanner.nextLine();
            

            // Validate input format
            if (command.matches("attemptChallenge \\d+ \\w+")) {
                out.println(command);    

                String response;
                while ((response = in.readLine()) != null) {
                    System.out.println(response);
                    if (response.startsWith("Question: ")) {
                        System.out.print("Your answer: ");
                        String answer = scanner.nextLine();
                        out.println(answer);
                    }
                }
            } else {
                System.out.println("Invalid command format. Terminating connection.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}