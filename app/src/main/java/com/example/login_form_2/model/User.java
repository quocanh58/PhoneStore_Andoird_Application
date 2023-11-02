package com.example.login_form_2.model;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

public class User {
    public String id;
    public String email;
    public String password;
    public String name;
    public String address;
    public String phone;
    public String role;

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
