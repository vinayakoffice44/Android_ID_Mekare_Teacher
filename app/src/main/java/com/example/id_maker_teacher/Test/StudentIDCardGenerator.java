package com.example.id_maker_teacher.Test;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.example.id_maker_teacher.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class StudentIDCardGenerator {

    public static void generateIDCard(Context context) {
        int a3Width = 297;  // A3 width in mm
        int a3Height = 420; // A3 height in mm
        int dpi = 3;
        int widthPx = a3Width * dpi;
        int heightPx = a3Height * dpi;

        PdfDocument pdfDocument = new PdfDocument();
        new FetchStudentImageTask(pdfDocument, context, widthPx, heightPx).execute("https://drive.google.com/file/d/11HQ3_nfLfmyMoCbvSt_vhuEnPK-jjlJ7/view?usp=sharing");
    }

    private static class FetchStudentImageTask extends AsyncTask<String, Void, Bitmap> {
        private PdfDocument pdfDocument;
        private Context context;
        private int widthPx, heightPx;

        public FetchStudentImageTask(PdfDocument pdfDocument, Context context, int widthPx, int heightPx) {
            this.pdfDocument = pdfDocument;
            this.context = context;
            this.widthPx = widthPx;
            this.heightPx = heightPx;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap bitmap;
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();

                bitmap = BitmapFactory.decodeStream(input);
                /*if (bitmap == null) {
                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_svg_prfile);
                }*/
                return bitmap;

            } catch (IOException e) {
                Toast.makeText(context, "Error "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return null;
            }catch (Error e) {
                Toast.makeText(context, "Error "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap studentImage) {
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(widthPx, heightPx, 1).create();
            PdfDocument.Page page = pdfDocument.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            Paint paint = new Paint();

            // Load Background Image
            /*Bitmap background = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
            background = Bitmap.createScaledBitmap(background, widthPx, heightPx, false);
            canvas.drawBitmap(background, 0, 0, paint);*/

            // Standard ID Card Size
            int cardWidth = 54 * 3;  // Convert mm to pixels
            int cardHeight = 86 * 3;
            int schoolNameHeight = (int) (cardHeight * 0.20);
            int studentImageHeight = (int) (cardHeight * 0.30);
            int studentInfoHeight = (int) (cardHeight * 0.50);
            int cols = 5;  // 5 cards across
            int rows = 4;  // 4 cards down
            int xOffset = 20;
            int yOffset = 20;

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    int x = xOffset + col * (cardWidth + 10);
                    int y = yOffset + row * (cardHeight + 10);
                    paint.setStyle(Paint.Style.STROKE);
                    paint.setColor(Color.BLACK);
                    paint.setStrokeWidth(2);
                    Bitmap cardBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.bg);
                    cardBackground = Bitmap.createScaledBitmap(cardBackground, cardWidth, cardHeight, false);
                    canvas.drawBitmap(cardBackground, x, y, paint);
                    canvas.drawRect(x, y, x + cardWidth, y + cardHeight, paint);

                    // Draw Student Image inside each card
                    if (studentImage != null) {
                        Bitmap resizedImage = Bitmap.createScaledBitmap(studentImage, 40 * 3, 40 * 3, false);
                        canvas.drawBitmap(resizedImage, x + (cardWidth - 40 * 3) / 2, y + schoolNameHeight, paint);
                    }

                    // Draw Student Information
                    paint.setTextSize(7 *2);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(android.graphics.Color.RED);

                    String schoolName = "Patkar Varde College is an educational institution";
                    // Draw Student Information
                    paint.setTextSize(7);
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(android.graphics.Color.RED);
                    paint.setColor(android.graphics.Color.RED);

                    int maxWidth = cardWidth - 20; // Allow some padding
                    if (paint.measureText(schoolName) > maxWidth) {
                        String[] words = schoolName.split(" ");
                        StringBuilder line = new StringBuilder();
                        int lineHeight = y + schoolNameHeight / 2;
                        for (String word : words) {
                            if (paint.measureText(line + word) > maxWidth) {
                                canvas.drawText(line.toString(), x + 10, lineHeight, paint);
                                line = new StringBuilder(word + " ");
                                lineHeight += 30; // Move to the next line
                            } else {
                                line.append(word).append(" ");
                            }
                        }
                        if (!line.toString().isEmpty()) {
                            canvas.drawText(line.toString(), x + 10, lineHeight, paint);
                        }
                    } else {
                        canvas.drawText(schoolName, x + 10, y + 50, paint);
                    }
                    paint.setColor(android.graphics.Color.BLACK);
                    paint.setColor(android.graphics.Color.BLACK);
                    canvas.drawText("STUDENT NAME: John Doe", x + 10, y + schoolNameHeight + studentImageHeight + 10, paint);
                    canvas.drawText("DOB: 01-01-2010", x + 10, y + schoolNameHeight + studentImageHeight + 40, paint);
                    canvas.drawText("Class: 5th", x + 10, y + schoolNameHeight + studentImageHeight + 70, paint);
                    canvas.drawText("Div: A", x + 10, y + schoolNameHeight + studentImageHeight + 100, paint);
                }
            }

            pdfDocument.finishPage(page);

            // Save PDF
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Student_ID_Cards_A3.pdf");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                pdfDocument.writeTo(fos);
                fos.close();
                Toast.makeText(context, "File Create Successfully", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            pdfDocument.close();
        }
    }
}



/*    private static Bitmap getStudentImageFromFTP(String urlStr) {
        try {
            URL url = new URL(urlStr);
            return BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }*/
