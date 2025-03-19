package com.example.id_maker_teacher.Utility;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;


public class MessageUtitlity {
    Context context;
    public MessageUtitlity(Context context) {
        this.context = context;
    }


    private boolean shouldReopenDialog = true; // ✅ Track if the dialog should reopen

    public void showReportDialog(String phoneNumber, String email, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Send Report to Head Office");
        builder.setMessage("Choose how you want to send the report:");

        // 🔹 Send via WhatsApp
        builder.setPositiveButton("Send via WhatsApp", (dialog, which) -> {
            sendWhatsAppMessage(phoneNumber, message);
        });

        // 🔹 Send via Email
        builder.setNegativeButton("Send via Email", (dialog, which) -> {
            sendEmail(email, "Booking", message);
            dialog.dismiss(); // ✅ Dismiss the dialog but allow reopening
        });

        // 🔹 Close Button
        builder.setNeutralButton("Close", (dialog, which) -> {

            dialog.dismiss();
        });

        // Make dialog not cancelable
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();



        dialog.show();
    }


    public void sendWhatsAppMessage(String phoneNumber, String message) {
        try {
            // Ensure phone number is in correct format (remove spaces and "+" sign)
            phoneNumber = phoneNumber.replace("+", "").replace(" ", "");

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://wa.me/" + phoneNumber + "?text=" + Uri.encode(message)));

            // ✅ No need to setPackage("com.whatsapp") - It works without this!
            context.startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    public void sendEmail(String recipient, String subject, String body) {
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("message/rfc822"); // ✅ Ensures only email apps are listed
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{recipient}); // ✅ Set recipient
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);  // ✅ Set subject
            emailIntent.putExtra(Intent.EXTRA_TEXT, body);        // ✅ Set body

            context.startActivity(Intent.createChooser(emailIntent, "Choose an email app"));

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isWhatsAppInstalled() {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            return true; // WhatsApp Installed
        } catch (PackageManager.NameNotFoundException e) {
            try {
                packageManager.getPackageInfo("com.whatsapp.w4b", PackageManager.GET_ACTIVITIES);
                return true; // WhatsApp Business Installed
            } catch (PackageManager.NameNotFoundException ex) {
                return false; // Neither Installed
            }
        }
    }

    public  String generateBookingMessage(
            String name, String vehicleModel, String colorModel,
            String productAmount, String paymentType, String downPayment,
            String emiAmount, String emiDuration,
            String bankName, String accountNo
    ) {
        String message = "🚗 *Vehicle Booking Details* 🚗\n\n" +
                "👤 *Customer Name:* " + name + "\n" +
                "🚘 *Vehicle Model:* " + vehicleModel + "\n" +
                "🎨 *Color:* " + colorModel + "\n" +
                "💰 *Actual Amount:* ₹" + productAmount + "\n\n";

        if (paymentType.equalsIgnoreCase("EMI")) {
            message += "🛒 *Payment Type:* EMI\n" +
                    "💵 *Down Payment:* ₹" + downPayment + "\n" +
                    "📆 *EMI Duration:* " + emiDuration + " months\n" +
                    "💳 *EMI Per Month:* ₹" + emiAmount + "\n\n" +
                    "🏦 *Bank Name:* " + bankName + "\n" +
                    "🔢 *Account No:* " + accountNo + "\n\n";
        } else {
            message += "🛒 *Payment Type:* Full Payment\n";
        }

        message += "✅ *Booking Confirmed!*\n📞 Please contact us for further details.\n";

        return message;
    }


}
