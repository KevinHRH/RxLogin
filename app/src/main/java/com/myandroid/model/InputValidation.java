package com.myandroid.model;

import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

/**
 * Created by Kevin_MP on 2016/3/16.
 */
public class InputValidation {
    private String userName;
    private String password;
    private String confirmedPassword;

    public InputValidation(String userName, String password, String confirmedPassword) {
        this.userName = userName;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
    }

    public boolean allowSubmit() {
        boolean isUserNameValid = userName.trim().length() > 2;
        boolean isPasswordValid = password.trim().length() > 3;
        boolean isPasswordEqual = confirmedPassword.trim().equals(password);
        boolean allowSubmit = isUserNameValid && isPasswordValid && isPasswordEqual;
        return allowSubmit;
    }

    public static String stringOfTextChangEvent(TextViewTextChangeEvent onTextChangeEvent) {
        return onTextChangeEvent.text().toString();
    }
}
