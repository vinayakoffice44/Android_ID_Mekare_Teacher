package com.example.id_maker_teacher.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserPreferences {
    private  String PREF_NAME = "UserPrefs";
    private  String KEY_TOKEN = "token";
    private  String KEY_USER_ID = "userId";
    private  String KEY_FIRST_NAME = "firstName";
    private  String KEY_LAST_NAME = "lastName";
    private  String KEY_EMAIL = "email";
    private  String KEY_PHONE = "phone";
    private  String KEY_PROFILE = "profile";
    private  String KEY_BRANCH_ID = "branchId";
    private  String KEY_LOGIN_TIME = "loginTime";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public UserPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // ✅ Save User Data
    public void saveUserData(String token, int userId, String firstName, String lastName,
                             String email, String phone, String profile, int branchId) {
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        editor.putString(KEY_TOKEN, token);
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_FIRST_NAME, firstName);
        editor.putString(KEY_LAST_NAME, lastName);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_PROFILE, profile);
        editor.putInt(KEY_BRANCH_ID, branchId);
        editor.putString(KEY_LOGIN_TIME, currentTime); // ✅ Store login time
        editor.apply(); // Save changes asynchronously
    }

    // ✅ Individual Setter Methods
    public void setToken(String token) {
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    public void setUserId(int userId) {
        editor.putInt(KEY_USER_ID, userId);
        editor.apply();
    }

    public void setFirstName(String firstName) {
        editor.putString(KEY_FIRST_NAME, firstName);
        editor.apply();
    }

    public void setLastName(String lastName) {
        editor.putString(KEY_LAST_NAME, lastName);
        editor.apply();
    }

    public void setEmail(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public void setPhone(String phone) {
        editor.putString(KEY_PHONE, phone);
        editor.apply();
    }

    public void setProfile(String profile) {
        editor.putString(KEY_PROFILE, profile);
        editor.apply();
    }

    public void setBranchId(int branchId) {
        editor.putInt(KEY_BRANCH_ID, branchId);
        editor.apply();
    }


    // ✅ Get User Data
    public String getToken() { return sharedPreferences.getString(KEY_TOKEN, ""); }
    public int getUserId() { return sharedPreferences.getInt(KEY_USER_ID, -1); }
    public String getFirstName() { return sharedPreferences.getString(KEY_FIRST_NAME, ""); }
    public String getLastName() { return sharedPreferences.getString(KEY_LAST_NAME, ""); }
    public String getEmail() { return sharedPreferences.getString(KEY_EMAIL, ""); }
    public String getPhone() { return sharedPreferences.getString(KEY_PHONE, ""); }
    public String getProfile() { return sharedPreferences.getString(KEY_PROFILE, ""); }
    public int getBranchId() { return sharedPreferences.getInt(KEY_BRANCH_ID, -1); }
    public String getLoginTime() { return sharedPreferences.getString(KEY_LOGIN_TIME, ""); } // ✅ Get login time

    // ✅ Clear User Data (For Logout)

    public boolean isLoginValid(Context context) {
        String loginDateStr = getLoginTime();
        if (loginDateStr.isEmpty()) return false;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date loginDate = sdf.parse(loginDateStr);
            Date currentDate = new Date();

            long diff = currentDate.getTime() - loginDate.getTime();
            long days = diff / (1000 * 60 * 60 * 24);

            return days <= 30; // ✅ Returns true if within 30 days, false otherwise

        } catch (ParseException e) {
            new ErrorUtility().SimpleError(context,"Unable Verify User. Please Login Again!");
            return false;
        }
    }
    public void clearUserData() {
        editor.clear();
        editor.apply();
    }
}

