package com.example.login_form_2.model.order;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.ArrayList;

public class GetOrderByUserReponse {
    public int result;
    public String message;
    public ArrayList<DataOrder> data;

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
