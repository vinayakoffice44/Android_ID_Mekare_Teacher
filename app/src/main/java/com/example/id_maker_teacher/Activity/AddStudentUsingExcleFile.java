package com.example.id_maker_teacher.Activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.id_maker_teacher.Model.ClassModel;
import com.example.id_maker_teacher.Model.StudentModel;
import com.example.id_maker_teacher.R;
import com.example.id_maker_teacher.SQL.DatabaseHelper;
import com.google.android.material.button.MaterialButton;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class AddStudentUsingExcleFile extends AppCompatActivity {

    private static final int PICK_EXCEL_FILE = 1;
    private static final String TAG = "AddStudentUsingExcleFile";
    MaterialButton uploadButton;

    ClassModel classModel;
    DatabaseHelper databaseHelper;

    ArrayList<StudentModel> studentList;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student_using_excle_file);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        InitTask();
        SetUpIds();
        ToolbarSetUp();
        UploadButtonClick();
        RequestPermission();

    }

    private void InitTask() {
        databaseHelper = new DatabaseHelper(this);
        classModel = new ClassModel();
        String  id  = getIntent().getStringExtra("ClassId");
        classModel = databaseHelper.getClassDetailsById(Integer.parseInt(id));
    }

    private void SetUpIds() {
        uploadButton = findViewById(R.id.add_student_excel_upload);
        toolbar = findViewById(R.id.add_student_excel_toolbar);


    }
    private void ToolbarSetUp() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show back button
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed(); // Handle back button click
        });
    }

    private void UploadButtonClick() {
        uploadButton.setOnClickListener(v -> openFileChooser());
    }

    private void RequestPermission() {
        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");  // Accept all file types

        // Allow only Excel files (.xls, .xlsx)
        String[] mimeTypes = {
                "application/vnd.ms-excel", // .xls
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" // .xlsx
        };
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

        startActivityForResult(Intent.createChooser(intent, "Select Excel File"), PICK_EXCEL_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_EXCEL_FILE && resultCode == RESULT_OK && data != null) {
            Uri fileUri = data.getData();
            if (fileUri != null) {
                readExcelFile(fileUri);
            }
        }
    }

    private void readExcelFile(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            //SQLiteDatabase db = new DatabaseHelper(this).getWritableDatabase();
            studentList = new ArrayList<>();


            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                if (isRowEmpty(row)) {
                    Log.d(TAG, "Empty row found at row " + row.getRowNum() + ", stopping processing.");
                    break; // Stop the loop if an empty row is found
                }

                String rollNumber = String.valueOf((int)row.getCell(0).getNumericCellValue());
                String studentName = row.getCell(1).getStringCellValue();
                String dob = ConvertDate(row.getCell(2));
                String bloodGroup = row.getCell(3).getStringCellValue();
                String parentOneName = row.getCell(4).getStringCellValue();
                String ParentOnePhone = String.valueOf((long)row.getCell(5).getNumericCellValue());
                String ParentTwoName = row.getCell(6).getStringCellValue();
                String ParentTwoPhone = String.valueOf((long)row.getCell(7).getNumericCellValue());
                String address = row.getCell(8).getStringCellValue();
                StudentModel studentModel= new StudentModel();

                studentModel.setStudentRollNumber(rollNumber);
                studentModel.setStudentFullName(studentName);
                studentModel.setDateOfBirth(dob);
                studentModel.setBloodGroup(bloodGroup);
                studentModel.setParentOneName(parentOneName);
                studentModel.setParentOnePhone(ParentOnePhone);
                studentModel.setParentTwoName(ParentTwoName);
                studentModel.setParentTwoPhone(ParentTwoPhone);
                studentModel.setHomeAddress(address);

                studentModel.setClass_Id(String.valueOf(classModel.getClassId()));
                studentModel.setStudentClass(classModel.getClassName());
                studentModel.setDiv(classModel.getDiv());

                studentModel.setProfileImage("");
                studentModel.setParentOneTitle("");
                studentModel.setParentTwoTitle("");

                studentList.add(studentModel);

            }


            workbook.close();
            inputStream.close();
            if(!studentList.isEmpty()){
                databaseHelper.addStudentList(studentList);
                finish();
            }


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"Errororor:   "+ e.getMessage());

        }
    }

    private boolean isRowEmpty(Row row) {
        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false; // Row is not empty
            }
        }
        return true; // Row is empty
    }
    public String ConvertDate(Cell cell){
        if (DateUtil.isCellDateFormatted(cell)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.US);
            return sdf.format(cell.getDateCellValue());  // Convert date format
        }
        return String.valueOf((long) cell.getNumericCellValue());  // Convert numeric to string
    }

}