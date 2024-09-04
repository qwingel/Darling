package com.example.darling;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.darling.Login.LoginFragment;

public class AuthActivity extends AppCompatActivity {
    LoginFragment loginFragment = new LoginFragment();
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_auth);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_login, loginFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
