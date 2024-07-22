import org.apache.pdfbox.pdmodel.PDDocument;


import java.io.IOException;

public class CreatePdfWithPDFBox {
    public static void main(String[] args) throws IOException{
        PDDocument document = new PDDocument();
        document.save("mypdf.pdf");
        System.out.println("pdf created");
        document.close();
    }
}