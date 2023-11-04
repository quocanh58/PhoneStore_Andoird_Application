package com.example.login_form_2.model.cart;

import androidx.annotation.NonNull;

import com.example.login_form_2.model.Product;
import com.google.gson.Gson;

public class DataCart {

    public String id;
    public String quantity;
    public String userID;

    public Product product;
    public boolean isChecked = false;

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this)
                ;
    }
}
