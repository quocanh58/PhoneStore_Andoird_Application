package com.example.login_form_2.model.login;

import androidx.annotation.NonNull;

import com.example.login_form_2.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;

public class LoginReponse {
    public int result;
    public String message;
    public User user;
    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
