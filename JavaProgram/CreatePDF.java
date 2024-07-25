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
    public static void reportpdf(long[] responsetime, List<String> questionsList, List<String> solutionlist, int score, long totaltime, String username,List<String> userAnswers, String Date) throws SQLException {
        // Step 1: Create a Document instance
        Document document = new Document();
        
        try {
            // Step 2: Create a PdfWriter instance
            PdfWriter.getInstance(document, new FileOutputStream("ChallengeReport1.pdf"));
            
            // Step 3: Open the document
            document.open();
            
            // Step 4: Add the challenge report details
            document.add(new Paragraph("Challenge Report"));
            document.add(new Paragraph(" "));
            
            document.add(new Paragraph("Score: " + score));
            document.add(new Paragraph("Total Time: " + totaltime/1000 + " seconds"));
            document.add(new Paragraph(" "));
            
            for (int i = 0; i < questionsList.size(); i++) {
                if(solutionlist.get(i)==userAnswers.get(i)){
                    document.add(new Paragraph("Question " + (i + 1) + ": " + questionsList.get(i)));
                    document.add(new Paragraph("You got the correct answer"));
                    document.add(new Paragraph("Your Answer: "+ userAnswers.get(i)));
                    document.add(new Paragraph("Response Time: " + responsetime[i]/1000 + " seconds"));
                    document.add(new Paragraph(" "));
                }else{
                    document.add(new Paragraph("Question " + (i + 1) + ": " + questionsList.get(i)));
                    document.add(new Paragraph("You fialed this one:"));
                    document.add(new Paragraph("Your Answer: "+ userAnswers.get(i)));
                    document.add(new Paragraph("The correct solution is : " + solutionlist.get(i)));
                    document.add(new Paragraph("Response Time: " + responsetime[i]/1000 + " seconds"));
                    document.add(new Paragraph(" "));
                }
            }
            
            // Optional: Add some alignment to center
            Paragraph footer = new Paragraph("End of Report\n Thanks for Attending");
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Step 5: Close the document
            document.close();
        }
        
        System.out.println("PDF report created successfully!");
        String participant_Email = DatabaseConnection.checkApplicantEmail(username);
        PDFEmailSender.sendEmailWithAttachment(participant_Email, "The report of the Thrive Challenge", "Thank You for attempting the challenge. Here is the report of the challenge.","ChallengeReport1.pdf");
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
