import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class pdf {
    public static void main(String[] args) {
        // Recipient's email ID
        String to = "sseluyindaeva@gmail.com";


        // SMTP server configuration
        String from = "sseluyindaeva@gmail.com";
        final String username = "sseluyindaeva@gmail.com"; // your Gmail username
        final String password = "zuec mgbk lrkk rmih"; // your Gmail password

        String host = "smtp.gmail.com";

        // Set up properties for the mail session
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587"); // Common port for TLS

        // Get the Session object
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set From: header field
            message.setFrom(new InternetAddress(from));

            // Set To: header field
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Here is your PDF file");

            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();

            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Please find the attached PDF file.");
            multipart.addBodyPart(messageBodyPart);

            // Part two is the attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "path/to/yourfile.pdf";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send the message
            Transport.send(message);
            System.out.println("Email sent successfully with attachment...");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}

