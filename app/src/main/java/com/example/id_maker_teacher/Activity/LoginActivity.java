package com.example.id_maker_teacher.Activity;

import static com.example.id_maker_teacher.Utility.AnimationUtility.dismissLoadingDialog;
import static com.example.id_maker_teacher.Utility.AnimationUtility.showLoadingDialog;
import static com.example.id_maker_teacher.Utility.ValidationUtility.isValidEmail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.id_maker_teacher.Model.OrganizationModel;
import com.example.id_maker_teacher.R;
import com.example.id_maker_teacher.Utility.ErrorUtility;
import com.example.id_maker_teacher.Utility.SharedPreferencesHelper;
import com.example.id_maker_teacher.Utility.ValidationUtility;
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
            String _email = email.getText().toString();
            String _password = password.getText().toString();
            if (_email.isEmpty() ) {
                emailLayout.setError("Email is required");
                email.requestFocus();
                return;
            }
            if (!isValidEmail(_email)) {
                emailLayout.setError("Enter The Valid Email");
                email.requestFocus();
                return;
            }
            if (_password.isEmpty()) {
                passwordLayout.setError("Password is required");
                password.requestFocus();
                return;
            }
            showLoadingDialog(LoginActivity.this);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(_email.equals("apv@gmail.com") && _password.equals("123456")){
                        SharedPreferencesHelper preferences = new SharedPreferencesHelper(LoginActivity.this);
                        OrganizationModel organization = new OrganizationModel();
                        organization.setOrganizationId(1);
                        organization.setOrganizationName("St. Joseph’s International Academy for Advanced Learning");
                        organization.setOrganizationAddress("Plot No. 56, Near Lotus Chowk, Sector 12, New Delhi - 110001, India");
                        organization.setOrganizationWebsite("https://www.stjosephsacademy.edu.in");
                        organization.setOrganizationPhone("98765 43210");
                        organization.setOrganizationEmail("contact@JIALschool.com");
                        organization.setOrganizationLogoPath("https://png.pngtree.com/png-vector/20230415/ourmid/pngtree-school-logo-design-template-vector-png-image_6705854.png");
                        organization.setOrganizationInstructionTitle("Welcome to ABC School");
                        organization.setOrganizationInstructionDescription("Follow the school rules and guidelines.");
                        organization.setOrganizationPrincipalSignature("https://www.cpbc.com/uploads/2020/11/Dustin-Johnson-first-name-only-signature-blue.png");
                        organization.setOrganizationPassword("securepassword");
                        organization.setOrganizationTemplateId("1");
                        organization.setTemplatePhotoShapeId("1");
                        preferences.saveOrganization(organization);
                        preferences.setLoginStatus(true);
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    }else {
                        new ErrorUtility().SimpleError(LoginActivity.this,"invalid Email Id or Password");
                    }
                    dismissLoadingDialog();
                }
            },3000);


        });

    }
    private void CreateAccountClick() {
        signUpLink.setOnClickListener(v -> {

        });
    }

}