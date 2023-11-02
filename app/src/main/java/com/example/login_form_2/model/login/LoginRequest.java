package com.example.login_form_2.model.login;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

public class LoginRequest {
    public String email;
    public String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
