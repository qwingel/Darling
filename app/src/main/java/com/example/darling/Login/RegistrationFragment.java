package com.example.darling.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.darling.Helpers.Func;
import com.example.darling.MainActivity;
import com.example.darling.R;
import com.example.darling.Helpers.RequestToServe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationFragment extends Fragment {
    Button btn_registration;
    LinearLayout ll_backToLogin;
    EditText et_email, et_username, et_password;
    SharedPreferences sPref, ssPref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }

    public void responseRegister(String email, String username, String password){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RequestToServe.SQUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestToServe.UserService userService = retrofit.create(RequestToServe.UserService.class);
        Call<RequestToServe.ResponseRegisterMessage> toRegister = userService.register(new RequestToServe.RegisterRequest(email, username, password));

        toRegister.enqueue(new Callback<RequestToServe.ResponseRegisterMessage>() {
            @Override
            public void onResponse(Call<RequestToServe.ResponseRegisterMessage> call, Response<RequestToServe.ResponseRegisterMessage> response) {
                if(response.body() != null){
                    if(response.body().status.equals("Failed"))
                        Toast.makeText(
                                getActivity().getApplicationContext(),
                                "Пользователь с такой почтой или логином уже существует",
                                Toast.LENGTH_LONG).show();
                    else {
                        sPref = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sPref.edit();
                        edit.putString("name", username);
                        edit.putString("username", username);
                        edit.putString("email", email);
                        edit.putString("description", "");
                        edit.putString("photo", "ssilka");
                        edit.apply();

                        ssPref = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = ssPref.edit();
                        editor.putBoolean("isOnline", true);
                        editor.putInt("screen", 0);
                        editor.apply();

                        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<RequestToServe.ResponseRegisterMessage> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    @Override
    public void onViewCreated(@NonNull View view1, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view1, savedInstanceState);

        ll_backToLogin = view1.findViewById(R.id.ll_backToLogin);
        btn_registration = view1.findViewById(R.id.btn_registration);

        et_email = view1.findViewById(R.id.et_email);
        et_username = view1.findViewById(R.id.et_uname);
        et_password = view1.findViewById(R.id.et_password);

        btn_registration.setOnClickListener(view -> {
            String sz_email = et_email.getText().toString();
            String sz_username = et_username.getText().toString();
            String sz_password = et_password.getText().toString().replaceAll(" ", "");
            if(Func.isEmailValid(sz_email)){
                if(Func.isUsernameValid(sz_username)){
                    responseRegister(
                            sz_email,
                            sz_username,
                            sz_password
                    );
                } else
                    Toast.makeText(
                            getContext(),
                            "Имя пользователя должно содержать от 3 до 12 символов a-z, _, -",
                            Toast.LENGTH_LONG
                    ).show();
            } else
                Toast.makeText(
                        getContext(),
                        "Введите реальный адрес электронной почты",
                        Toast.LENGTH_LONG
                ).show();
        });
        ll_backToLogin.setOnClickListener(view -> {
            LoginFragment loginFragment = new LoginFragment();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fl_login, loginFragment);
            ft.addToBackStack(null);
            ft.commit();
        });
    }
}