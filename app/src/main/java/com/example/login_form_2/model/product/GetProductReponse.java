package com.example.login_form_2.model.product;

import androidx.annotation.NonNull;

import com.example.login_form_2.model.Product;
import com.google.gson.Gson;

import java.util.ArrayList;

public class GetProductReponse {
    public int result;
    public String message;
    public ArrayList<Product> data;

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
