import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Applicant {
    public String username;
    public String firstName;
    public String lastName;
    public String emailAddress;
    public LocalDate dateOfBirth;
    public String schoolRegistrationNumber;
    public byte[] image;

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

  

}
