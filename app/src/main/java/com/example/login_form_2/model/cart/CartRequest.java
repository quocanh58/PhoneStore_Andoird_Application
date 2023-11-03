package com.example.login_form_2.model.cart;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

public class CartRequest {


    public String type;
    public int productID;
    public int cartID;
    public int quantity;
    public int userID;

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
