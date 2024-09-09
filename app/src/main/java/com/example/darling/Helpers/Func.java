package com.example.darling.Helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Func {
    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isUsernameValid(CharSequence username){
        boolean isValid = false;

        String expression = "^[a-z0-9_-]{3,12}$";

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(username);

        if(matcher.matches()) { isValid = true; }

        return isValid;
    }
}
