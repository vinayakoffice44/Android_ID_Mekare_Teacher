package com.example.id_maker_teacher.Templeate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.widget.Toast;

import com.example.id_maker_teacher.Model.OrganizationModel;
import com.example.id_maker_teacher.Model.StudentModel;
import com.example.id_maker_teacher.R;
import com.example.id_maker_teacher.Utility.SharedPreferencesHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;



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
import java.util.ArrayList;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import android.graphics.Matrix;

public class Design_One {

    Context context;
    TextPaint textPaint;
    OrganizationModel organizationModel;
    SharedPreferencesHelper sharedPreferencesHelper;
    public Design_One(Context context) {
        this.context = context;
        sharedPreferencesHelper = new SharedPreferencesHelper(context);
        organizationModel = sharedPreferencesHelper.getOrganization();
    }


    public void FrontPart(StudentModel studentModel) {
        Bitmap Logo;
        int margin = 20;
        PdfDocument pdfDocument = new PdfDocument();

        double Width = 57.298;  // width in mm
        double Height = 89.348; // height in mm
        double dpi = 300; // Screen DPI

        int cardWidth = (int) ((Width * dpi) / 25.4);  // Convert mm to pixels
        int cardHeight = (int) ((Height * dpi) / 25.4);

        // Calculate section heights
        int section1LogoWidth = (int) (cardWidth * 0.25);  // 30% width for logo
        int section1TextWidth = (int) (cardWidth * 0.75);  // 70% width for text

        int section1Height = (int) (cardHeight * 0.20);
        int section2Height = (int) (cardHeight * 0.40);
        int section3Height = (int) (cardHeight * 0.40);

        int logoWidth = section1LogoWidth - (2 * margin);  // Subtract margin
        int logoHeight = section1Height - (2 * margin);    // Subtract margin

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(cardWidth, cardHeight, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        // Background
        Bitmap cardBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_3);
        cardBackground = Bitmap.createScaledBitmap(cardBackground, cardWidth, cardHeight, false);
        canvas.drawBitmap(cardBackground, 0, 0, null);




        // Section 1: School Logo and School Name
        String schoolName = organizationModel.getOrganizationName();
        String logo_path = organizationModel.getOrganizationLogoPath(); // Update this path dynamically
        File logoFile = new File(logo_path);
        if (logoFile.exists()) {
            Logo = getOptimizedBitmap(logo_path, logoWidth, logoWidth);
            Logo = scaleBitmap(Logo, logoWidth, logoWidth);
        } else {
            // Use a default placeholder image if the logo is missing
            Logo = BitmapFactory.decodeResource(context.getResources(), R.drawable.sm);
            Logo = Bitmap.createScaledBitmap(Logo, logoWidth, logoHeight, false);
        }
        // Calculate the center position of the logo inside its section
        int logoX = margin;  // Left margin
        int logoY = (section1Height - logoHeight) / 2; // Center vertically
        // Draw the logo
        canvas.drawBitmap(Logo, logoX, logoY, null);

        // Set text properties
        textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(42);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Define max width for school name (right side)
        int textWidth = section1TextWidth - 20;  // Leave some margin

        // Wrap text using StaticLayout
        StaticLayout schoolNameLayout = new StaticLayout(
                schoolName, textPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false
        );

        // Draw the school name inside the allocated space
        canvas.save();
        canvas.translate(section1LogoWidth + 10, (section1Height - schoolNameLayout.getHeight()) / 2); // Align text in center
        schoolNameLayout.draw(canvas);
        canvas.restore();






        // Section 2: Student Image
        textPaint.setColor(Color.BLACK);
        String studentImagePath = studentModel.getProfileImage(); // Update this path dynamically
        File imgFile = new File(studentImagePath);

        int passportSize = (int) (section2Height * 0.7);
        int passportSizeW = (int) (cardWidth * 0.35);


        Bitmap studentImage;
        if (imgFile.exists()) {
            studentImage = getOptimizedBitmap(studentImagePath, passportSizeW, passportSize);
            studentImage = scaleBitmap(studentImage, passportSizeW, passportSize);
        } else {
            // If image not found, use a default image
            studentImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.sm);
            studentImage = Bitmap.createScaledBitmap(studentImage, section2Height, section2Height, false);
        }

        // Position for the image
        // Center the smaller image
        int imageX = (cardWidth - passportSizeW) / 2;
        int imageY = section1Height + (section2Height - passportSizeW) / 2;

        // Draw the image
        canvas.drawBitmap(studentImage, imageX, imageY, null);


        // Add a border around the image
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.BLACK);  // Border color (Change as needed)
        borderPaint.setStyle(Paint.Style.STROKE); // Stroke mode for border
        //borderPaint.setStrokeWidth(8);  // Border thickness

        // Draw the border (Rectangle)
        borderPaint.setStrokeWidth(5);  // Reduce border thickness
        canvas.drawRect(imageX, imageY, imageX + passportSizeW, imageY + passportSize, borderPaint);





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
        textStartY = drawAlignedText(canvas, "Name",": "+studentName, 98, textStartY,cardWidth);
        textStartY = drawAlignedText(canvas, "Class", ": "+studentClass, 98, textStartY,cardWidth);
        textStartY = drawAlignedText(canvas, "Div", ": "+division, 98, textStartY,cardWidth);
        textStartY = drawAlignedText(canvas, "DOB",": "+ dob, 98, textStartY,cardWidth);


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
        canvas.translate(x + 118, y - 35); // Position the text
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


////////// back sid
    public void BackIDGenerate(StudentModel studentModel) {
        Bitmap Logo;
        int margin = 20;
        PdfDocument pdfDocument = new PdfDocument();

        double Width = 57.298;  // width in mm
        double Height = 89.348; // height in mm
        double dpi = 300; // Screen DPI

        int cardWidth = (int) ((Width * dpi) / 25.4);  // Convert mm to pixels
        int cardHeight = (int) ((Height * dpi) / 25.4);

        // Section Heights
        int section1Height = (int) (cardHeight * 0.25);
        int section2Height = (int) (cardHeight * 0.35);
        int section3Height = (int) (cardHeight * 0.35);

        int section1LogoWidth = (int) (cardWidth * 0.25);  // 30% width for logo
        int section1TextWidth = (int) (cardWidth * 0.75);  // 70% width for text

        int logoWidth = section1LogoWidth - (2 * margin);  // Subtract margin
        int logoHeight = section1Height - (2 * margin);

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(cardWidth, cardHeight, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        // Background
        Bitmap cardBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_3);
        cardBackground = Bitmap.createScaledBitmap(cardBackground, cardWidth, cardHeight, false);
        canvas.drawBitmap(cardBackground, 0, 0, null);

        // ---------------- SECTION 1: School Logo & Name ----------------
        String schoolName = organizationModel.getOrganizationName();
        String logo_path = organizationModel.getOrganizationLogoPath(); // Update this path dynamically
        File logoFile = new File(logo_path);
        if (logoFile.exists()) {
            Logo = getOptimizedBitmap(logo_path, logoWidth, logoWidth);
            Logo = scaleBitmap(Logo, logoWidth, logoWidth);
        } else {
            // Use a default placeholder image if the logo is missing
            Logo = BitmapFactory.decodeResource(context.getResources(), R.drawable.sm);
            Logo = Bitmap.createScaledBitmap(Logo, logoWidth, logoHeight, false);
        }
        // Calculate the center position of the logo inside its section
        int logoX = margin;  // Left margin
        int logoY = (section1Height - logoHeight) / 2; // Center vertically
        // Draw the logo
        canvas.drawBitmap(Logo, logoX, logoY, null);

        // Set text properties
        textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(42);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Define max width for school name (right side)
        int textWidth = section1TextWidth - 20;  // Leave some margin

        // Wrap text using StaticLayout
        StaticLayout schoolNameLayout = new StaticLayout(
                schoolName, textPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false
        );

        // Draw the school name inside the allocated space
        canvas.save();
        canvas.translate(section1LogoWidth + 10, 20/*(section1Height - schoolNameLayout.getHeight()) / 2*/); // Align text in center
        schoolNameLayout.draw(canvas);
        canvas.restore();

        // ---------------- SECTION 2: Parent Info & Student Address ----------------
        int section2Y = section1Height + margin;
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(36);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Parent Details Heading (Bold & Big)
        canvas.drawText("Parent Details", margin+24, section2Y, textPaint);

        textPaint.setTextSize(32);
        int lineSpacing = 40;
        int textY = section2Y + lineSpacing;

        // Parent 1
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Name: ", margin+30, textY, textPaint);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        canvas.drawText(studentModel.getParentOneName(), margin + 150, textY, textPaint);

        textY += lineSpacing;
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Phone: ", margin+30, textY, textPaint);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        canvas.drawText(studentModel.getParentOnePhone(), margin + 150, textY, textPaint);

        textY += lineSpacing + 10; // Extra space

        // Parent 2
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Name: ", margin+30, textY, textPaint);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        canvas.drawText(studentModel.getParentOneName(), margin + 150, textY, textPaint);

        textY += lineSpacing;
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Phone: ", margin+30, textY, textPaint);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        canvas.drawText(studentModel.getParentTwoPhone(), margin + 150, textY, textPaint);

        textY += lineSpacing + 20; // Extra space

        // Student Address Heading
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        textPaint.setTextSize(36);
        canvas.drawText("Student Address", margin+22, textY, textPaint);

        textY += 20;

        // Address Text
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        textPaint.setTextSize(32);
        StaticLayout addressLayout = new StaticLayout(
                studentModel.getHomeAddress(), textPaint, cardWidth - (2 * margin), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false
        );

        canvas.save();
        canvas.translate(margin+22, textY);
        addressLayout.draw(canvas);
        canvas.restore();

// ---------------- SECTION 3: School Address & Principal's Signature ----------------
        int section3Y = section1Height + section2Height + margin;
        textPaint.setTextSize(34);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("School Address", margin + 22, section3Y, textPaint);

        textPaint.setTextSize(30);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        StaticLayout schoolAddressLayout = new StaticLayout(
                organizationModel.getOrganizationAddress() + "\nPhone: " + organizationModel.getOrganizationPhone(),
                textPaint, cardWidth - 2 * margin, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false
        );

        canvas.save();
        canvas.translate(margin + 22, section3Y + 20);
        schoolAddressLayout.draw(canvas);
        canvas.restore();

// Principal Signature
        String signaturePath = organizationModel.getOrganizationPrincipalSignature();
        File signatureFile = new File(signaturePath);
        Bitmap signatureBitmap;

        if (signatureFile.exists()) {
            signatureBitmap = getOptimizedBitmap(signaturePath, 150, 80);
        } else {
            signatureBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
        }

        int signX = cardWidth - signatureBitmap.getWidth() - margin;
        int signY = (cardHeight - signatureBitmap.getHeight() - margin-80);

// Draw the signature
        canvas.drawBitmap(signatureBitmap, signX, signY, null);

// Draw "Principal" below the signature
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        textPaint.setTextSize(28);
        canvas.drawText("Principal", signX + 120, signY + signatureBitmap.getHeight() + 10, textPaint);

        // ---------------- FINISH PAGE & SAVE PDF ----------------
        pdfDocument.finishPage(page);

        // Save PDF
        File directory = new File(context.getFilesDir(), "app/test");
        if (!directory.exists()) directory.mkdirs();

        File file = new File(directory, "back.pdf");
        if (file.exists()) file.delete();

        try {
            FileOutputStream fos = new FileOutputStream(file);
            pdfDocument.writeTo(fos);
            fos.close();
            Toast.makeText(context, "Back ID Card Created Successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error Creating ID Card", Toast.LENGTH_SHORT).show();
        } finally {
            pdfDocument.close();
        }
    }




//////////////// alll card togater
    public void generateAllStudentIDs(ArrayList<StudentModel> studentList) {
        PdfDocument pdfDocument = new PdfDocument();

        double dpi = 300; // High resolution
        int a3Width = (int) ((297 * dpi) / 25.4); // A3 Portrait Width
        int a3Height = (int) ((420 * dpi) / 25.4); // A3 Portrait Height

        // ID Card Size (Converted to Pixels)
        int idWidth = (int) ((57.298 * dpi) / 25.4);
        int idHeight = (int) ((89.348 * dpi) / 25.4);

        int marginX = 20; // Horizontal margin between ID cards
        int marginY = 20; // Vertical margin between ID cards
        int idsPerRow = 5; // 5 ID cards per row
        int idsPerColumn = 10; // 10 ID cards per column
        int maxPerPage = idsPerRow * idsPerColumn; // 50 IDs per A3 page

        int totalStudents = studentList.size();
        int totalPages = (int) Math.ceil((double) totalStudents / maxPerPage);

        int studentIndex = 0;

        for (int pageIndex = 0; pageIndex < totalPages; pageIndex++) {
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(a3Width, a3Height, pageIndex + 1).create();
            PdfDocument.Page page = pdfDocument.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            Paint paint = new Paint();

            // Set White Background for A3 page
            canvas.drawColor(Color.WHITE);

            for (int row = 0; row < idsPerColumn; row++) {
                for (int col = 0; col < idsPerRow; col++) {
                    if (studentIndex >= totalStudents) {
                        // Stop drawing if no more students
                        break;
                    }

                    StudentModel student = studentList.get(studentIndex);
                    int x = marginX + col * (idWidth + marginX);
                    int y = marginY + row * (idHeight + marginY);

                    generateFrontIDCard(canvas, student, x, y, idWidth, idHeight);
                    studentIndex++;
                }
            }

            pdfDocument.finishPage(page);
        }

        // Save PDF
        File directory = new File(context.getFilesDir(), "app/test/all_student");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, "all_students_A3.pdf");
        if (file.exists()) {
            file.delete();
        }

        try {
            FileOutputStream fos = new FileOutputStream(file);
            pdfDocument.writeTo(fos);
            fos.close();
            Toast.makeText(context, "Student ID Cards Generated Successfully!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error Creating ID Cards", Toast.LENGTH_SHORT).show();
        } finally {
            pdfDocument.close();
        }
    }



    private void generateFrontIDCard(Canvas canvas, StudentModel studentModel, int x, int y, int idWidth, int idHeight) {
        Bitmap Logo;
        int margin = 10; // Reduced margin for better spacing

        // Calculate section heights
        int section1Height = (int) (idHeight * 0.20);
        int section2Height = (int) (idHeight * 0.40);
        int section3Height = (int) (idHeight * 0.40);

        // Section 1: Background
        Bitmap cardBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_3);
        cardBackground = Bitmap.createScaledBitmap(cardBackground, idWidth, idHeight, false);
        canvas.drawBitmap(cardBackground, x, y, null);

        // Section 1: School Logo and Name
        String schoolName = organizationModel.getOrganizationName();
        String logo_path = organizationModel.getOrganizationLogoPath();
        File logoFile = new File(logo_path);

        int logoSize = (int) (section1Height * 0.8);
        int logoX = x + margin; // Left margin
        int logoY = y + (section1Height - logoSize) / 2;

        if (logoFile.exists()) {
            Logo = getOptimizedBitmap(logo_path, logoSize, logoSize);
        } else {
            Logo = BitmapFactory.decodeResource(context.getResources(), R.drawable.sm);
        }
        Logo = Bitmap.createScaledBitmap(Logo, logoSize, logoSize, false);
        canvas.drawBitmap(Logo, logoX, logoY, null);

        // School Name
        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(36);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        StaticLayout schoolNameLayout = new StaticLayout(
                schoolName, textPaint, idWidth - logoSize - 3 * margin, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false
        );

        canvas.save();
        canvas.translate(x + logoSize + 2 * margin, y + (section1Height - schoolNameLayout.getHeight()) / 2);
        schoolNameLayout.draw(canvas);
        canvas.restore();

        // Section 2: Student Image
        String studentImagePath = studentModel.getProfileImage();
        File imgFile = new File(studentImagePath);

        int passportSize = (int) (section2Height * 0.7);
        int passportSizeW = (int) (idWidth * 0.35);

        Bitmap studentImage;
        if (imgFile.exists()) {
            studentImage = getOptimizedBitmap(studentImagePath, passportSizeW, passportSize);
        } else {
            studentImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.sm);
        }
        studentImage = Bitmap.createScaledBitmap(studentImage, passportSizeW, passportSize, false);

        int imageX = x + (idWidth - passportSizeW) / 2;
        int imageY = y + section1Height + (section2Height - passportSize) / 2;
        canvas.drawBitmap(studentImage, imageX, imageY, null);

        // Border around Image
        Paint borderPaint = new Paint();
        borderPaint.setColor(Color.BLACK);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(5);
        canvas.drawRect(imageX, imageY, imageX + passportSizeW, imageY + passportSize, borderPaint);

        // Section 3: Student Details
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(28);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        int textStartY = y + section1Height + section2Height + 30;

        // Draw Student Details with proper alignment
        textStartY = drawAlignedTextForAll(canvas, "Name", ": " + studentModel.getStudentFullName(), x + 98, textStartY, idWidth);
        textStartY = drawAlignedTextForAll(canvas, "Class", ": " + studentModel.getStudentClass(), x + 98, textStartY, idWidth);
        textStartY = drawAlignedTextForAll(canvas, "Div", ": " + studentModel.getDiv(), x + 98, textStartY, idWidth);
        textStartY = drawAlignedTextForAll(canvas, "DOB", ": " + studentModel.getDateOfBirth(), x + 98, textStartY, idWidth);
    }



    public int drawAlignedTextForAll(Canvas canvas, String label, String value, int x, int y,int cardWidth) {
        TextPaint textPaint = new TextPaint();
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
        canvas.translate(x + 118, y - 35); // Position the text
        valueLayout.draw(canvas);
        canvas.restore();

        // Return new y position for next text (shift by text height)
        return y + valueLayout.getHeight() + 20; // Adding extra spacing to prevent overlap
    }
    public void generateBackSide(ArrayList<StudentModel> studentList) {

        PdfDocument pdfDocument = new PdfDocument();

        double dpi = 300; // High resolution
        int a3Width = (int) ((297 * dpi) / 25.4); // A3 Portrait Width
        int a3Height = (int) ((420 * dpi) / 25.4); // A3 Portrait Height

        // ID Card Size (Converted to Pixels)
        int idWidth = (int) ((57.298 * dpi) / 25.4);
        int idHeight = (int) ((89.348 * dpi) / 25.4);

        int marginX = 20; // Horizontal margin between ID cards
        int marginY = 20; // Vertical margin between ID cards
        int idsPerRow = 5; // 5 ID cards per row
        int idsPerColumn = 10; // 10 ID cards per column
        int maxPerPage = idsPerRow * idsPerColumn; // 50 IDs per A3 page

        int totalStudents = studentList.size();
        int totalPages = (int) Math.ceil((double) totalStudents / maxPerPage);

        int studentIndex = 0;

        for (int pageIndex = 0; pageIndex < totalPages; pageIndex++) {
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(a3Width, a3Height, pageIndex + 1).create();
            PdfDocument.Page page = pdfDocument.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            Paint paint = new Paint();

            // Set White Background for A3 page
            canvas.drawColor(Color.WHITE);

            for (int row = 0; row < idsPerColumn; row++) {
                for (int col = 0; col < idsPerRow; col++) {
                    if (studentIndex >= totalStudents) {
                        // Stop drawing if no more students
                        break;
                    }

                    StudentModel student = studentList.get(studentIndex);
                    int x = marginX + col * (idWidth + marginX);
                    int y = marginY + row * (idHeight + marginY);

                    drawBackCard(canvas, student, x, y, idWidth, idHeight);
                    studentIndex++;
                }
            }

            pdfDocument.finishPage(page);
        }

        // Save PDF
        File directory = new File(context.getFilesDir(), "app/test/all_student");
        if (!directory.exists()) directory.mkdirs();
        File file = new File(directory, "back.pdf");
        if (file.exists()) file.delete();

        try {
            FileOutputStream fos = new FileOutputStream(file);
            pdfDocument.writeTo(fos);
            fos.close();
            Toast.makeText(context, "Back ID Cards Created Successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error Creating Back ID Cards", Toast.LENGTH_SHORT).show();
        } finally {
            pdfDocument.close();
        }
    }

    private void drawBackCard(Canvas canvas, StudentModel studentModel, int x, int y, int idWidth, int idHeight) {

        Bitmap Logo;
        int margin = 0;
        PdfDocument pdfDocument = new PdfDocument();

        double Width = 57.298;  // width in mm
        double Height = 89.348; // height in mm
        double dpi = 300; // Screen DPI

        int cardWidth = (int) ((Width * dpi) / 25.4);  // Convert mm to pixels
        int cardHeight = (int) ((Height * dpi) / 25.4);

        // Section Heights
        int section1Height = (int) (cardHeight * 0.25);
        int section2Height = (int) (cardHeight * 0.35);
        int section3Height = (int) (cardHeight * 0.35);

        int section1LogoWidth = (int) (cardWidth * 0.25);  // 30% width for logo
        int section1TextWidth = (int) (cardWidth * 0.75);  // 70% width for text

        int logoWidth = section1LogoWidth - (2 * margin);  // Subtract margin
        int logoHeight = section1Height - (2 * margin);

        // Background
        Bitmap cardBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg_3);
        cardBackground = Bitmap.createScaledBitmap(cardBackground, cardWidth, cardHeight, false);
        canvas.drawBitmap(cardBackground, x, y, null);

        // ---------------- SECTION 1: School Logo & Name ----------------
        String schoolName = organizationModel.getOrganizationName();
        String logo_path = organizationModel.getOrganizationLogoPath(); // Update this path dynamically
        File logoFile = new File(logo_path);
        if (logoFile.exists()) {
            Logo = getOptimizedBitmap(logo_path, logoWidth, logoWidth);
            Logo = scaleBitmap(Logo, logoWidth, logoWidth);
        } else {
            // Use a default placeholder image if the logo is missing
            Logo = BitmapFactory.decodeResource(context.getResources(), R.drawable.sm);
            Logo = Bitmap.createScaledBitmap(Logo, logoWidth, logoHeight, false);
        }
        // Calculate the center position of the logo inside its section
        int logoX = margin;  // Left margin
        int logoY = (section1Height - logoHeight) / 2; // Center vertically
        // Draw the logo
        canvas.drawBitmap(Logo, x+logoX, y+logoY, null);

        // Set text properties
        textPaint = new TextPaint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(42);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Define max width for school name (right side)
        int textWidth = section1TextWidth - 20;  // Leave some margin

        // Wrap text using StaticLayout
        StaticLayout schoolNameLayout = new StaticLayout(
                schoolName, textPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false
        );

        // Draw the school name inside the allocated space
        canvas.save();
        canvas.translate(x+section1LogoWidth + 10, y+20/*(section1Height - schoolNameLayout.getHeight()) / 2*/); // Align text in center
        schoolNameLayout.draw(canvas);
        canvas.restore();


        // ---------------- SECTION 2: Parent Info & Student Address ----------------
        int section2Y = section1Height + margin;
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(36);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        // Parent Details Heading (Bold & Big)
        canvas.drawText("Parent Details", x+margin+22, y+section2Y, textPaint);

        textPaint.setTextSize(32);
        int lineSpacing = 40;
        int textY = section2Y + lineSpacing;

        // Parent 1
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Name: ", x+margin+30, y+textY, textPaint);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        canvas.drawText(studentModel.getParentOneName(), x+margin + 150, y+textY, textPaint);

        textY += lineSpacing;
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Phone: ", x+margin+30, y+textY, textPaint);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        canvas.drawText(studentModel.getParentOnePhone(), x+margin + 150, y+textY, textPaint);

        textY += lineSpacing + 10; // Extra space

        // Parent 2
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Name: ", x+margin+30, y+textY, textPaint);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        canvas.drawText(studentModel.getParentTwoName(), x+margin + 150, y+textY, textPaint);

        textY += lineSpacing;
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Phone: ", x+margin+30, y+textY, textPaint);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        canvas.drawText(studentModel.getParentTwoPhone(), x+margin + 150, y+textY, textPaint);

        textY += lineSpacing + 20; // Extra space

        // Student Address Heading
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        textPaint.setTextSize(36);
        canvas.drawText("Student Address", x+margin+22, y+textY, textPaint);

        textY += 20;

        // Address Text
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        textPaint.setTextSize(32);
        StaticLayout addressLayout = new StaticLayout(
                studentModel.getHomeAddress(), textPaint, idWidth - (2 * margin), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false
        );

        canvas.save();
        canvas.translate(x+margin+22, y+textY);
        addressLayout.draw(canvas);
        canvas.restore();

// ---------------- SECTION 3: School Address & Principal's Signature ----------------
        int section3Y = section1Height + section2Height + margin;
        textPaint.setTextSize(34);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("School Address", x+margin + 22, y+section3Y, textPaint);

        textPaint.setTextSize(30);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

        StaticLayout schoolAddressLayout = new StaticLayout(
                organizationModel.getOrganizationAddress() + "\nPhone: " + organizationModel.getOrganizationPhone(),
                textPaint, idWidth - 2 * margin, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false
        );

        canvas.save();
        canvas.translate(x+margin + 22, y+section3Y + 20);
        schoolAddressLayout.draw(canvas);
        canvas.restore();

        String signaturePath = organizationModel.getOrganizationPrincipalSignature();
        File signatureFile = new File(signaturePath);
        Bitmap signatureBitmap;

        if (signatureFile.exists()) {
            signatureBitmap = getOptimizedBitmap(signaturePath, 150, 80);
        } else {
            signatureBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
        }

        int signX = idWidth - signatureBitmap.getWidth() - margin;
        int signY = (idHeight - signatureBitmap.getHeight() - margin-80);

// Draw the signature
        canvas.drawBitmap(signatureBitmap, x+signX, y+signY, null);

// Draw "Principal" below the signature
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        textPaint.setTextSize(28);
        canvas.drawText("Principal", x+signX + 120, y+signY + signatureBitmap.getHeight() + 10, textPaint);
    }



    public void generateIDCardsFrontAndBackTogether(ArrayList<StudentModel> studentList) {
        PdfDocument pdfDocument = new PdfDocument();

        double dpi = 300; // High resolution
        int a3Width = (int) ((297 * dpi) / 25.4); // A3 Portrait Width
        int a3Height = (int) ((420 * dpi) / 25.4); // A3 Portrait Height

        // ID Card Size (Converted to Pixels)
        int idWidth = (int) ((57.298 * dpi) / 25.4);
        int idHeight = (int) ((84.348 * dpi) / 25.4);

        int marginX = 2; // Horizontal margin between ID cards
        int marginY = 0; // Vertical margin between ID cards
        int idsPerRow = 5; // 5 ID cards per row
        int idsPerColumn = 5; // 5 rows (25 per page)
        int maxPerPage = idsPerRow * idsPerColumn; // 25 IDs per A3 page

        int totalStudents = studentList.size();
        int totalPages = (int) Math.ceil((double) totalStudents / maxPerPage);

        int studentIndex = 0;

        for (int pageIndex = 0; pageIndex < totalPages; pageIndex++) {
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(a3Width, a3Height, pageIndex + 1).create();
            PdfDocument.Page page = pdfDocument.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            canvas.drawColor(Color.WHITE); // Set White Background

            int countOnPage = 0; // Track how many IDs are on the page

            for (int row = 0; row < idsPerColumn; row++) {
                for (int col = 0; col < idsPerRow; col++) {
                    if (studentIndex >= totalStudents) {
                        break; // Stop if no more students
                    }

                    StudentModel student = studentList.get(studentIndex);

                    int x = marginX + col * (idWidth + marginX); // Column placement
                    int y = marginY + row * (2 * idHeight + marginY); // Row placement (double height for front & back)

                    // Draw Front Side (Top)
                    generateFrontIDCard(canvas, student, x, y, idWidth, idHeight);

                    // Draw Back Side (Below the Front)
                    drawBackCard(canvas, student, x, y + idHeight + marginY, idWidth, idHeight);

                    studentIndex++;
                    countOnPage++;

                    // Stop adding to this page when 25 cards are placed
                    if (countOnPage >= maxPerPage) {
                        break;
                    }
                }
                if (countOnPage >= maxPerPage) {
                    break;
                }
            }

            pdfDocument.finishPage(page);
        }

        // Save PDF
        File directory = new File(context.getFilesDir(), "app/test/all_student");
        if (!directory.exists()) directory.mkdirs();
        File file = new File(directory, "all_students_A3_front_back_together.pdf");
        if (file.exists()) file.delete();

        try {
            FileOutputStream fos = new FileOutputStream(file);
            pdfDocument.writeTo(fos);
            fos.close();
            Toast.makeText(context, "Student ID Cards (Front & Back Together) Generated Successfully!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error Creating ID Cards", Toast.LENGTH_SHORT).show();
        } finally {
            pdfDocument.close();
        }
    }






















}
