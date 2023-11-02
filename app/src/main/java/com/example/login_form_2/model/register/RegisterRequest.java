package com.example.login_form_2.model.register;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("name")
    private String name;

    @SerializedName("address")
    private String address;

    @SerializedName("phone")
    private String phone;

    public RegisterRequest(String email, String password, String name, String address, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
