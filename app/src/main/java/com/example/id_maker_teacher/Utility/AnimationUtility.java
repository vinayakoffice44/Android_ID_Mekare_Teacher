package com.example.id_maker_teacher.Utility;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

import com.example.id_maker_teacher.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textview.MaterialTextView;

public class AnimationUtility {

    private static Dialog loadingDialog;
    private static Dialog MessageDialog;

    // Interface for handling OK button click
    public interface OnOkClickListener {
        void onOkClicked();
    }

    //loading animation
    public static void showLoadingDialog(Context context) {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            return; // Prevent multiple dialogs from opening
        }

        // Inflate Custom Layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_animation, null);

        // Find Lottie Animation View
        LottieAnimationView animationView = view.findViewById(R.id.lottieAnimation);

        // Create Material Dialog
        loadingDialog = new MaterialAlertDialogBuilder(context)
                .setView(view)
                .setCancelable(false) // Prevent dialog from closing on outside touch
                .create();

        // Set transparent background
        if (loadingDialog.getWindow() != null) {
            loadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }


        // Show Dialog
        loadingDialog.show();
    }
    public static void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null; // Prevent memory leak
        }
    }


    //message animation
    public static void showMessageDialog(Context context,String code ,String message,OnOkClickListener listener) {
        // Inflate Custom Layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_message_animation, null);

        // Initialize Views
        LottieAnimationView animationView = view.findViewById(R.id.lottieAnimation);
        MaterialTextView messageText = view.findViewById(R.id.dialogMessage);
        MaterialButton okButton = view.findViewById(R.id.okButton);

        if(code.equals("201") || code.equals("200")){
            animationView.setAnimation(R.raw.done);
        } else if (code.equals("500")) {
            animationView.setAnimation(R.raw.not_done);
        } else {
            animationView.setAnimation(R.raw.wor);
        }
        // Set Message
        messageText.setText(message);
        // Create Dialog
        MessageDialog = new MaterialAlertDialogBuilder(context)
                .setView(view)
                .setCancelable(false) // Prevent closing when clicking outside
                .create();

        // Set Transparent Background
        if (MessageDialog.getWindow() != null) {
            MessageDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        // OK Button Click Listener
        okButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onOkClicked(); // Call the interface method
            }else {
                dismissMessageDialog();
            }

        });

        // Show Dialog
        MessageDialog.show();
    }
    public static void dismissMessageDialog() {
        if (MessageDialog != null && MessageDialog.isShowing()) {
            MessageDialog.dismiss();
            MessageDialog = null; // Prevent memory leak
        }
    }



}
