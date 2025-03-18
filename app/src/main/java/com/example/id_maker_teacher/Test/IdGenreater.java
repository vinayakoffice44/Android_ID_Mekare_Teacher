package com.example.id_maker_teacher.Test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.widget.Toast;

import com.example.id_maker_teacher.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

public class IdGenreater {

    Context context;
    TextPaint textPaint;
    public IdGenreater(Context context) {
        this.context = context;
    }


 /*   public void SingeIDGenerate() {
        PdfDocument pdfDocument = new PdfDocument();

        double Width = 57.298;  // width in mm
        double Height = 89.348; // height in mm
        double dpi = 72; // Screen DPI

        int cardWidth = (int) ((Width * dpi) / 25.4);  // Convert mm to pixels
        int cardHeight = (int) ((Height * dpi) / 25.4);

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(cardWidth, cardHeight, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);

        // Load and scale background image
        Bitmap cardBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
        cardBackground = Bitmap.createScaledBitmap(cardBackground, cardWidth, cardHeight, false);

        // Draw image correctly
        canvas.drawBitmap(cardBackground, 0, 0, null);




        pdfDocument.finishPage(page);

        // Save PDF to storage
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "A_single_student_card.pdf");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            pdfDocument.writeTo(fos);
            fos.close();
            Toast.makeText(context, "File Created Successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error Creating File", Toast.LENGTH_SHORT).show();
        }

        pdfDocument.close();
    }*/



/*    public void SingeIDGenerate() {
        PdfDocument pdfDocument = new PdfDocument();

        double Width = 57.298;  // width in mm
        double Height = 89.348; // height in mm
        double dpi = 72; // Screen DPI

        int cardWidth = (int) ((Width * dpi) / 25.4);  // Convert mm to pixels
        int cardHeight = (int) ((Height * dpi) / 25.4);

        // Calculate section heights
        int section1Height = (int) (cardHeight * 0.30);
        int section2Height = (int) (cardHeight * 0.30);
        int section3Height = (int) (cardHeight * 0.40);

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(cardWidth, cardHeight, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        // Background
        Bitmap cardBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
        cardBackground = Bitmap.createScaledBitmap(cardBackground, cardWidth, cardHeight, false);
        canvas.drawBitmap(cardBackground, 0, 0, null);

        // Section 1: School Name
        paint.setColor(Color.BLACK);
        paint.setTextSize(10);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Patkar Varde College is an educational institution", cardWidth / 2, section1Height / 2, paint);

        // Section 2: Student Image

            Bitmap studentImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.sm);
            studentImage = Bitmap.createScaledBitmap(studentImage, section2Height, section2Height, false);
            canvas.drawBitmap(studentImage, (cardWidth - section2Height) / 2, section1Height, null);


        // Section 3: Student Details
        paint.setTextSize(7);
        paint.setTextAlign(Paint.Align.LEFT);
        //paint.setTypeface(Typeface.DEFAULT);
        int textStartY = section1Height + section2Height + 30;

        canvas.drawText("Name: " + "Vinayak Arjun Mhavarkar ahjsdfk aldkjfa;ldfj", 15, textStartY, paint);
        canvas.drawText("Class: " + "class 10", 15, textStartY + 10, paint);
        canvas.drawText("Div: " + "A", 15, textStartY + 20, paint);
        canvas.drawText("DOB: " + "06/03/2002", 15, textStartY + 30, paint);

        pdfDocument.finishPage(page);

        // Save PDF
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "School_ID_Card.pdf");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            pdfDocument.writeTo(fos);
            fos.close();
            Toast.makeText(context, "new ID Card Created Successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error Creating ID Card", Toast.LENGTH_SHORT).show();
        }

        pdfDocument.close();
    }*/










    public void SingeIDGenerate() {
        PdfDocument pdfDocument = new PdfDocument();

        double Width = 57.298;  // width in mm
        double Height = 89.348; // height in mm
        double dpi = 72; // Screen DPI

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

        // Section 1: School Name (Multi-line Support with Color & Address)
        String schoolName = "Patkar Varde  science and ";
        String schoolAddress = "SV Road, Goregaon (W) Mumbai - 400104";

        // Set school name color to RED
        textPaint = new TextPaint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(10);
        textPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        int textWidth = cardWidth - 20;  // Margin 10px on both sides

        canvas.save(); // Save before translate
        canvas.translate(10, 10); // Position for school name
        StaticLayout schoolNameLayout = new StaticLayout(schoolName, textPaint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        schoolNameLayout.draw(canvas);
        canvas.restore(); // Restore after drawing

        // School Address (Smaller text below school name)
        textPaint.setColor(Color.BLACK); // Black color for address
        textPaint.setTextSize(5); // Smaller font size
        canvas.save();
        canvas.translate(10, 10 + schoolNameLayout.getHeight()); // Position below school name
        StaticLayout schoolAddressLayout = new StaticLayout(schoolAddress, textPaint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        schoolAddressLayout.draw(canvas);
        canvas.restore();

        // Section 2: Student Image
        Bitmap studentImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.sm);
        studentImage = Bitmap.createScaledBitmap(studentImage, section2Height, section2Height, false);
        canvas.drawBitmap(studentImage, (cardWidth - section2Height) / 2, section1Height, null);

        // Section 3: Student Details (Multi-line Support for Student Name)
     /*   String studentName =   "Name  : Vinayak Arjun Mhavarkar son of arjun";
        String studentClass =  "Class : 10";
        String division =      "Div   : A";
        String dob =           "DOB   : 06/03/2002";

        textPaint.setTextSize(7);
        textPaint.setTypeface(Typeface.DEFAULT);
        textPaint.setTextAlign(Paint.Align.LEFT);

        int textStartY = section1Height + section2Height + 10;

        canvas.save(); // Save before translate
        canvas.translate(15, textStartY);
        StaticLayout studentNameLayout = new StaticLayout(studentName, textPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        studentNameLayout.draw(canvas);
        canvas.restore(); // Restore after drawing

        // Draw other details below student name
        int extraLinesHeight = studentNameLayout.getHeight() + 10;
        canvas.drawText(studentClass, 15, textStartY + extraLinesHeight, textPaint);
        canvas.drawText(division, 15, textStartY + extraLinesHeight + 10, textPaint);
        canvas.drawText(dob, 15, textStartY + extraLinesHeight + 20, textPaint);*/


        // Section 3: Student Details (Proper Alignment)
        String studentName = "Vinayak Arjun Mhavarkar son of Arjun";
        String studentClass = "10";
        String division = "A";
        String dob = "06/03/2002";

        textPaint.setTextSize(7);
        textPaint.setTypeface(Typeface.DEFAULT);
        textPaint.setTextAlign(Paint.Align.LEFT);

        int textStartY = section1Height + section2Height + 10;
        int labelWidth = 40; // Fixed width for labels

// Function to draw aligned text

// Draw student details with proper alignment
        drawAlignedText(canvas, "Name  :", studentName, 15, textStartY);
        drawAlignedText(canvas, "Class :", studentClass, 15, textStartY + 12);
        drawAlignedText(canvas, "Div   :", division, 15, textStartY + 24);
        drawAlignedText(canvas, "DOB   :", dob, 15, textStartY + 36);


        pdfDocument.finishPage(page);

        // Save PDF
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "School_ID_Card.pdf");

        try {
            FileOutputStream fos = new FileOutputStream(file);
            pdfDocument.writeTo(fos);
            fos.close();
            Toast.makeText(context, "New ID Card Created Successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error Creating ID Card", Toast.LENGTH_SHORT).show();
        }

        pdfDocument.close();
    }


    public void drawAlignedText(Canvas canvas, String label, String value, int x, int y) {
        textPaint.setTextSize(7);
        textPaint.setTypeface(Typeface.DEFAULT);
        textPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(label, x, y, textPaint);
        textPaint.setTextSize(7);
        textPaint.setTypeface(Typeface.DEFAULT);
        textPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(value, x + 25, y, textPaint);
    }






}
