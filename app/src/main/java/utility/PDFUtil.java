package utility;

import android.os.Environment;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by adrian on 8/20/2016.
 */
public class PDFUtil {

    public static void createPDF(){
        Document doc = new Document();
        String path = Environment.getExternalStorageDirectory() + "/test.pdf";
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(path));
            doc.open();
            doc.add(new Paragraph("Test PDF"));
            doc.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
