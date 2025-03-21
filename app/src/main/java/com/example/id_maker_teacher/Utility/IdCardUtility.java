package com.example.id_maker_teacher.Utility;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.IOException;

public class IdCardUtility {


    public Context context;
    public String pdfPath;

    public IdCardUtility(Context context) {
        this.context = context;
    }

    private void generatePDF() {
        try {
            // Create a custom folder in internal storage
            File classDataDir = new File(context.getFilesDir(), "ClassData");
            if (!classDataDir.exists()) {
                classDataDir.mkdirs(); // Create the directory if it doesn't exist
            }

            // Define the file path inside "ClassData" folder
            File pdfFile = new File(classDataDir, "GeneratedPDF.pdf");
            pdfPath = pdfFile.getAbsolutePath(); // Store path for later use

            // Create PDF writer
            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A3);

            // Add centered text
            Paragraph paragraph = new Paragraph("Mobile Generated PDF")
                    .setFontSize(30)
                    .setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);
            document.add(paragraph);

            document.close();

            Toast.makeText(context, "PDF Generated in ClassData folder!", Toast.LENGTH_SHORT).show();
            openPdf(); // Show in WebView

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error Generating PDF", Toast.LENGTH_SHORT).show();
        }
    }
    //open pdf in editor
    private void openPdf() {

        File file = new File(context.getFilesDir(), "ClassData/GeneratedPDF.pdf");

        if (!file.exists()) {
            Toast.makeText(context, "PDF not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uri = FileProvider.getUriForFile(context, "com.example.id_maker_teacher.fileprovider", file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            context.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context, "No PDF viewer app found!", Toast.LENGTH_SHORT).show();
        }
    }
    //convert in to image
    public static Bitmap convertPdfToImage(File pdfFilePath) {
        File file = (pdfFilePath);
        if (!file.exists()) return null;

        try {
            ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
            PdfRenderer pdfRenderer = new PdfRenderer(parcelFileDescriptor);

            PdfRenderer.Page page = pdfRenderer.openPage(0);  // Open first page
            Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

            // Cleanup
            page.close();
            pdfRenderer.close();
            parcelFileDescriptor.close();

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
