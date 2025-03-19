package com.example.id_maker_teacher.Activity;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.id_maker_teacher.Fragment.ClassFragment;
import com.example.id_maker_teacher.Fragment.ProfileFragment;
import com.example.id_maker_teacher.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class DashboardActivity extends AppCompatActivity {

    FrameLayout container;
    ChipNavigationBar menu;
    Fragment fragment = new Fragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.dashboard_container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        container = findViewById(R.id.fragment_container);
        menu = findViewById(R.id.bottom_menu);
        menu.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {

                if(id == R.id.register){
                    fragment = new ClassFragment();
                } else if (id == R.id.profile) {
                    fragment =new ProfileFragment();
                }
                if (fragment != null) {
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null) // Fix: Ensures fragment is updated properly
                            .commit();
                }

            }
        });
        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ClassFragment())
                    .commit();
            menu.setItemSelected(R.id.register, true); // Fix: Ensure default selection is applied
        }

    }
}