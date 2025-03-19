package com.example.id_maker_teacher.Fragment;

import static com.example.id_maker_teacher.Utility.AnimationUtility.dismissLoadingDialog;
import static com.example.id_maker_teacher.Utility.AnimationUtility.showLoadingDialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.id_maker_teacher.Activity.LoginActivity;
import com.example.id_maker_teacher.Model.ClassModel;
import com.example.id_maker_teacher.R;
import com.example.id_maker_teacher.SQL.DatabaseHelper;
import com.example.id_maker_teacher.Utility.ErrorUtility;
import com.example.id_maker_teacher.Utility.SharedPreferencesHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ClassFragment extends Fragment {

   View view;
    private RecyclerView classRecyclerView;
    private ClassAdapter classAdapter;
    private ArrayList<ClassModel> classList;
    private FloatingActionButton addClassButton;
    SharedPreferencesHelper sharedPreferencesHelper;
    DatabaseHelper databaseHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_class, container, false);
        InitTask();
        setUpIds();
        setUpAdapter();
        GetData();
        ClickFloatingActionButton();
        return view;

    }

    private void InitTask() {
        classList = new ArrayList<>();
        sharedPreferencesHelper = new SharedPreferencesHelper(getContext());
        databaseHelper = new DatabaseHelper(getContext());
    }

    private void setUpIds() {
        classRecyclerView = view.findViewById(R.id.class_recycle_view);
        addClassButton = view.findViewById(R.id.class_add_class);
    }

    private void setUpAdapter() {
        classRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        classAdapter = new ClassAdapter(classList);
        classRecyclerView.setAdapter(classAdapter);
    }
    private void ClickFloatingActionButton() {
        addClassButton.setOnClickListener(V->{
            showAddClassDialog();
        });

    }


    private void GetData() {

        try{
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            handler.post(() -> showLoadingDialog(getContext()));
            executor.execute(() -> {
                classList.clear();
                ArrayList<ClassModel> tempList = new ArrayList<>();
                try {
                    tempList.addAll(databaseHelper.getAllClasses());
                } catch (Exception e) {
                    handler.post(() -> {
                       dismissLoadingDialog();
                       new ErrorUtility().SimpleError(getContext(),"Some Problem Occur for Getting Class Data");
                    });
                    return;
                }
                handler.post(() -> {
                    dismissLoadingDialog();
                    classList.clear();
                    classList.addAll(tempList);
                    classAdapter.notifyDataSetChanged();
                });
            });

        }catch (Error e){
            new ErrorUtility().SimpleError(getContext(),"Some Problem Occur for Getting Class Data Not compited Process");
        }


    }
    private void showAddClassDialog() {
        AlertDialog.Builder builder = new MaterialAlertDialogBuilder(getContext());
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
        ArrayAdapter<String> classNameAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line,ClassNameList );
        ClassDropdown.setAdapter(classNameAdapter);
        ArrayList<String> DivList = new ArrayList<>();
        DivList.add("A");
        DivList.add("B");
        DivList.add("C");
        ArrayAdapter<String> divAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line,DivList );
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


            try{

                ClassModel model = new ClassModel(
                        TeacherNameText,
                        TeacherNumberText,
                        ClassDropdownText,
                        DivDropdownText,
                        sharedPreferencesHelper.getOrganization().getOrganizationId(),
                        sharedPreferencesHelper.getOrganization().getOrganizationName(),
                        sharedPreferencesHelper.getOrganization().getOrganizationAddress(),
                        sharedPreferencesHelper.getOrganization().getOrganizationWebsite(),
                        sharedPreferencesHelper.getOrganization().getOrganizationPhone(),
                        sharedPreferencesHelper.getOrganization().getOrganizationEmail(),
                        sharedPreferencesHelper.getOrganization().getOrganizationLogoPath(),
                        sharedPreferencesHelper.getOrganization().getOrganizationInstructionTitle(),
                        sharedPreferencesHelper.getOrganization().getOrganizationInstructionDescription(),
                        sharedPreferencesHelper.getOrganization().getOrganizationPrincipalSignature()

                );
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    Handler handler = new Handler(Looper.getMainLooper());
                   handler.post(() -> showLoadingDialog(getContext()));
                    executor.execute(() -> {
                        try {
                            if(databaseHelper.addClass(model)!=-1){
                                GetData();
                            }

                        } catch (Exception e) {
                            handler.post(() -> {
                                dismissLoadingDialog();
                                new ErrorUtility().SimpleError(getContext(),"Some Problem Occur for Getting Class Data");
                            });
                            return;
                        }
                        handler.post(() -> {
                            dismissLoadingDialog();
                        });
                    });
            }catch (Error e){
                new ErrorUtility().SimpleError(getContext(),"Some Problem Occur for adding Class Not compiled Process");
            }

            dialog.dismiss();


        });



        dialog.show();
    }


    public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

        private ArrayList<ClassModel> classList;

        public ClassAdapter(ArrayList<ClassModel> classList) {
            this.classList = classList;
        }

        @NonNull
        @Override
        public ClassAdapter.ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false);
            return new ClassAdapter.ClassViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ClassAdapter.ClassViewHolder holder, int position) {
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
    }



}