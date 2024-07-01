import java.io.BufferedReader;
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
        }
        out.println("Enter your password:");
        String password = in.readLine();
        if (password.isEmpty()) {
            out.println("Password cannot be empty.");
        }
        // Add logic to check if the username is in the database
    }
}
