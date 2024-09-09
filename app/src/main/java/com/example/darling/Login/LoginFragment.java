package com.example.darling.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.darling.MainActivity;
import com.example.darling.R;
import com.example.darling.Helpers.RequestToServe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginFragment extends Fragment {
    Button btn_login, btn_signup, btn_recovery;
    EditText et_login, et_password;
    SharedPreferences sPref, ssPref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    public void responseLogin(String login, String password){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RequestToServe.SQUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestToServe.UserService userService = retrofit.create(RequestToServe.UserService.class);
        Call<RequestToServe.ResponseLoginMessage> toLogin = userService.login(new RequestToServe.LoginRequest(login, password));

        toLogin.enqueue(new Callback<RequestToServe.ResponseLoginMessage>() {
            @Override
            public void onResponse(@NonNull Call<RequestToServe.ResponseLoginMessage> call, Response<RequestToServe.ResponseLoginMessage> response) {
                if(response.body() != null){
                    if (response.body().status.equals("Failed")){
                        Toast.makeText(
                                getActivity().getApplicationContext(),
                                "Неверный логин или пароль",
                                Toast.LENGTH_LONG
                        ).show();
                    } else {
                        String username = response.body().username;
                        String email = response.body().email;
                        String name = response.body().name;
                        String description = response.body().description;
                        String photoUrl = response.body().photoUrl;

//                        Boolean isOnline = response.body().isOnline;
//                        int screen = response.body().screen;

                        sPref = getActivity().getSharedPreferences("account", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sPref.edit();
                        edit.putString("name", name);
                        edit.putString("username", username);
                        edit.putString("email", email);
                        edit.putString("description", description);
                        edit.putString("photo", photoUrl);
                        edit.apply();

                        ssPref = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = ssPref.edit();
                        editor.putBoolean("isOnline", true);
                        editor.putInt("screen", 0);
                        editor.apply();

                        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    }
                }
            }

            @Override
            public void onFailure(Call<RequestToServe.ResponseLoginMessage> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    @Override
    public void onViewCreated(@NonNull View view1, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view1, savedInstanceState);
        btn_login = view1.findViewById(R.id.btn_login);
        btn_signup = view1.findViewById(R.id.btn_signup);
        btn_recovery = view1.findViewById(R.id.btn_forgotpass);

        et_login = view1.findViewById(R.id.et_uname);
        et_password = view1.findViewById(R.id.et_password);

        btn_login.setOnClickListener(view -> {
            responseLogin(et_login.getText().toString().replaceAll(" ", ""), et_password.getText().toString().replaceAll(" ", ""));
        });

        btn_signup.setOnClickListener(view ->{
            RegistrationFragment registrationFragment = new RegistrationFragment();
            setNewFragment(registrationFragment);
        } );

        btn_recovery.setOnClickListener(view -> {
            RecoveryFragment recoveryFragment = new RecoveryFragment();
            setNewFragment(recoveryFragment);
        });
    }

    private void setNewFragment(Fragment fragment){
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_login, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}