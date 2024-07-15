import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainServer {

    public static void main(String[] args) {
        new MainServer().startServer();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(2020)) {
            System.out.println("Server is listening on port 2020");

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> handleClient(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String command = in.readLine();
            if (command == null) {
                System.out.println("Received null command");
                return; // Handle null command gracefully
            }
            System.out.println("Received command: " + command);

            DatabaseConnection connection = new DatabaseConnection();

            if ("GET_QUESTIONS".equals(command)) {
                int challengeNo = Integer.parseInt(in.readLine());
                try {
                    ResultSet questions = connection.getQuestions(challengeNo);
                    while (questions.next()) {
                        int questionNo = questions.getInt("questionNo");
                        String questionText = questions.getString("questions");
                        out.println(questionNo + ": " + questionText);
                    }
                    out.println("END_OF_QUESTIONS");
                } catch (SQLException e) {
                    out.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }
            } else if ("SUBMIT_ANSWER".equals(command)) {
                int challengeNo = Integer.parseInt(in.readLine());
                int questionNo = Integer.parseInt(in.readLine());
                String userAnswer = in.readLine();
                try {
                    String correctAnswer = connection.getSolution(questionNo);
                    int marks = 0;
                    if (userAnswer.trim().isEmpty() || Integer.parseInt(userAnswer) < 0) {
                        marks = 0;
                    } else if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                        marks = 10;
                    } else {
                        marks = -3;
                    }
                    connection.updateMarks(challengeNo, questionNo, marks);
                    out.println(marks);
                } catch (SQLException e) {
                    out.println("Error: " + e.getMessage());
                    e.printStackTrace();
                } catch (NumberFormatException e) {
                    out.println("Error: Invalid number format.");
                    e.printStackTrace();
                }
            } else {
                out.println("Unknown command.");
            }
        } catch (IOException e) {
            System.err.println("Error communicating with client: " + e.getMessage());
            e.printStackTrace(); // Log the error for debugging
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}




