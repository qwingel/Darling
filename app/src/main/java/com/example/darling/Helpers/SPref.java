package com.example.darling.Helpers;

import android.content.SharedPreferences;

import com.google.android.material.navigation.NavigationView;

public class SPref {
    private static String name;
    private static String username;
    private static String description;
    private static String email;
    private static String photo;


    public static void setName(String name_){
        name = name_;
    }

    public static String getName(){
        return name;
    }
    public static void setUsername(String username_){
        username = username_;
    }
    public static String getUsername(){
        return username;
    }
    public static void setEmail(String email_){
        email = email_;
    }
    public static String getEmail(){
        return email;
    }
    public static void setDescription(String description_){
        description = description_;
    }
    public static String getDescription(){
        return description;
    }
    public static void setPhoto(String photo_){
        photo = photo_;
    }
    public static String getPhoto(){
        return photo;
    }

    public static void setAllDataNull(){
        setName("");
        setUsername("");
        setEmail("");
        setDescription("");
        setPhoto("ssilka");
    }

    public static void setAllData(String name_, String username_, String email_, String description_, String photo_){
        setName(name_);
        setUsername(username_);
        setEmail(email_);
        setDescription(description_);
        setPhoto(photo_);
    }

    public static String[] getAllData(){
        return new String[]{getName(), getUsername(), getEmail(), getDescription(), getPhoto() };
    }
}
