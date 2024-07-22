import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

public class CreatePDF {
    public static void main(String[] args) {
        // Step 1: Create a Document instance
        Document document = new Document();
        
        try {
            // Step 2: Create a PdfWriter instance
            PdfWriter.getInstance(document, new FileOutputStream("output.pdf"));
            
            // Step 3: Open the document
            document.open();
            
            // Step 4: Add content to the document
            document.add(new Paragraph("Hello, World!"));
            document.add(new Paragraph("This is a sample PDF created using iText 2.1.7."));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Step 5: Close the document
            document.close();
        }
        
        System.out.println("PDF created successfully!");
    }
}
