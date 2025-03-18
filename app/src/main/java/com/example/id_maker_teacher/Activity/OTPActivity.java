package com.example.id_maker_teacher.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.id_maker_teacher.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class OTPActivity extends AppCompatActivity {

    Animation sideUpIn;
    TextView otpTitle, otpSubTitle, otpResendTitle, otpTimerTitle;
    TextInputEditText otpDigitOne, otpDigitTwo, otpDigitThree, otpDigitFour;
    MaterialButton summitedButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otpactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        InitTask();
        SetUpIds();
        ApplyAnimation();
        SummitedButtonClick();
        ResentButtonClick();
        EditEmailClick();
    }
    private void InitTask() {
        sideUpIn = AnimationUtils.loadAnimation(this, R.anim.slide_up_in);
    }

    private void SetUpIds() {
        otpTitle = findViewById(R.id.otp_title);
        otpSubTitle = findViewById(R.id.otp_sub_title);
        otpResendTitle = findViewById(R.id.otp_resend_title);
        otpTimerTitle = findViewById(R.id.otp_timer_title);
        summitedButton = findViewById(R.id.otp_button);

        otpDigitOne = findViewById(R.id.otp_digit_1);
        otpDigitTwo = findViewById(R.id.otp_digit_2);
        otpDigitThree = findViewById(R.id.otp_digit_3);
        otpDigitFour = findViewById(R.id.otp_digit_4);


    }

    private void ApplyAnimation() {
        otpTitle.startAnimation(sideUpIn);
        otpSubTitle.startAnimation(sideUpIn);
        otpResendTitle.startAnimation(sideUpIn);
        otpTimerTitle.startAnimation(sideUpIn);
        summitedButton.startAnimation(sideUpIn);
        otpDigitOne.startAnimation(sideUpIn);
        otpDigitTwo.startAnimation(sideUpIn);
        otpDigitThree.startAnimation(sideUpIn);
        otpDigitFour.startAnimation(sideUpIn);


    }

    private void SummitedButtonClick() {
        summitedButton.setOnClickListener(v -> {
            startActivity(new Intent(OTPActivity.this, DashboardActivity.class));
            finish();
        });
    }
    private void ResentButtonClick() {
        otpTimerTitle.setOnClickListener(V->{

        });

    }

    private void EditEmailClick() {
        otpSubTitle.setOnClickListener(V->{

        });

    }
}