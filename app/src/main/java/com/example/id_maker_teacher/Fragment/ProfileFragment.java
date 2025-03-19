package com.example.id_maker_teacher.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.id_maker_teacher.Activity.HomePage;
import com.example.id_maker_teacher.Activity.LoginActivity;
import com.example.id_maker_teacher.Model.OrganizationModel;
import com.example.id_maker_teacher.R;
import com.example.id_maker_teacher.Utility.SharedPreferencesHelper;
import com.example.id_maker_teacher.Utility.UserPreferences;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ProfileFragment extends Fragment {

    View view;
    ImageView logo;
    TextInputEditText name,address,phone,website;
    TextInputLayout nameLayout,addressLayout,phoneLayout,websiteLayout;

    MaterialButton LogoutButton;
    SharedPreferencesHelper sharedPreferencesHelper ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        InitTask();
        SetUpIdes();
        SetUpData();
        logoutButtonClick();
        return view;
    }



    private void InitTask() {
        sharedPreferencesHelper = new SharedPreferencesHelper(getContext());
    }

    private void SetUpIdes() {
        logo = view.findViewById(R.id.profile_image);
        name = view.findViewById(R.id.profile_organization_name);
        address = view.findViewById(R.id.profile_organization_address);
        phone = view.findViewById(R.id.profile_organization_phone);
        website = view.findViewById(R.id.profile_organization_website);
        nameLayout = view.findViewById(R.id.profile_organization_name_layout);
        addressLayout = view.findViewById(R.id.profile_organization_address_layout);
        phoneLayout = view.findViewById(R.id.profile_organization_phone_layout);
        websiteLayout = view.findViewById(R.id.profile_organization_website_layout);
        LogoutButton = view.findViewById(R.id.fragment_profile_logout_button);

    }
    private void SetUpData() {
        OrganizationModel organizationModel = sharedPreferencesHelper.getOrganization();
        name.setText(organizationModel.getOrganizationName());
        address.setText(organizationModel.getOrganizationAddress());
        phone.setText(organizationModel.getOrganizationPhone());
        website.setText(organizationModel.getOrganizationWebsite());

        Glide.with(getContext())
                .load(/*getString(R.string.Base_Url)+*/organizationModel.getOrganizationLogoPath())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_svg_profile_two) // Show default image if loading fails
                        .error(R.drawable.ic_svg_profile_two) // Show error image if URL is broken
                        .circleCrop()) // ✅ Make it Circular
                .into(logo);
    }
    private void logoutButtonClick() {
        LogoutButton.setOnClickListener(V->{
            new SharedPreferencesHelper(getContext()).clearAllData();
            startActivity(new Intent(getContext(), HomePage.class));
        });
    }

}