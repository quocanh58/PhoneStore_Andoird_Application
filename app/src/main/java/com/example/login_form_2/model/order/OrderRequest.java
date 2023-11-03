package com.example.login_form_2.model.order;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.ArrayList;

public class OrderRequest {
    public int UserID;
    public long time;
    public long totalPrice;

    public ArrayList<Order> data;

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
