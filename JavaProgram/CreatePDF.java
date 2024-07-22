import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.List;


public class CreatePDF {
    public static void reportpdf(long[] responsetime, List<String> questionsList, List<String> solutionlist, int score, long totaltime) {
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
            document.add(new Paragraph("Total Time: " + totaltime + " seconds"));
            document.add(new Paragraph(" "));
            
            for (int i = 0; i < questionsList.size(); i++) {
                document.add(new Paragraph("Question " + (i + 1) + ": " + questionsList.get(i)));
                document.add(new Paragraph("Solution: " + solutionlist.get(i)));
                document.add(new Paragraph("Response Time: " + responsetime[i] + " seconds"));
                document.add(new Paragraph(" "));
            }
            
            // Optional: Add some alignment to center
            Paragraph footer = new Paragraph("End of Report");
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Step 5: Close the document
            document.close();
        }
        
        System.out.println("PDF report created successfully!");
        PDFEmailSender.sendEmailWithAttachment("sseluyindaeva@gmail.com", "The report of the Thrive Challenge", "Thank You for attempting the challenge. Here is the report of the challenge.","ChallengeReport1.pdf");
    }

    // public static void main(String[] args) {
    //     // Sample data
    //     long[] responseTimes = {10, 20, 15, 30, 25, 20, 15, 10, 5, 10};
    //     List<String> questions = List.of(
    //             "Question 1", "Question 2", "Question 3", "Question 4", "Question 5",
    //             "Question 6", "Question 7", "Question 8", "Question 9", "Question 10");
    //     List<String> solutions = List.of(
    //             "Solution 1", "Solution 2", "Solution 3", "Solution 4", "Solution 5",
    //             "Solution 6", "Solution 7", "Solution 8", "Solution 9", "Solution 10");
    //     int score = 85;
    //     int totalTime = 180;

    //     // Generate the report
    //     reportpdf(responseTimes, questions, solutions, score, totalTime);

    // }
}
