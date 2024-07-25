import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class PDFEmailSender {

    public static void sendEmailWithAttachment(String to, String subject, String bodyText, String filePath) {
        // Sender's email ID
        String from = "sseluyindaeva@gmail.com";
        final String username = "sseluyindaeva@gmail.com"; // your Gmail username
        final String password = "zuec mgbk lrkk rmih"; // your Gmail app-specific password

        // SMTP server configuration
        String host = "smtp.gmail.com";

        // Set up properties for the mail session
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587"); // Common port for TLS
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.ssl.trust", "*");
        properties.put("mail.smtp.ssl.enable", "true");

        // Using port 465 with SSL directly
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
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
            message.setSubject(subject);

            // Create a multipart message for attachment
            Multipart multipart = new MimeMultipart();

            // Create the message body part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(bodyText);
            multipart.addBodyPart(messageBodyPart);

            // Part two is the attachment
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filePath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filePath);
            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            // Send the message
            Transport.send(message);
            System.out.println("Email sent successfully with attachment...");

            // Delete the file after sending the email
            File file = new File(filePath);
            if (file.delete()) {
                System.out.println("File deleted successfully.");
            } else {
                System.out.println("Failed to delete the file.");
            }

        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }


    

}
