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



}
