
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class Challenge {
    public static void attemptChallenge(BufferedReader in,PrintWriter out)throws IOException,SQLException{

        String command = in.readLine();
        if (command != null && command.startsWith("attemptChallenge")) {
            String[] parts = command.split(" ", 2);

            if (parts.length == 2) {
                String ChallengeNumber = parts[1].trim();

                if (!DatabaseConnection.isValidChallenge(ChallengeNumber)) {
                    out.println("Invalid challenge number. Terminating connection.");
                }else{
                    out.println(ChallengeNumber);
                }



            }else{
                out.println("Invalid command format. Terminating connection.");
            }

        }else{
            out.println("Invalid command. Terminating connection."); 
        }
  
        
    }

    

}
