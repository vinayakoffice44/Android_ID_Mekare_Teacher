package com.example.id_maker_teacher.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.id_maker_teacher.Model.OrganizationModel;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    private static final String PREF_NAME = "OrganizationPrefs";

    // Organization Data Keys
    private static final String KEY_ID = "organization_id";
    private static final String KEY_NAME = "organization_name";
    private static final String KEY_ADDRESS = "organization_address";
    private static final String KEY_WEBSITE = "organization_website";
    private static final String KEY_PHONE = "organization_phone";
    private static final String KEY_EMAIL = "organization_email";
    private static final String KEY_LOGO_PATH = "organization_logo_path";
    private static final String KEY_INSTRUCTION_TITLE = "organization_instruction_title";
    private static final String KEY_INSTRUCTION_DESC = "organization_instruction_desc";
    private static final String KEY_PRINCIPAL_SIGNATURE = "organization_principal_signature";
    private static final String KEY_PASSWORD = "organization_password";

    // Login Status Key
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Save organization data
    public void saveOrganization(OrganizationModel organization) {
        editor.putInt(KEY_ID, organization.getOrganizationId());
        editor.putString(KEY_NAME, organization.getOrganizationName());
        editor.putString(KEY_ADDRESS, organization.getOrganizationAddress());
        editor.putString(KEY_WEBSITE, organization.getOrganizationWebsite());
        editor.putString(KEY_PHONE, organization.getOrganizationPhone());
        editor.putString(KEY_EMAIL, organization.getOrganizationEmail());
        editor.putString(KEY_LOGO_PATH, organization.getOrganizationLogoPath());
        editor.putString(KEY_INSTRUCTION_TITLE, organization.getOrganizationInstructionTitle());
        editor.putString(KEY_INSTRUCTION_DESC, organization.getOrganizationInstructionDescription());
        editor.putString(KEY_PRINCIPAL_SIGNATURE, organization.getOrganizationPrincipalSignature());
        editor.putString(KEY_PASSWORD, organization.getOrganizationPassword());
        editor.apply();
    }

    // Get organization data
    public OrganizationModel getOrganization() {
        OrganizationModel organization = new OrganizationModel();
        organization.setOrganizationId(sharedPreferences.getInt(KEY_ID, 0));
        organization.setOrganizationName(sharedPreferences.getString(KEY_NAME, ""));
        organization.setOrganizationAddress(sharedPreferences.getString(KEY_ADDRESS, ""));
        organization.setOrganizationWebsite(sharedPreferences.getString(KEY_WEBSITE, ""));
        organization.setOrganizationPhone(sharedPreferences.getString(KEY_PHONE, ""));
        organization.setOrganizationEmail(sharedPreferences.getString(KEY_EMAIL, ""));
        organization.setOrganizationLogoPath(sharedPreferences.getString(KEY_LOGO_PATH, ""));
        organization.setOrganizationInstructionTitle(sharedPreferences.getString(KEY_INSTRUCTION_TITLE, ""));
        organization.setOrganizationInstructionDescription(sharedPreferences.getString(KEY_INSTRUCTION_DESC, ""));
        organization.setOrganizationPrincipalSignature(sharedPreferences.getString(KEY_PRINCIPAL_SIGNATURE, ""));
        organization.setOrganizationPassword(sharedPreferences.getString(KEY_PASSWORD, ""));
        return organization;
    }

    // Set login status
    public void setLoginStatus(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    // Get login status
    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    // Clear all stored data
    public void clearAllData() {
        editor.clear();
        editor.apply();
    }
}
