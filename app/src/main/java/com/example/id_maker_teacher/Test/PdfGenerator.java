package com.example.id_maker_teacher.Test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;

import com.example.id_maker_teacher.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfGenerator {

    private Context context;

    public PdfGenerator(Context context) {
        this.context = context;
    }

    public void createStudentIdPdf() {
        // A3 Size in Pixels (for 300 DPI)
        int a3Width = 3508;
        int a3Height = 4961;

        // ID Card Size (54mm x 86mm) converted to pixels for 300 DPI
        int idCardWidth = 638;
        int idCardHeight = 1016;

        // Create a new PDF Document
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(a3Width, a3Height, 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(32);

        // Load Background Image from Drawable
        Bitmap bgBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
        Bitmap bgScaled = Bitmap.createScaledBitmap(bgBitmap, idCardWidth, idCardHeight, false);

        // Dummy Student Data (25 students)
        List<Student> students = getDummyStudents();

        // Draw 25 ID Cards (5 columns x 5 rows)
        int startX = 50;  // Starting X Position
        int startY = 50;  // Starting Y Position
        int gapX = 30;    // Horizontal gap
        int gapY = 30;    // Vertical gap
        int colCount = 5; // 5 cards per row

        for (int i = 0; i < students.size(); i++) {
            int row = i / colCount;
            int col = i % colCount;
            int x = startX + (idCardWidth + gapX) * col;
            int y = startY + (idCardHeight + gapY) * row;

            // Draw Background Image
            canvas.drawBitmap(bgScaled, x, y, null);

            // Load Student Image
            Bitmap studentImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_svg_prfile);
            Bitmap scaledStudentImage = Bitmap.createScaledBitmap(studentImage, 200, 200, false);
            canvas.drawBitmap(scaledStudentImage, x + 220, y + 80, null);

            // Draw Text (School Name, Student Name, Class & Division)
            canvas.drawText("ABC International School", x + 80, y + 50, paint);
            canvas.drawText("Name: " + students.get(i).name, x + 80, y + 320, paint);
            canvas.drawText("Class: " + students.get(i).className, x + 80, y + 370, paint);
            canvas.drawText("Div: " + students.get(i).division, x + 80, y + 420, paint);
        }

        // Finish Page and Save PDF
        pdfDocument.finishPage(page);

        // Save PDF to Storage
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Student_IDs.pdf");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            pdfDocument.writeTo(fos);
            pdfDocument.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Dummy Student Data
    private List<Student> getDummyStudents() {
        List<Student> students = new ArrayList<>();
        int[] images = {R.drawable.ic_svg_prfile, R.drawable.ic_svg_prfile, R.drawable.ic_svg_prfile, R.drawable.ic_svg_prfile, R.drawable.ic_svg_prfile};
        for (int i = 1; i <= 25; i++) {
            students.add(new Student("Student " + i, "10", "A", R.drawable.ic_svg_prfile));
        }
        return students;
    }

    // Student Model Class
    class Student {
        String name;
        String className;
        String division;
        int imageResId;

        public Student(String name, String className, String division, int imageResId) {
            this.name = name;
            this.className = className;
            this.division = division;
            this.imageResId = imageResId;
        }
    }
}
