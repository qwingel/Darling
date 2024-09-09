package com.example.darling;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.darling.Helpers.RequestToServe;
import com.example.darling.Helpers.SPref;
import com.example.darling.MainFragments.PageFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    FrameLayout fl_Main;
    PageFragment musicFragment = new PageFragment();
    SharedPreferences sPref, ssPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onVersionResponse();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sPref = getSharedPreferences("account", MODE_PRIVATE);
        SPref.setAllData(
                sPref.getString("name", null),
                sPref.getString("username", null),
                sPref.getString("email", null),
                sPref.getString("description", null),
                "ssilka"
        );

        ssPref = getSharedPreferences("settings", MODE_PRIVATE);
        SPref.setAllSettings(
                ssPref.getBoolean("isOnline", true),
                ssPref.getInt("screen", 0)
        );

        fl_Main = findViewById(R.id.mainFrameLayout);
        setDefFragment(musicFragment);
    }

    private void setDefFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrameLayout, fragment);
        ft.commit();
    }

    public void onVersionResponse(){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(RequestToServe.SQUrl)
                .build();

        RequestToServe.UserService userService = retrofit.create(RequestToServe.UserService.class);
        Call<RequestToServe.ResponseVersionMessage> checkVersion = userService.version();
        checkVersion.enqueue(new Callback<RequestToServe.ResponseVersionMessage>() {
            @Override
            public void onResponse(@NonNull Call<RequestToServe.ResponseVersionMessage> call, @NonNull Response<RequestToServe.ResponseVersionMessage> response) {
                if(response.body() != null){
                    System.out.println("Responsell");
                    float v = Float.parseFloat(response.body().version);
                    if(v > RequestToServe.SQVersion)
                        Toast.makeText(
                                getApplicationContext(),
                                "Обновите приложение до версии " + v,
                                Toast.LENGTH_LONG
                        ).show();
                }
            }

            @Override
            public void onFailure(Call<RequestToServe.ResponseVersionMessage> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}