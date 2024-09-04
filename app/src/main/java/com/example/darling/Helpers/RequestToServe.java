package com.example.darling.Helpers;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class RequestToServe {
    public static final String SQUrl = "https://antarktida.pythonanywhere.com/";

    public static class ResponseLoginMessage {
        public String status, username, email, name, description, photoUrl;
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
        @Override
        public String toString(){
            return "ResponseMessage{" +
                    "status='" + status + '\'' + '}';
        }
    }

    public static class ResponseResetMessage {
        public String status, message;
        @Override
        public String toString(){
            return "ResponseMessage{" +
                    "status='" + status + '\'' +
                    ", message='" + message + '\'' + '}';
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

    public interface UserService {
        @POST("/login")
        Call<ResponseLoginMessage> login(@Body LoginRequest loginRequest);
        @POST("/register")
        Call<ResponseRegisterMessage> register(@Body RegisterRequest registerRequest);
        @POST("/reset")
        Call<ResponseResetMessage> reset(@Body ResetRequest resetRequest);
    }
}
