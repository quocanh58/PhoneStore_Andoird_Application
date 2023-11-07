package com.example.login_form_2.model.product;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

public class ProductRequest {
    public int Id;
    public String type;
    public String tensanpham;
    public int giasanpham;
    public String hinhanhsanpham;
    public String mota;
    public int idsanpham;

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
