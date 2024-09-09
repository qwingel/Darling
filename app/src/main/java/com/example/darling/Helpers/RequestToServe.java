package com.example.darling.Helpers;

import androidx.annotation.NonNull;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class RequestToServe {
    public static final String SQUrl = "https://Antarktida.pythonanywhere.com";
    public static final float SQVersion = 0.4f;

    public static class ResponseVersionMessage {
        public String version;
    }

    public static class ResponseLoginMessage {
        public String status, username, email, name, description, photoUrl;
        @NonNull
        @Override
        public String toString(){
            return "ResponseMessage{" +
                    "status='" + status + '\'' +
                    "username='" + username + '\'' +
                    "name='" + name + '\'' +
                    "email='" + email + '\'' +
                    "description" + description + '\'' +
                    "photoUrl" + photoUrl + '\'' + '}';
        }
    }

    public static class ResponseRegisterMessage {
        public String status;
        @NonNull
        @Override
        public String toString(){
            return "ResponseMessage{" +
                    "status='" + status + '\'' + '}';
        }
    }

    public static class ResponseResetMessage {
        public String status, message;
        @NonNull
        @Override
        public String toString(){
            return "ResponseMessage{" +
                    "status='" + status + '\'' +
                    ", message='" + message + '\'' + '}';
        }
    }

    public static class ResponseUpdateMessage {
        public String status;
        @NonNull
        @Override
        public String toString(){
            return "ResponseMessage{" +
                    "status='" + status + '\'' + '}';
        }
    }

    public static class LoginRequest {
        public LoginRequest(String login, String password){
            this.login = login;
            this.password = password;
        }
        String login, password;
    }

    public static class RegisterRequest {
        public RegisterRequest(String email, String username, String password){
            this.email = email;
            this.username = username;
            this.password = password;
        }
        String email, username, password;
    }

    public static class ResetRequest {
        public ResetRequest(String email){
            this.email = email;
        }
        String email;
    }

    public static class UpdateRequest {
        public UpdateRequest(String oldEmail, String email, String name, String username, String description){
            this.oldEmail = oldEmail;
            this.email = email;
            this.name = name;
            this.username = username;
            this.description= description;
        }
        String oldEmail, email, name, username, description;
    }

    public interface UserService {
        @GET("/version")
        Call<ResponseVersionMessage> version();
        @POST("/login")
        Call<ResponseLoginMessage> login(@Body LoginRequest loginRequest);
        @POST("/register")
        Call<ResponseRegisterMessage> register(@Body RegisterRequest registerRequest);
        @POST("/reset")
        Call<ResponseResetMessage> reset(@Body ResetRequest resetRequest);
        @POST("/update")
        Call<ResponseUpdateMessage> update(@Body UpdateRequest updateRequest);
    }
}
