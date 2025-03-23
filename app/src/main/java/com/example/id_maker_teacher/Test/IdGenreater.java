package com.example.id_maker_teacher.Test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.id_maker_teacher.Model.OrganizationModel;
import com.example.id_maker_teacher.Model.StudentModel;
import com.example.id_maker_teacher.R;
import com.example.id_maker_teacher.Utility.ErrorUtility;
import com.example.id_maker_teacher.Utility.SharedPreferencesHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import android.graphics.Matrix;


public class IdGenreater {

    Context context;
    TextPaint textPaint;
    OrganizationModel organizationModel;
    SharedPreferencesHelper sharedPreferencesHelper;
    public IdGenreater(Context context) {
        this.context = context;
        sharedPreferencesHelper = new SharedPreferencesHelper(context);
        organizationModel = sharedPreferencesHelper.getOrganization();
    }


    // front sim pal design
    public void SingeIDGenerate(StudentModel studentModel) {
        PdfDocument pdfDocument = new PdfDocument();

        double Width = 57.298;  // width in mm
        double Height = 89.348; // height in mm
        double dpi = 300; // Screen DPI

        int cardWidth = (int) ((Width * dpi) / 25.4);  // Convert mm to pixels
        int cardHeight = (int) ((Height * dpi) / 25.4);

        // Calculate section heights
        int section1Height = (int) (cardHeight * 0.25);
        int section2Height = (int) (cardHeight * 0.35);
        int section3Height = (int) (cardHeight * 0.40);

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(cardWidth, cardHeight, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        // Background
        Bitmap cardBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
        cardBackground = Bitmap.createScaledBitmap(cardBackground, cardWidth, cardHeight, false);
        canvas.drawBitmap(cardBackground, 0, 0, null);




        // Section 1: School Name (Multi-line Support with Wrapping)
        String schoolName = organizationModel.getOrganizationName();
        String schoolAddress = organizationModel.getOrganizationAddress();

        textPaint = new TextPaint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(42);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Define max width for school name
        int textWidth = cardWidth - 20;  // 10px margin on both sides

        // Wrap text using StaticLayout
        StaticLayout schoolNameLayout = new StaticLayout(
                schoolName, textPaint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false
        );

        // Draw school name inside the card width
        canvas.save();
        canvas.translate(10, 30);  // X=10 (margin), Y=30 (top margin)
        schoolNameLayout.draw(canvas);
        canvas.restore();

        // Adjust Y-position dynamically for the school address
        int schoolNameHeight = schoolNameLayout.getHeight();

        // Draw School Address Below the Name
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(30);  // Smaller font size for address

        StaticLayout schoolAddressLayout = new StaticLayout(
                schoolAddress, textPaint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false
        );

        canvas.save();
        canvas.translate(10, 30 + schoolNameHeight + 10); // Place below school name
        schoolAddressLayout.draw(canvas);
        canvas.restore();




        // Section 2: Student Image
        String studentImagePath = studentModel.getProfileImage(); // Update this path dynamically
        File imgFile = new File(studentImagePath);

        Bitmap studentImage;
        if (imgFile.exists()) {
            studentImage = getOptimizedBitmap(studentImagePath, section2Height, section2Height);
            studentImage = scaleBitmap(studentImage, section2Height, section2Height);
        } else {
            // If image not found, use a default image
            studentImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.sm);
            studentImage = Bitmap.createScaledBitmap(studentImage, section2Height, section2Height, false);
        }

        // Position for the image
        int imageX = (cardWidth - section2Height) / 2;
        int imageY = section1Height;

        // Draw the image
        canvas.drawBitmap(studentImage, imageX, imageY, null);

        // Add a border around the image
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.BLACK);  // Border color (Change as needed)
        borderPaint.setStyle(Paint.Style.STROKE); // Stroke mode for border
        borderPaint.setStrokeWidth(8);  // Border thickness

        // Draw the border (Rectangle)
        canvas.drawRect(imageX, imageY, imageX + section2Height, imageY + section2Height, borderPaint);





        // Section 3: Student Details (Proper Alignment)
        String studentName = studentModel.getStudentFullName();
        String studentClass = studentModel.getStudentClass();
        String division = studentModel.getDiv();
        String dob = studentModel.getDateOfBirth();

    /*    textPaint.setTextSize(20);
        textPaint.setTypeface(Typeface.DEFAULT);
        textPaint.setTextAlign(Paint.Align.LEFT);*/

        int textStartY = section1Height + section2Height + 50;
        int labelWidth = 40; // Fixed width for labels

// Function to draw aligned text

// Draw student details with proper alignment
        textStartY = drawAlignedText(canvas, "Name",": "+studentName, 90, textStartY,cardWidth);
        textStartY = drawAlignedText(canvas, "Class", ": "+studentClass, 90, textStartY,cardWidth);
        textStartY = drawAlignedText(canvas, "Div", ": "+division, 90, textStartY,cardWidth);
        textStartY = drawAlignedText(canvas, "DOB",": "+ dob, 90, textStartY,cardWidth);


        pdfDocument.finishPage(page);

        // Save PDF
        File directory = new File(context.getFilesDir(), "app/test");

// Create the directory if it does not exist
        if (!directory.exists()) {
            directory.mkdirs();
        }

// Define the PDF file path
        File file = new File(directory, "front.pdf");

// Delete the existing file if it exists
        if (file.exists()) {
            file.delete();
        }

        try {
            FileOutputStream fos = new FileOutputStream(file);
            pdfDocument.writeTo(fos);
            fos.close();
            Toast.makeText(context, "New ID Card Created Successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            String error = e.getMessage();
            e.printStackTrace();
            Toast.makeText(context, "Error Creating ID Card", Toast.LENGTH_SHORT).show();

        }finally {
            pdfDocument.close();
        }

    }


    public int drawAlignedText(Canvas canvas, String label, String value, int x, int y,int cardWidth) {
        textPaint.setTextSize(35);
        textPaint.setTypeface(Typeface.DEFAULT);
        textPaint.setTextAlign(Paint.Align.LEFT);

        // Draw label
        canvas.drawText(label, x, y, textPaint);

        // Handle long values using StaticLayout
        int textWidth = cardWidth - 200;
        StaticLayout valueLayout = new StaticLayout(value, textPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

        // Draw value text
        canvas.save();
        canvas.translate(x + 110, y - 35); // Position the text
        valueLayout.draw(canvas);
        canvas.restore();

        // Return new y position for next text (shift by text height)
        return y + valueLayout.getHeight() + 20; // Adding extra spacing to prevent overlap
    }

    public Bitmap getOptimizedBitmap(String imagePath, int targetWidth, int targetHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;  // High-quality image
        options.inJustDecodeBounds = true; // Get dimensions without loading
        BitmapFactory.decodeFile(imagePath, options);

        // Calculate the best scaling factor
        options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight);
        options.inJustDecodeBounds = false; // Now load the full bitmap
        return BitmapFactory.decodeFile(imagePath, options);
    }

    public Bitmap scaleBitmap(Bitmap original, int targetWidth, int targetHeight) {
        float scaleX = (float) targetWidth / original.getWidth();
        float scaleY = (float) targetHeight / original.getHeight();
        Matrix matrix = new Matrix();
        matrix.setScale(scaleX, scaleY);

        return Bitmap.createBitmap(original, 0, 0, original.getWidth(), original.getHeight(), matrix, true);
    }


    // Function to calculate optimal inSampleSize
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;

            // Keep reducing inSampleSize while it's larger than the requested size
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }



    // back side
    public void BackIDGenerate(StudentModel studentModel) {
        PdfDocument pdfDocument = new PdfDocument();

        double Width = 57.298;  // width in mm
        double Height = 89.348; // height in mm
        double dpi = 300; // Screen DPI

        int cardWidth = (int) ((Width * dpi) / 25.4);  // Convert mm to pixels
        int cardHeight = (int) ((Height * dpi) / 25.4);

        // Start at 30% of card height
        int startY = (int) (cardHeight * 0.30);

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(cardWidth, cardHeight, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        // Background
        Bitmap cardBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
        cardBackground = Bitmap.createScaledBitmap(cardBackground, cardWidth, cardHeight, false);
        canvas.drawBitmap(cardBackground, 0, 0, null);

        // Section: Parent Information
        String parent1Name = studentModel.getParentOneName();
        String parent1Number = studentModel.getParentOnePhone();
        String parent2Name = studentModel.getParentTwoName();
        String parent2Number = studentModel.getParentTwoPhone();
        String studentAddress = studentModel.getHomeAddress();

        TextPaint Title = new TextPaint();
        Title.setColor(Color.BLACK);
        Title.setTextSize(30);
        Title.setTypeface(Typeface.DEFAULT_BOLD);

        TextPaint Value = new TextPaint();
        Value.setColor(Color.BLACK);
        Value.setTypeface(Typeface.DEFAULT);
        Value.setTextSize(30);


        // Parent details with proper alignment
        startY = drawAlignedText(canvas, "Parent 1", ": " + parent1Name, 80, startY, cardWidth);
        startY = drawAlignedText(canvas, "Contact", ": " + parent1Number, 50, startY, cardWidth);
        startY += 20; // Extra spacing

        startY = drawAlignedText(canvas, "Parent 2", ": " + parent2Name, 80, startY, cardWidth);
        startY = drawAlignedText(canvas, "Contact", ": " + parent2Number, 50, startY, cardWidth);
        startY += 30; // Extra spacing







        startY += 30; // Extra spacing

        // Section: Student Address
        textPaint.setTextSize(28);
        textPaint.setTypeface(Typeface.DEFAULT);
        StaticLayout addressLayout = new StaticLayout(
                "Address: " + studentAddress, textPaint, cardWidth - 40, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false
        );

        canvas.save();
        canvas.translate(50, startY);
        addressLayout.draw(canvas);
        canvas.restore();

        // Section: Principal Signature (Bottom Left)
        String signaturePath = organizationModel.getOrganizationPrincipalSignature(); // Signature image URL
        Bitmap signatureBitmap = getBitmapFromUrl(signaturePath);

        if (signatureBitmap != null) {
            int signWidth = 150;
            int signHeight = 60;
            Bitmap scaledSignature = Bitmap.createScaledBitmap(signatureBitmap, signWidth, signHeight, false);
            canvas.drawBitmap(scaledSignature, 50, cardHeight - 90, null);
        }

        pdfDocument.finishPage(page);

        // Save PDF
        File directory = new File(context.getFilesDir(), "app/test");

        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, "back.pdf");

        if (file.exists()) {
            file.delete();
        }

        try {
            FileOutputStream fos = new FileOutputStream(file);
            pdfDocument.writeTo(fos);
            fos.close();
            Toast.makeText(context, "Back Side Created Successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error Creating Back Side", Toast.LENGTH_SHORT).show();
        } finally {
            pdfDocument.close();
        }
    }

    public Bitmap getBitmapFromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if loading fails
        }
    }





    // show image image
    public  void ShowId(File pdfFilePath, ImageView imageView) {
        File file = (pdfFilePath);
        if (file.exists()){

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
                imageView.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            new ErrorUtility().SimpleError(context,"Unable to create the pdf,\nTry again later");
        }



    }






}
