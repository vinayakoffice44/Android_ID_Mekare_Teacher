package com.example.id_maker_teacher.Test;

import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.id_maker_teacher.Activity.HomePage;
import com.example.id_maker_teacher.Model.StudentModel;
import com.example.id_maker_teacher.R;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.geom.PageSize;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class test extends AppCompatActivity {

    private String pdfPath;
    private ImageView pdfImageView;
    private PdfRenderer pdfRenderer;
    private PdfRenderer.Page currentPage;
    private ParcelFileDescriptor fileDescriptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pdfImageView = findViewById(R.id.pdfImageView);

        Button btnGenerate = findViewById(R.id.btnGenerate);
        Button btnShare = findViewById(R.id.btnShare);


        btnGenerate.setOnClickListener(v -> {
            //generatePDF();

            startActivity(new Intent(test.this, HomePage.class));
        });
        btnShare.setOnClickListener(v -> {
           /* File classDataDir = new File(getFilesDir(), "ClassData");
            if (!classDataDir.exists()) {
                classDataDir.mkdirs(); // Create the directory if it doesn't exist
            }

            // Define the file path inside "ClassData" folder
            File pdfFile = new File(classDataDir, "GeneratedPDF.pdf");
            Bitmap pdfBitmap = convertPdfToImage( pdfFile);
            if (pdfBitmap != null) {
                pdfImageView.setImageBitmap(pdfBitmap);
            }*/

            new IdGenreater(test.this).SingeIDGenerate(new StudentModel(
                    15,
                    "Vinayak Arjun Mhavarkar ram kumar ",
                    "15",
                    "20",
                    "06/03/2002",
                    "A",
                    "B+",
                    "/data/user/0/com.example.id_maker_teacher/files/app/images/1/student_images/5.jpg"));
        });

        btnShare.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

           /*     File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File file = new File(path, "School_ID_Card.pdf");*/
                File directory = new File(getFilesDir(), "app/test");
                File file = new File(directory, "front.pdf");
                Bitmap pdfBitmap = convertPdfToImage( file);
            if (pdfBitmap != null) {
                pdfImageView.setImageBitmap(pdfBitmap);
            }
                return false;
            }
        });
    }




    //external  storage store
    /*private void generatePDF() {
        try {
            // PDF file location
            pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/GeneratedPDF.pdf";
            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A3);

            // Add text to center of the page
            Paragraph paragraph = new Paragraph("Mobile Generated PDF")
                    .setFontSize(30)
                    .setTextAlignment(com.itextpdf.layout.property.TextAlignment.CENTER);
            document.add(paragraph);

            document.close();

            Toast.makeText(this, "PDF Generated!", Toast.LENGTH_SHORT).show();
            previewPDF(); // Show PDF after generation

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error Generating PDF", Toast.LENGTH_SHORT).show();
        }
    }*/

    // internal storage store
    private void generatePDF() {
        try {
            // Create a custom folder in internal storage
            File classDataDir = new File(getFilesDir(), "ClassData");
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

            Toast.makeText(this, "PDF Generated in ClassData folder!", Toast.LENGTH_SHORT).show();
            openPdf(); // Show in WebView

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error Generating PDF", Toast.LENGTH_SHORT).show();
        }
    }
    //open pdf in editor
    private void openPdf() {

        File file = new File(getFilesDir(), "ClassData/GeneratedPDF.pdf");

        if (!file.exists()) {
            Toast.makeText(this, "PDF not found!", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uri = FileProvider.getUriForFile(this, "com.example.id_maker_teacher.fileprovider", file);

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "No PDF viewer app found!", Toast.LENGTH_SHORT).show();
        }
    }
    //convert in to image
    public static Bitmap convertPdfToImage( File pdfFilePath) {
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