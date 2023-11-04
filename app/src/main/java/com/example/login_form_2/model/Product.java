package com.example.login_form_2.model;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {
    @SerializedName("Id")
    public String id;
    public String tensanpham;
    public String giasanpham;
    public String hinhanhsanpham;
    public String mota;
    public String soluong;
    public String idloaisanpham;

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
