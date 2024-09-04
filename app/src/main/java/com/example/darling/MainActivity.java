package com.example.darling;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.darling.Helpers.SPref;
import com.example.darling.Music.MusicFragment;

public class MainActivity extends AppCompatActivity {
    FrameLayout fl_Main;
    MusicFragment musicFragment = new MusicFragment();
    SharedPreferences sPref_settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sPref_settings = getSharedPreferences("settings", MODE_PRIVATE);
        SPref.setAllData(
                sPref_settings.getString("name", null),
                sPref_settings.getString("username", null),
                sPref_settings.getString("email", null),
                sPref_settings.getString("description", null),
                "ssilka"
        );

        fl_Main = findViewById(R.id.mainFrameLayout);
        setDefFragment(musicFragment);
    }

    private void setDefFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrameLayout, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}