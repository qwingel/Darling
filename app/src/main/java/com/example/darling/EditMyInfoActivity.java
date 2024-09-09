package com.example.darling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.darling.Helpers.Func;
import com.example.darling.Helpers.RequestToServe;
import com.example.darling.Helpers.SPref;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditMyInfoActivity extends AppCompatActivity {
    Button btn_backToMain, btn_confirmNewInfo;
    EditText et_newEmail, et_newName, et_newUsername, et_newDescription;
    SharedPreferences sPref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_info);

        String sz_oldEmail = SPref.getEmail();

        btn_backToMain = findViewById(R.id.btn_backToMain);
        btn_confirmNewInfo = findViewById(R.id.btn_confirmNewInfo);

        et_newDescription = findViewById(R.id.et_newDescripton);
        et_newEmail = findViewById(R.id.et_newEmail);
        et_newUsername = findViewById(R.id.et_newUsername);
        et_newName = findViewById(R.id.et_newName);

        et_newEmail.setHint(sz_oldEmail);
        et_newName.setHint(SPref.getName());
        et_newUsername.setHint(SPref.getUsername());
        et_newDescription.setHint(SPref.getDescription());

        btn_backToMain.setOnClickListener(view -> {
            toMain();
        });

        btn_confirmNewInfo.setOnClickListener(view -> {
            String email = et_newEmail.getText().toString();
            String username = et_newUsername.getText().toString();
            String name = et_newName.getText().toString();
            String description = et_newDescription.getText().toString();

            if(email.isEmpty() || Func.isEmailValid(email)){
                 if(username.isEmpty() || Func.isUsernameValid(username)){
                     onUpdateResponse(sz_oldEmail,
                             email.isEmpty() ? sz_oldEmail : email,
                             name.isEmpty() ? SPref.getName() : name,
                             username.isEmpty() ? SPref.getUsername() : username,
                             description.isEmpty() ? SPref.getDescription() : description);

                     setSharedPref(email, username, name, description);
                     toMain();
                 } else
                     Toast.makeText(
                             getApplicationContext(),
                             "Имя пользователя должно содержать от 3 до 12 символов a-z, _, -",
                             Toast.LENGTH_LONG
                     ).show();
            } else
                Toast.makeText(
                        getApplicationContext(),
                        "Введите реальный адрес электронной почты",
                        Toast.LENGTH_LONG
                ).show();
        });
    }

    public void toMain(){
        finish();
        overridePendingTransition(R.anim.alpha_with_100, R.anim.right_out);
    }

    public void setSharedPref(String email, String username, String name, String description){
        sPref = getSharedPreferences("account", MODE_PRIVATE);

        SharedPreferences.Editor edit = sPref.edit();
        if(!email.isEmpty())
            edit.putString("email", email);

        if(!username.isEmpty())
            edit.putString("username", username);

        if(!name.isEmpty())
            edit.putString("name", name);

        if(!description.isEmpty())
            edit.putString("description", description);

        edit.apply();
    }

    public void onUpdateResponse(String oldEmail, String email, String name, String username, String description){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(RequestToServe.SQUrl)
                .build();

        RequestToServe.UserService userService = retrofit.create(RequestToServe.UserService.class);
        Call<RequestToServe.ResponseUpdateMessage> updateRequestCall = userService.update(new RequestToServe.UpdateRequest(oldEmail, email, name, username, description));
        updateRequestCall.enqueue(new Callback<RequestToServe.ResponseUpdateMessage>() {
            @Override
            public void onResponse(Call<RequestToServe.ResponseUpdateMessage> call, Response<RequestToServe.ResponseUpdateMessage> response) {
                if(response.body() != null){
                    if(response.body().status.equals("Failed"))
                        Toast.makeText(
                                getApplicationContext(),
                                "Error",
                                Toast.LENGTH_LONG
                        ).show();
                }
            }

            @Override
            public void onFailure(Call<RequestToServe.ResponseUpdateMessage> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
