import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Email {
    public static void notifyRep(String representativeEmail) {
        // Sender's email ID needs to be mentioned
        String from = "sseluyindaeva@gmail.com";
        final String username = System.getenv("EMAIL_USERNAME"); // your Gmail username
        final String password = System.getenv("EMAIL_PASSWORD"); // your Gmail password

        // Assuming you are sending email through Gmail's SMTP server
        String host = "smtp.gmail.com";

        // Set system properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Get the Session object
        Session session = Session.getInstance(properties,
           new javax.mail.Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                 return new PasswordAuthentication(username, password);
              }
           });

        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);

            // Set From: header field of the header
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header
            message.setRecipients(Message.RecipientType.TO,
               InternetAddress.parse(representativeEmail));

            // Set Subject: header field
            message.setSubject("New applicant to validate");

            // Now set the actual message
            message.setText("I hope you are doing well. I am sending this email to inform you that a new applicant has been submitted to validate. Please log into the system to validate the applicant.");

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
