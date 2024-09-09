package com.example.darling;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.darling.Settings.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {
    LinearLayout ll_back_to_login;
    FrameLayout fl_settings;
    SettingsFragment settingsFragment = new SettingsFragment();
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_settings);

        ll_back_to_login = findViewById(R.id.ll_back_to_main);

        ll_back_to_login.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.alpha_with_100, R.anim.right_out);
        });

        fl_settings = findViewById(R.id.fl_settings);
        setDefFragment(settingsFragment);
    }
    private void setDefFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_settings, fragment);
        ft.commit();
    }
}
