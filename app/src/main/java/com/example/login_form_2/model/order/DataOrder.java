package com.example.login_form_2.model.order;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

public class DataOrder {
    public Donhang donhang;

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
