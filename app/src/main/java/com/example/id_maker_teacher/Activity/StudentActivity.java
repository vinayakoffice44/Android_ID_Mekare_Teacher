package com.example.id_maker_teacher.Activity;


import static com.example.id_maker_teacher.Utility.AnimationUtility.dismissLoadingDialog;
import static com.example.id_maker_teacher.Utility.AnimationUtility.showLoadingDialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.id_maker_teacher.Model.ClassModel;
import com.example.id_maker_teacher.Model.StudentModel;
import com.example.id_maker_teacher.R;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import com.example.id_maker_teacher.SQL.DatabaseHelper;
import com.example.id_maker_teacher.Templeate.Design_One;
import com.example.id_maker_teacher.Test.IdGenreater;
import com.example.id_maker_teacher.Utility.ErrorUtility;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class StudentActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private ArrayList<StudentModel> studentList;
    FloatingActionButton floatingActionButton;
    DatabaseHelper databaseHelper;


    String Class,Div,ClassId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        InitTask();
        SetUpIds();
        SetUpAdapter();
        SetUpData();
        AddStudentButtonClick();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        SetUpData();
    }

    private void InitTask() {
        databaseHelper = new DatabaseHelper(this);
        studentList = new ArrayList<>();
        if (getIntent() != null) {
            Class = getIntent().getStringExtra("Class");
            Div = getIntent().getStringExtra("Div");
            ClassId = getIntent().getStringExtra("ClassId");
        }

    }

    private void SetUpIds() {
        recyclerView = findViewById(R.id.student_recycle_view);
        floatingActionButton = findViewById(R.id.student_add_student);

    }

    private void SetUpAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentAdapter = new StudentAdapter(this, studentList);
        recyclerView.setAdapter(studentAdapter);

    }

    private void SetUpData() {
        try{
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            handler.post(() -> showLoadingDialog(StudentActivity.this));
            executor.execute(() -> {
                studentList.clear();
                ArrayList<StudentModel> tempList = new ArrayList<>();
                try {
                    tempList.addAll(databaseHelper.getAllStudentsUsingClassId(ClassId));
                    if(tempList.isEmpty()){

                    }
                } catch (Exception e) {
                    handler.post(() -> {
                        dismissLoadingDialog();
                        new ErrorUtility().SimpleError(StudentActivity.this,"Some Problem Occur for Getting Class Data");
                    });
                    return;
                }
                handler.post(() -> {
                    dismissLoadingDialog();
                    studentList.clear();
                    studentList.addAll(tempList);
                    studentAdapter.notifyDataSetChanged();
                   // new Design_One(StudentActivity.this).generateAllStudentIDs(studentList);
                   // new Design_One(StudentActivity.this).generateBackSide(studentList);
                    new Design_One(StudentActivity.this).generateIDCardsFrontAndBackTogether(studentList);
                });
            });

        }catch (Error e){
            new ErrorUtility().SimpleError(StudentActivity.this,"Some Problem Occur for Getting Class Data Not compited Process");
        }
    }

    private void AddStudentButtonClick() {
        floatingActionButton.setOnClickListener(V->{
            showAddStudentDialog();
        });
    }
    public void showAddStudentDialog() {
        AlertDialog.Builder builder= new MaterialAlertDialogBuilder(StudentActivity.this);
        // Create Dialog

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_sudent_option, null); // Ensure this XML exists
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // Transparent background

        // Find Views
        MaterialTextView btnCancel = dialogView.findViewById(R.id.dialog_add_student_option_cancel);
        MaterialCardView btnExcel = dialogView.findViewById(R.id.dialog_add_student_option_excel);
        MaterialCardView btnManual = dialogView.findViewById(R.id.dialog_add_student_option_manually);

        // Set Click Listeners
        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnExcel.setOnClickListener(v -> {
            Intent intent = new Intent(StudentActivity.this, AddStudentUsingExcleFile.class);
            intent.putExtra("ClassId", ClassId);
            startActivity(intent);
            dialog.dismiss();

            dialog.dismiss();
        });

        btnManual.setOnClickListener(v -> {
            Intent intent = new Intent(StudentActivity.this, AddStudentManuallyActivity.class);
            intent.putExtra("ClassId", ClassId);
            intent.putExtra("UpdateFlag", false);
            startActivity(intent);
            dialog.dismiss();
        });

        // Show Dialog
        dialog.show();
    }

    public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

        private Context context;
        private List<StudentModel> studentList;

        public StudentAdapter(Context context, List<StudentModel> studentList) {
            this.context = context;
            this.studentList = studentList;
        }

        @NonNull
        @Override
        public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false);
            return new StudentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
            StudentModel student = studentList.get(position);

            holder.studentName.setText(student.getStudentFullName());
            holder.studentRollNo.setText(student.getStudentRollNumber());
            holder.studentClass.setText(student.getStudentClass());
            holder.studentDiv.setText(student.getDiv());
            holder.studentBloodGroup.setText(student.getBloodGroup());
            holder.studentDob.setText(student.getDateOfBirth());

            // Load Profile Image using Glide (if available)
            Glide.with(context)
                    .load(student.getProfileImage()) // URL or drawable
                    .placeholder(R.drawable.ic_svg_profile_two) // Placeholder image
                    .error(R.drawable.ic_svg_profile_two)
                    .skipMemoryCache(true)  // Disable memory cache
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.studentImage);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(StudentActivity.this, AddStudentManuallyActivity.class);
                intent.putExtra("StudentId", String.valueOf(student.getStudentId()));
                intent.putExtra("UpdateFlag", true);
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return studentList.size();
        }

        public  class StudentViewHolder extends RecyclerView.ViewHolder {
            ImageView studentImage;
            MaterialTextView studentName, studentRollNo, studentClass, studentDiv, studentBloodGroup, studentDob;

            public StudentViewHolder(@NonNull View itemView) {
                super(itemView);
                studentImage = itemView.findViewById(R.id.item_student_image);
                studentName = itemView.findViewById(R.id.item_student_name);
                studentRollNo = itemView.findViewById(R.id.item_student_roll_no);
                studentClass = itemView.findViewById(R.id.item_student_class);
                studentDiv = itemView.findViewById(R.id.item_student_div);
                studentBloodGroup = itemView.findViewById(R.id.item_student_blood_group);
                studentDob = itemView.findViewById(R.id.item_student_dob);
            }
        }
    }



}