package com.example.id_maker_teacher.Utility;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadUtility {
    Context context;

    public DownloadUtility(Context context) {
        this.context = context;
    }

    public String downloadLogo(String imageUrl) {
        return downloadImage(imageUrl, "app/images/organization/logo", "logo.png");
    }
    public String downloadSignature(String imageUrl) {
        return downloadImage(imageUrl, "app/images/organization/signature", "signature.png");
    }

    private String downloadImage(String imageUrl, String folderPath, String fileName) {
        try {
            // Create directory
            File directory = new File(context.getFilesDir(), folderPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Create file
            File imageFile = new File(directory, fileName);

            // Download image
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setConnectTimeout(5000); // Timeout 5 seconds
            connection.setReadTimeout(5000);
            connection.connect();

            InputStream inputStream = connection.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(imageFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();

            // Return file path
            return imageFile.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null if download fails
        }
    }



}
