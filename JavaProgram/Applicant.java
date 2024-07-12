import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Applicant {
    public String username;
    public String firstName;
    public String lastName;
    public String emailAddress;
    public LocalDate dateOfBirth;
    public String schoolRegistrationNumber;
    public byte[] image;

    /**
     * Writes the applicant's information to a file named "Vault.txt". If the file does not exist, it will be created.
     * The file will contain the following information on separate lines:
     * - Username
     * - First name
     * - Last name
     * - Email address
     * - Date of birth
     * - School registration number
     * - Image data (as a byte array)
     *
     * @param applicant The Applicant object containing the information to be written to the file.
     * @throws IOException If there is an error creating or writing to the file.
     */
    public static void fileHandler(Applicant applicant) throws IOException {
        File file = new File("Temp.csv");
        
        if(file.createNewFile()){
            System.out.println("Temp file is created");
            FileWriter headerWriter = new FileWriter("Temp.csv",true);
            headerWriter.write("Username, First Name, Last Name, Email Address, Date of Birth, School Registration Number, Image\n");
            headerWriter.close();
        }else{
            System.out.println("Temp file already exists");
        }
        FileWriter writer = new FileWriter("Temp.csv",true);//true indicates append mode
        writer.write(applicant.username + ", " + applicant.firstName + ", " + applicant.lastName + ", " + applicant.emailAddress + ", " + applicant.dateOfBirth.toString() + ", " + applicant.schoolRegistrationNumber + ", " + applicant.image + "\n");
        writer.close();

        String repEmail = DatabaseConnection.checkRepresentativeEmail(applicant.schoolRegistrationNumber);

        if (repEmail!= null) {
            Email.notifyRep(repEmail);
        }else{
            System.out.println("No Representative Found");
        }
    }



    public byte[] getImageAsBlob(String imagePath) throws IOException {
        // Log the path being used
        System.out.println("Reading image from: " + imagePath);

        try (InputStream imageStream = new FileInputStream(imagePath)) {
            return imageStream.readAllBytes();
        }
    }


    Applicant (String username, String firstName,String lastName,String email,String DOB,String RegNo,String image) throws IOException{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.username=username;
        this.firstName= firstName;
        this.lastName =lastName;
        this.emailAddress = email;
        this.dateOfBirth= LocalDate.parse(DOB, formatter);
        this.schoolRegistrationNumber = RegNo;
        this.image = getImageAsBlob(image);
    }

    
    //This is the method used to create object of an applicant to store the applicant detail into the file
    public static void newApplicant(BufferedReader in, PrintWriter out) throws IOException{

        out.println("Welcome To Thrive Math Competition");
        out.println("To Register, enter the following command, and mind to skip a space where indicated:");
        out.println("Register: username\t firstname\t lastname\t emailAddress\t date_of_birth(yyyy-mm-dd)\t school_registration\t location_of_image");
      // Read the response from the client
        String response = in.readLine();

        if (response != null && response.startsWith("Register:")) {
            // Remove the "Register:" part and trim leading/trailing whitespace
            String trimmedResponse = response.substring(9).trim();
            // Split the trimmed response by one or more spaces since i am a forgiving person
            String[] responses = trimmedResponse.split("\\s+");
            //i can also use [ \t\n\x0B\f\r]+ as for \\s+
            //' ' is for a space,\t is for tab,\n for newline,\x0b for verticaltab,
            // \r for return character and \f is for formfeed character and + is for one or more

            // Ensure there are exactly 7 fields
            if (responses.length == 7) {
                String username = responses[0];
                String firstname = responses[1];
                String lastname = responses[2];
                String email = responses[3];
                String dateOfBirth = responses[4];
                String registrationNumber = responses[5];
                String imagePath = responses[6];

                try {
                    Applicant applicant = new Applicant(username, firstname, lastname, email, dateOfBirth, registrationNumber, imagePath);
                    out.println("Applicant registered successfully\nPlease wait to be verified by your School Representative\nYou will recieve an email when verified\nThank you\n:)");
                    Applicant.fileHandler(applicant);
                    // Add logic to save the applicant to the database
                } catch (IOException e) {
                    out.println("Failed to save image as BLOB.");
                    e.printStackTrace();
                    
                } catch (DateTimeParseException e) {
                    out.println("Failed to parse date of birth. Please try again and write a correct date");
                    e.printStackTrace();
                }
                
            } else {
                out.println("Invalid command format. Please ensure you provide all required fields.");
            }
        } else {
            out.println("Invalid command please Enter the Command Correctly \"Register:\"");
        }


    }

  

}
