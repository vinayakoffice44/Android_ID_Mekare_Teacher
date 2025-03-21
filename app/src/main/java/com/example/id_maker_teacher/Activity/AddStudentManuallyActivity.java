package com.example.id_maker_teacher.Activity;

import static com.example.id_maker_teacher.Utility.AnimationUtility.showMessageDialog;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.id_maker_teacher.Model.ClassModel;
import com.example.id_maker_teacher.Model.StudentModel;
import com.example.id_maker_teacher.R;
import com.example.id_maker_teacher.SQL.DatabaseHelper;
import com.example.id_maker_teacher.Utility.AnimationUtility;
import com.example.id_maker_teacher.Utility.ErrorUtility;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddStudentManuallyActivity extends AppCompatActivity {



    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_GALLERY = 2;
    TextInputLayout studentNameLayout, studentRollNumberLayout, studentBloodGroupLayout, studentAddressLayout, studentDateOfBirthLayout,
             studentParentOneNameLayout, studentParentOnePhoneLayout,  studentParentTwoNameLayout,
            studentParentTwoPhoneLayout;
    TextInputEditText studentName, studentRollNumber, studentAddress, studentDateOfBirth,
            studentParentOneName,studentParentTowName,studentParentOnePhone,studentParentTwoPhone;

    AutoCompleteTextView studentBloodGroup;
    ArrayAdapter<String> bloodGroupAdapter;
    ArrayList<String> bloodGroupList;

    MaterialButton SaveButton,DeleteButton;

    boolean UpdateFlag = false;
    String ClassId;
    ClassModel classModel;
    Animation animation;
    ImageView studentProfileImage;

    StudentModel studentModel;
    public File photoFile;

    String studentImagePath= null;

    DatabaseHelper databaseHelper;

    Toolbar toolbar;

    MaterialButton viewIdFrontFace,viewIdBackFace;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student_manually);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        requestPermissions();
        InitTask();
        SetUpIds();
        ToolbarSetUp();
        SetBloodGroup();
        SetUpAnimation();
        SetUpCalender();
        SetUpPickImage();
        SetSubmitButton();
        SetUpdateDate();
        DeleteButtonClick();
    }




    private void SetUpdateDate() {
        if(UpdateFlag){

            studentImagePath = studentModel.getProfileImage();
            studentName.setText(String.valueOf(studentModel.getStudentFullName()));
            studentRollNumber.setText(studentModel.getStudentRollNumber());
            studentAddress.setText(studentModel.getHomeAddress());
            studentDateOfBirth.setText(studentModel.getDateOfBirth());
            studentParentOneName.setText(studentModel.getParentOneName());
            studentParentTowName.setText(studentModel.getParentTwoName());
            studentParentOnePhone.setText(studentModel.getParentOnePhone());
            studentParentTwoPhone.setText(studentModel.getParentTwoPhone());
            studentBloodGroup.setText(studentModel.getBloodGroup(),false);
            showImage(studentImagePath);
            SaveButton.setText("Update");

            DeleteButton.setVisibility(View.VISIBLE);
            viewIdFrontFace.setVisibility(View.VISIBLE);
            viewIdBackFace.setVisibility(View.VISIBLE);
            toolbar.setTitle("Update Student");

        }

    }

    private void DeleteButtonClick() {
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(AddStudentManuallyActivity.this);
                builder.setTitle("Confirm Delete");
                builder.setMessage("Are you sure you want to delete?");

                // OK button
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseHelper.deleteStudent(studentModel.getStudentId());
                        deleteStudentImage(studentModel.getProfileImage());
                        showMessageDialog(AddStudentManuallyActivity.this, "200", "Student Deleted Successfully", new AnimationUtility.OnOkClickListener() {
                            @Override
                            public void onOkClicked() {
                                finish();
                            }
                        });
                    }
                });

                // Cancel button
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Close the dialog
                    }
                });

                // Show the dialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
    public boolean deleteStudentImage(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    private void InitTask() {
        databaseHelper = new DatabaseHelper(this);
        bloodGroupList = new ArrayList<>();
        studentModel = new StudentModel();
        animation = AnimationUtils.loadAnimation(this, R.anim.slide_up_in);
        if(getIntent()!=null){
            UpdateFlag = getIntent().getBooleanExtra("UpdateFlag",false);

            if(UpdateFlag){
                String  id  = getIntent().getStringExtra("StudentId");
                try{
                    studentModel = databaseHelper.getStudentById(Integer.parseInt(id));
                    classModel = databaseHelper.getClassDetailsById(Integer.parseInt(studentModel.getClass_Id()));

                }catch (Error e){
                    new ErrorUtility().SimpleError(AddStudentManuallyActivity.this,"Some Problem Occur for Getting Class Data in Add Student Manually Process");
                }
            }else {
                ClassId = getIntent().getStringExtra("ClassId");
                //classModel = new ClassModel();
                try{
                    classModel = databaseHelper.getClassDetailsById(Integer.parseInt(ClassId));
                    studentModel.setClass_Id(String.valueOf(classModel.getClassId()));
                    studentModel.setStudentClass(classModel.getClassName());
                    studentModel.setDiv(classModel.getDiv());
                }catch (Error e){
                    new ErrorUtility().SimpleError(AddStudentManuallyActivity.this,"Some Problem Occur for Getting Class Data in Add Student Manually Process");
                }

            }
        }

    }

    private void SetUpIds() {
        studentNameLayout = findViewById(R.id.add_student_manually_student_name_layout);
        studentRollNumberLayout = findViewById(R.id.add_student_manually_student_roll_number_layout);
        studentBloodGroupLayout = findViewById(R.id.add_student_manually_student_blood_group_layout);
        studentAddressLayout = findViewById(R.id.add_student_manually_student_address_layout);
        studentDateOfBirthLayout = findViewById(R.id.add_student_manually_student_dob_layout);
        studentParentOneNameLayout = findViewById(R.id.add_student_manually_parent_one_name_layout);
        studentParentOnePhoneLayout = findViewById(R.id.add_student_manually_parent_one_number_layout);
        studentParentTwoNameLayout = findViewById(R.id.add_student_manually_parent_two_name_layout);
        studentParentTwoPhoneLayout = findViewById(R.id.add_student_manually_parent_two_number_layout);

        studentName = findViewById(R.id.add_student_manually_student_name);
        studentRollNumber = findViewById(R.id.add_student_manually_student_roll_number);
        studentAddress = findViewById(R.id.add_student_manually_student_address);
        studentDateOfBirth = findViewById(R.id.add_student_manually_student_dob);
        studentParentOneName = findViewById(R.id.add_student_manually_parent_one_name);
        studentParentTowName = findViewById(R.id.add_student_manually_parent_two_name);
        studentParentOnePhone = findViewById(R.id.add_student_manually_parent_one_number);
        studentParentTwoPhone = findViewById(R.id.add_student_manually_parent_two_number);
        studentBloodGroup = findViewById(R.id.add_student_manually_student_blood_group);
        studentProfileImage = findViewById(R.id.add_student_manually_student_image);

        SaveButton = findViewById(R.id.add_student_manually_save_button);
        DeleteButton = findViewById(R.id.add_student_manually_delete_button);
        viewIdFrontFace = findViewById(R.id.add_student_manually_view_id_card_front_button);
        viewIdBackFace = findViewById(R.id.add_student_manually_view_id_card_back_button);
        toolbar = findViewById(R.id.add_student_manually_toolbar);





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

    private void SetBloodGroup() {
        bloodGroupList.add("A+");
        bloodGroupList.add("B+");
        bloodGroupList.add("O+");
        bloodGroupList.add("AB+");
        bloodGroupList.add("A-");
        bloodGroupList.add("B-");
        bloodGroupList.add("O-");
        bloodGroupList.add("AB-");
        bloodGroupList.add("-");
        bloodGroupAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item , bloodGroupList);
        studentBloodGroup.setAdapter(bloodGroupAdapter);


    }

    private void SetUpAnimation() {
        studentNameLayout.startAnimation(animation);
        studentRollNumberLayout.startAnimation(animation);
        studentBloodGroupLayout.startAnimation(animation);
        studentAddressLayout.startAnimation(animation);
        studentDateOfBirthLayout.startAnimation(animation);
        studentParentOneNameLayout.startAnimation(animation);
        studentParentOnePhoneLayout.startAnimation(animation);
        studentParentTwoNameLayout.startAnimation(animation);
        studentParentTwoPhoneLayout.startAnimation(animation);
        studentProfileImage.startAnimation(animation);
        SaveButton.startAnimation(animation);

    }

    private void SetUpCalender() {
        studentDateOfBirth.setOnClickListener(V->{
            showDatePicker();
        });
    }
    private void showDatePicker() {
        // Get current date
        final Calendar calendar = Calendar.getInstance();
        if (!studentDateOfBirth.getText().toString().isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                calendar.setTime(dateFormat.parse(studentDateOfBirth.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            // Create Calendar instance for selected date
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(selectedYear, selectedMonth, selectedDay);

            // Format date as "12 May 2015"
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            String formattedDate = dateFormat.format(selectedCalendar.getTime());

            // Set formatted date to the EditText
            studentDateOfBirth.setText(formattedDate);
            int age = calculateAge(selectedYear, selectedMonth, selectedDay);
            studentDateOfBirthLayout.setHelperText("Student Age: " + age + " years");
            studentDateOfBirthLayout.setHelperTextColor(ColorStateList.valueOf(Color.BLACK));
        }, year, month, day);

        // Restrict selection to past dates only
        final Calendar calendar2 = Calendar.getInstance();

        datePickerDialog.getDatePicker().setMaxDate(calendar2.getTimeInMillis());

        // Show the DatePickerDialog
        datePickerDialog.show();
    }
    private int calculateAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        dob.set(year, month, day);

        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        // Check if birthday has occurred this year
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }

    private void SetUpPickImage() {
        studentProfileImage.setOnClickListener(V->{
            studentImagePath = null;
            showImagePickerDialog();
        });
    }
    private void requestPermissions() {
        requestPermissions(new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        }, 100);
    }



    private void showImagePickerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddStudentManuallyActivity.this);
        builder.setTitle("Select Image Source");

        String[] options = {"Camera"};
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                openCamera();
            }
        });

        builder.show();
    }

    // 📷 Open Camera
    public void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = createImageFile();
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.id_maker_teacher.fileprovider", photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CAMERA);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 📁 Open Gallery
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_GALLERY);
    }

    // 🎯 Handle Image Selection
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bitmap selectedImage = null;

            if (requestCode == REQUEST_CAMERA) {
                if (photoFile != null && photoFile.exists()) {
                    selectedImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    Uri sourceUri = Uri.fromFile(photoFile);
                    Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped_passport.jpg"));

                    // Start UCrop for cropping the image to passport size
                    UCrop.of(sourceUri, destinationUri)
                            .withAspectRatio(35, 45) // Set aspect ratio for passport size (35mm x 45mm)
                            .withMaxResultSize(350, 450) // Adjust resolution if needed
                            .start(this);
                } else {
                    showError("Image Not Found, Select Again");
                    return;
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {
                Uri resultUri = UCrop.getOutput(data);
                if (resultUri != null) {
                    saveAndDisplayImage(resultUri);
                }
            }
            else if (requestCode == REQUEST_GALLERY) {
                if (data != null && data.getData() != null) {
                    Uri imageUri = data.getData();
                    try {
                        selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    showError("Error: No image selected");
                    return;
                }
            }

           /* if (selectedImage != null) {
                // Compress & Save the image
                studentImagePath = saveCompressedImage(selectedImage);
                // Show the saved image using Glide
                showImage(studentImagePath);
            } else {
                showError("Error: Failed to process image");
            }*/
        }
    }
    private void saveAndDisplayImage(Uri imageUri) {
        try {
            // Create the directory if it does not exist
            File directory = new File(getFilesDir(), "app/images/" + classModel.getClassId() + "/student_images");
            if (!directory.exists()) {
                directory.mkdirs();  // Create directory and subdirectories if needed
            }

            // Create a new file inside the directory for the image
            File imageFile = new File(directory, "image.jpg");

            // Convert Uri to Bitmap
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

            // Compress the bitmap to 50KB
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int quality = 100; // Start with max quality
            do {
                outputStream.reset(); // Clear previous data
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                quality -= 5; // Reduce quality by 5 each iteration
            } while (outputStream.toByteArray().length > 50 * 1024 && quality > 10); // Stop when size is <= 50KB or quality is very low

            // Write compressed image to file
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            fileOutputStream.write(outputStream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
            studentImagePath = imageFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            showImage(studentImagePath);
        }
    }






    private void showImage(String imagePath) {
        Glide.with(this)
                .load(new File(imagePath))
                .placeholder(R.drawable.ic_svg_profile_two)
                .error(R.drawable.ic_svg_profile_two)
                .skipMemoryCache(true)  // Disable memory cache
                .diskCacheStrategy(DiskCacheStrategy.NONE)  // Disable disk cache
                .into(studentProfileImage);
    }


    private void showError(String message) {
        new ErrorUtility().SimpleError(this, message);
    }


    private File createImageFile() throws IOException {
        File storageDir = new File(getFilesDir(), "app/images/"+classModel.getClassId() + "/student_images");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        return new File(storageDir, "image.jpg");
    }


    private void SetSubmitButton() {
        SaveButton.setOnClickListener(V->{
            String _studentName = studentName.getText().toString();
            String _studentRollNumber = studentRollNumber.getText().toString();
            String _studentBloodGroup = studentBloodGroup.getText().toString();
            String _studentAddress = studentAddress.getText().toString();
            String _studentDateOfBirth = studentDateOfBirth.getText().toString();
            String _studentParentOneName = studentParentOneName.getText().toString();
            String _studentParentOnePhone = studentParentOnePhone.getText().toString();
            String _studentParentTwoName = studentParentTowName.getText().toString();
            String _studentParentTwoPhone = studentParentTwoPhone.getText().toString();

            if(studentImagePath==null){
                new ErrorUtility().SimpleError(AddStudentManuallyActivity.this,"Student Image Is Required");
                studentNameLayout.requestFocus();
                return;
            }
            if(_studentName.isEmpty() ){
                studentNameLayout.setError("Please Enter Student Name");
                studentNameLayout.requestFocus();
                return;
            }
            if(_studentRollNumber.isEmpty() ){
              studentRollNumber.setError("Please Enter Student Roll Number");
              studentRollNumber.requestFocus();

            }
            if(_studentBloodGroup.isEmpty() ){
                studentBloodGroupLayout.setError("Please Enter Student Blood Group");
                studentBloodGroupLayout.requestFocus();
                return;
            }
            if(_studentAddress.isEmpty() ){
                studentAddressLayout.setError("Please Enter Student Address");
                studentAddressLayout.requestFocus();
                return;
            }
            if(_studentDateOfBirth.isEmpty() ){
                studentDateOfBirthLayout.setError("Please Enter Student Date Of Birth");
                studentDateOfBirthLayout.requestFocus();
                return;
            }
            if(_studentParentOneName.isEmpty() ){
                studentParentOneNameLayout.setError("Please Enter Student Parent One Name");
                studentParentOneNameLayout.requestFocus();
                return;
            }
            if(_studentParentOnePhone.isEmpty() ){
                studentParentOnePhoneLayout.setError("Please Enter Student Parent One Phone");
                studentParentOnePhoneLayout.requestFocus();
                return;
            }
            if(_studentParentOnePhone.length()!= 10){
                studentParentOnePhoneLayout.setError("Please Enter Valid Student Parent Phone number");
                studentParentOnePhoneLayout.requestFocus();
                return;
            }
            if(_studentParentTwoName.isEmpty() ){
                studentParentTwoNameLayout.setError("Please Enter Student Parent Two Name");
                studentParentTwoNameLayout.requestFocus();
                return;
            }
            if(_studentParentTwoPhone.isEmpty() ){
                studentParentTwoPhoneLayout.setError("Please Enter Student Parent Two Phone");
                studentParentTwoPhoneLayout.requestFocus();
                return;
            }
            if(_studentParentTwoPhone.length()!= 10){
                studentParentTwoPhoneLayout.setError("Please Enter Valid Student Parent Phone number");
                studentParentTwoPhoneLayout.requestFocus();
                return;
            }

            studentModel.setStudentFullName(_studentName);
            studentModel.setStudentRollNumber(_studentRollNumber);
            studentModel.setStudentClass(classModel.getClassName());
            studentModel.setDateOfBirth(_studentDateOfBirth);
            studentModel.setDiv(classModel.getDiv());
            studentModel.setBloodGroup(_studentBloodGroup);
            studentModel.setProfileImage("");
            studentModel.setParentOneName(_studentParentOneName);
            studentModel.setParentOnePhone(_studentParentOnePhone);
            studentModel.setParentTwoName(_studentParentTwoName);
            studentModel.setParentTwoPhone(_studentParentTwoPhone);
            studentModel.setHomeAddress(_studentAddress);
            studentModel.setClass_Id(String.valueOf(classModel.getClassId()));

            if(UpdateFlag){
                databaseHelper.updateStudent(studentModel);

                if( studentImagePath.contains("image.jpg")){
                    String newPath = renameFileWithExtension(studentImagePath, String.valueOf(studentModel.getStudentId()));
                    if(newPath!= null){
                        databaseHelper.updateStudentImage(newPath,String.valueOf(studentModel.getStudentId()));
                        finish();
                    }else {
                        new ErrorUtility().SimpleError(this,"Problem Occur in Adding Student photo");
                    }
                }else {
                    databaseHelper.updateStudentImage(studentImagePath,String.valueOf(studentModel.getStudentId()));

                }

                finish();



            }else {

                long result = databaseHelper.addStudent(studentModel);
                if(result!= -1){
                    String newPath = renameFileWithExtension(studentImagePath, String.valueOf(result));
                    if(newPath!= null){
                        databaseHelper.updateStudentImage(newPath,String.valueOf(result));
                        finish();
                    }else {
                        new ErrorUtility().SimpleError(this,"Problem Occur in Adding Student photo");
                    }

                }else {
                    new ErrorUtility().SimpleError(this,"Problem Occur in Adding Student\nTry Again Latter");
                }
            }












        });


    }

    public String renameFileWithExtension(String filePath, String newFileName) {
        File oldFile = new File(filePath);

        // Check if the file exists
        if (!oldFile.exists()) {
            System.out.println("File does not exist.");
            return null;
        }

        // Get the file extension
        String fileName = oldFile.getName();
        int dotIndex = fileName.lastIndexOf(".");
        String extension = (dotIndex > 0) ? fileName.substring(dotIndex) : ""; // Keep extension if exists

        // Create new file object with the new name + original extension
        File newFile = new File(oldFile.getParent(), newFileName + extension);

        // Rename the file
        if (oldFile.renameTo(newFile)) {
            System.out.println("File renamed successfully: " + newFile.getAbsolutePath());
            return newFile.getAbsolutePath(); // Return the new path
        } else {
            System.out.println("Failed to rename file.");
            return null; // Return null if renaming fails
        }
    }


}