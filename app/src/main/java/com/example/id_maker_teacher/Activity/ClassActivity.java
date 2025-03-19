package com.example.id_maker_teacher.Activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.id_maker_teacher.Model.ClassModel;
import com.example.id_maker_teacher.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ClassActivity extends AppCompatActivity {


/*    private RecyclerView classRecyclerView;
    private ClassAdapter classAdapter;
    private ArrayList<ClassModel> classList;
    private FloatingActionButton addClassButton;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_class);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
 /*       InitTask();
        setUpIds();
        setUpAdapter();
        GetData();
        ClickFloatingActionButton();*/
    }


 /*   private void InitTask() {
        classList = new ArrayList<>();
    }

    private void setUpIds() {
        classRecyclerView = findViewById(R.id.class_recycle_view);
        addClassButton = findViewById(R.id.class_add_class);
    }

    private void setUpAdapter() {
        classRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        classAdapter = new ClassAdapter(classList);
        classRecyclerView.setAdapter(classAdapter);
    }
    private void ClickFloatingActionButton() {
        addClassButton.setOnClickListener(V->{
            showAddClassDialog();
        });

    }


    private void GetData() {

    }
    private void showAddClassDialog() {
        AlertDialog.Builder builder = new MaterialAlertDialogBuilder(ClassActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_class, null); // Ensure this XML exists
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // Transparent background


        MaterialTextView cancelButton = dialogView.findViewById(R.id.item_add_class_cancel_button);
        TextInputEditText TeacherName = dialogView.findViewById(R.id.item_add_class_teacher_nam);
        TextInputEditText TeacherNumber = dialogView.findViewById(R.id.item_add_class_teacher_number);
        AutoCompleteTextView ClassDropdown = dialogView.findViewById(R.id.item_add_class_class_dropdown);
        AutoCompleteTextView DivDropdown = dialogView.findViewById(R.id.item_add_class_div_dropdown);
        MaterialButton AddClassButton = dialogView.findViewById(R.id.item_add_class_add_class);

        ArrayList<String> ClassNameList = new ArrayList<>();
        ClassNameList.add("Class 1");
        ClassNameList.add("Class 2");
        ClassNameList.add("Class 3");
        ClassNameList.add("Class 4");
        ClassNameList.add("Class 5");
        ClassNameList.add("Class 6");
        ClassNameList.add("Class 7");
        ClassNameList.add("Class 8");
        ClassNameList.add("Class 9");
        ClassNameList.add("Class 10");
        ArrayAdapter<String> classNameAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,ClassNameList );
        ClassDropdown.setAdapter(classNameAdapter);
        ArrayList<String> DivList = new ArrayList<>();
        DivList.add("A");
        DivList.add("B");
        DivList.add("C");
        ArrayAdapter<String> divAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,DivList );
        DivDropdown.setAdapter(divAdapter);

        cancelButton.setOnClickListener(V->{
            dialog.dismiss();
        });
        AddClassButton.setOnClickListener(V->{
            String TeacherNameText = TeacherName.getText().toString();
            String TeacherNumberText = TeacherNumber.getText().toString();
            String ClassDropdownText = ClassDropdown.getText().toString();
            String DivDropdownText = DivDropdown.getText().toString();
            if(TeacherNameText.isEmpty()){
                TeacherName.setError("Enter Teacher Name");
                TeacherName.requestFocus();
                return;
            }
            if(TeacherNumberText.isEmpty()){
                TeacherNumber.setError("Enter Teacher Number");
                TeacherNumber.requestFocus();
                return;
            }
            if(TeacherNumberText.length()!=10){
                TeacherNumber.setError("Enter Valid Number");
                TeacherNumber.requestFocus();
                return;
            }
            if(ClassDropdownText.isEmpty()){
                ClassDropdown.setError("Select Class");
                ClassDropdown.requestFocus();
                return;
            }
            if(DivDropdownText.isEmpty()){
                DivDropdown.setError("Select Div");
                DivDropdown.requestFocus();
                return;
            }
            classList.add(new ClassModel(ClassDropdownText, DivDropdownText));
            classAdapter.notifyDataSetChanged();
            dialog.dismiss();


        });




        // Create and show the dialog

        dialog.show();
    }


    public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

        private ArrayList<ClassModel> classList;

        public ClassAdapter(ArrayList<ClassModel> classList) {
            this.classList = classList;
        }

        @NonNull
        @Override
        public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false);
            return new ClassViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
            ClassModel classModel = classList.get(position);
            holder.classNameText.setText(classModel.getClassName());
            holder.divText.setText("DIV : " + classModel.getDiv());
        }

        @Override
        public int getItemCount() {
            return classList.size();
        }

        public  class ClassViewHolder extends RecyclerView.ViewHolder {
            MaterialTextView classNameText, divText;

            public ClassViewHolder(@NonNull View itemView) {
                super(itemView);
                classNameText = itemView.findViewById(R.id.item_class_name);
                divText = itemView.findViewById(R.id.item_class_div);

            }
        }
    }*/

}