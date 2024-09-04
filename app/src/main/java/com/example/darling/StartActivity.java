package com.example.darling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {
    SharedPreferences sPref_settings;
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_start);

        sPref_settings = getSharedPreferences("settings", MODE_PRIVATE);
        String username = sPref_settings.getString("username", null);
        Intent intent;

        if (username != null && !username.isEmpty())
            intent = new Intent(getApplicationContext(), MainActivity.class);
        else
            intent = new Intent(getApplicationContext(), AuthActivity.class);

        startActivity(intent);
    }
}
