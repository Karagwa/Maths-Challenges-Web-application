/**
 * Provides a buffered reader for reading input from a stream.
 */
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
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            Scanner sn = new Scanner(System.in);
            System.out.println("Are you an applicant or a school representative?\nEnter 1 if you are an applicant or Enter 2 if you are a School Representative");
            String userType = sn.nextLine();
            out.println(userType);




            // Read response from server
            String response = in.readLine();
            
            if ("Are you already registered? Enter 1 for yes or 0 for no".equals(response)){
                System.out.println(response);
                String isRegistered = sn.nextLine();
                out.println(isRegistered);

                String response1 = in.readLine();
                if("Enter your username".equals(response1)){
                    System.out.println(response1);
                    String username = sn.nextLine();
                    out.println(username);

                    System.out.println(in.readLine());
                    out.println(sn.nextLine());

                    //wait for response of registration

                }else if(response.startsWith("Welcome To Thrive Math Competition")){
                    System.out.println(response);
                    out.println(in.readLine());

                    System.out.println(in.readLine());
                    return;

                    //this the response to a new applicant. after the program stops



                }

            }else if ("Enter your system username:".equals(response)){
                System.out.println(response);
                out.println(sn.nextLine());
                System.out.println(in.readLine());
                out.println(sn.nextLine());

                System.out.println(in.readLine());
                return;
                //login the school representative into the system
            }else{
                System.out.println(in.readLine());
                return;
                
            }
            sn.close();

        } catch (IOException ex) {
            System.out.println("Client exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        
    }
}

