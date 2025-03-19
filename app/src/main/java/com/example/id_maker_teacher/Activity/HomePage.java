package com.example.id_maker_teacher.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.id_maker_teacher.R;
import com.example.id_maker_teacher.Utility.SharedPreferencesHelper;
import com.google.android.material.button.MaterialButton;

public class HomePage extends AppCompatActivity {

    MaterialButton getStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SetupIds();
        GetStartedButtonClick();
    }
    private void SetupIds() {
        getStartedButton = findViewById(R.id.home_get_started_button);

    }
    private void GetStartedButtonClick() {
        getStartedButton.setOnClickListener(v -> {
            SharedPreferencesHelper preferences = new SharedPreferencesHelper(this);
            boolean isLoggedIn = preferences.isLoggedIn();
            if(isLoggedIn){
                startActivity(new Intent(this, DashboardActivity.class));
            }else {
                startActivity(new Intent(this, LoginActivity.class));
            }


        });
    }
}