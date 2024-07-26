import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class CreatePDF {
    public static void reportpdf(long[] responseTime, List<String> questionsList, List<String> solutionList, int score, long totalTime, String username, List<String> userAnswers, String date) throws SQLException {
        // Step 1: Create a Document instance
        Document document = new Document();
    
        try {
            // Step 2: Create a PdfWriter instance
            PdfWriter.getInstance(document, new FileOutputStream("ChallengeReports/ChallengeReport1.pdf"));
    
            // Step 3: Open the document
            document.open();
    
            // Step 4: Add the challenge report details
            document.add(new Paragraph("Challenge Report"));
            document.add(new Paragraph(" "));
    
            document.add(new Paragraph("Score: " + score));
            document.add(new Paragraph("Total Time: " + totalTime / 1000 + " seconds"));
            document.add(new Paragraph(" "));
    
            for (int i = 0; i < questionsList.size(); i++) {
                if (solutionList.get(i).equals(userAnswers.get(i))) {
                    document.add(new Paragraph("Question " + (i + 1) + ": " + questionsList.get(i)));
                    document.add(new Paragraph("You got the correct answer"));
                    document.add(new Paragraph("Your Answer: " + userAnswers.get(i)));
                    document.add(new Paragraph("Response Time: " + responseTime[i] / 1000 + " seconds"));
                    document.add(new Paragraph(" "));
                } else {
                    document.add(new Paragraph("Question " + (i + 1) + ": " + questionsList.get(i)));
                    document.add(new Paragraph("You failed this one:"));
                    document.add(new Paragraph("Your Answer: " + userAnswers.get(i)));
                    document.add(new Paragraph("The correct solution is: " + solutionList.get(i)));
                    document.add(new Paragraph("Response Time: " + responseTime[i] / 1000 + " seconds"));
                    document.add(new Paragraph(" "));
                }
            }
    
            // Optional: Add some alignment to center
            Paragraph footer = new Paragraph("End of Report\nThanks for Attending");
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);
    
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Step 5: Close the document
            document.close();
        }
    
        System.out.println("PDF report created successfully!");
        
        try {
            String participantEmail = DatabaseConnection.checkApplicantEmail(username);



            
            // This sends the email with the report attachment to the applicant immediately
            //PDFEmailSender.sendEmailWithAttachment(participantEmail, "The report of the Thrive Challenge", "Thank you for attempting the challenge. Here is the report of the challenge.", "ChallengeReport1.pdf");






            
            // This sends the email with the report attachment to the applicant after the challenge is over
            scheduleEmailWithAttachment(participantEmail, "The report of the Thrive Challenge", "Thank you for attempting the challenge. Here is the report of the challenge.", "ChallengeReport1.pdf", "2025-01-01");





        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    public static void scheduleEmailWithAttachment(String to, String subject, String bodyText, String filePath, String endTime) {
        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Parse the date string
            Date parsedDate = (Date) dateFormat.parse(endTime);

            // Convert to Calendar to set the time to midnight
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            // Get the time in milliseconds
            long sendMillis = calendar.getTimeInMillis();
            long currentMillis = System.currentTimeMillis();
            long delay = sendMillis - currentMillis;

            if (delay <= 0) {
                System.out.println("The send date is in the past. Email will not be sent.");
                return;
            }

            // Create a ScheduledExecutorService with a single thread
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

            // Schedule the sendEmailWithAttachment task to run after the calculated delay
            scheduler.schedule(() -> PDFEmailSender.sendEmailWithAttachment(to, subject, bodyText, filePath), delay, TimeUnit.MILLISECONDS);

            // Shutdown the scheduler after scheduling the task
            scheduler.shutdown();
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Invalid date format. Please use yyyy-MM-dd");
        }
    }

}
