package com.example.id_maker_teacher.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.id_maker_teacher.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    public Animation slideIn,slideOut;
    public TextView loginTitle,forgetPassword,signUpLink,moreOption;
    public TextInputEditText email,password;
    public TextInputLayout emailLayout,passwordLayout;
    public MaterialButton loginButton;
    public ImageView googleIcon,facebookIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        InitTask();
        SetUpIds();
        ApplyAnimation();
        LoginButtonClick();
        CreateAccountClick();

    }



    private void InitTask() {
        //Animation init
        slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_up_in);
        slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_left_out);
    }

    private void SetUpIds() {
        loginTitle = findViewById(R.id.login_title);
        email = findViewById(R.id.login_email);
        emailLayout = findViewById(R.id.login_email_layout);
        password = findViewById(R.id.login_password);
        passwordLayout = findViewById(R.id.login_password_layout);
        loginButton = findViewById(R.id.login_button);
        forgetPassword = findViewById(R.id.login_forget_password);
        signUpLink = findViewById(R.id.login_sign_up_link);
 /*       moreOption = findViewById(R.id.login_more_option_title);
        googleIcon = findViewById(R.id.login_google_icon);
        facebookIcon = findViewById(R.id.login_facebook_icon);*/
    }

    private void ApplyAnimation() {
        // Apply animation
        loginTitle.startAnimation(slideIn);
        email.startAnimation(slideIn);
        emailLayout.startAnimation(slideIn);
        password.startAnimation(slideIn);
        passwordLayout.startAnimation(slideIn);
        loginButton.startAnimation(slideIn);
        forgetPassword.startAnimation(slideIn);
        signUpLink.startAnimation(slideIn);
        /*moreOption.startAnimation(slideIn);
        googleIcon.startAnimation(slideIn);
        facebookIcon.startAnimation(slideIn);*/
    }

    private void LoginButtonClick() {
        loginButton.setOnClickListener(v -> {
            startActivity(new Intent(this, OTPActivity.class));
        });

    }
    private void CreateAccountClick() {
        signUpLink.setOnClickListener(v -> {

        });
    }

}