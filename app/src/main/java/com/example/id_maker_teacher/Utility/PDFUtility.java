package com.example.id_maker_teacher.Utility;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.ParcelFileDescriptor;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.id_maker_teacher.R;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PDFUtility {
    public  void showPdfDialog(Context context, String pdfPath) {
        // Create dialog
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_pdf_viewer);
        dialog.setCancelable(false);

        // Find views
        ImageView pdfImageView = dialog.findViewById(R.id.dialog_pdf_viewer_ImageView);
        Button btnClose = dialog.findViewById(R.id.dialog_pdf_viewer_close_button);

        // Load and render the PDF
        File file = new File(pdfPath);
        if (!file.exists()) {
            Toast.makeText(context, "Error: PDF file not found", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
            PdfRenderer pdfRenderer = new PdfRenderer(parcelFileDescriptor);
            PdfRenderer.Page page = pdfRenderer.openPage(0);

            Bitmap bitmap = Bitmap.createBitmap(page.getWidth(), page.getHeight(), Bitmap.Config.ARGB_8888);
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            pdfImageView.setImageBitmap(bitmap);

            page.close();
            pdfRenderer.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed to open PDF", Toast.LENGTH_SHORT).show();
        }

        // Close button action
        btnClose.setOnClickListener(view -> dialog.dismiss());

        // Show dialog
        dialog.show();
    }

    public static MultipartBody.Part prepareFilePart(String partName, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return null; // Skip if file does not exist

        RequestBody requestFile = RequestBody.create(MediaType.parse("application/pdf"), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }


    public static MultipartBody.Part prepareImagePart(String partName, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    // ✅ Function to determine the MIME type based on the file extension
    private static String getImageMimeType(String filePath) {
        if (filePath.endsWith(".png")) return "image/png";
        if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg")) return "image/jpeg";
        if (filePath.endsWith(".webp")) return "image/webp";

        return "image/jpeg"; // Default to JPEG
    }




}
