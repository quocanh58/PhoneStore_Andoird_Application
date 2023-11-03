package com.example.login_form_2.model.category;

import androidx.annotation.NonNull;

import com.example.login_form_2.model.LoaiSanPham;
import com.google.gson.Gson;

import java.util.ArrayList;

public class GetCategoryReponse {
    public int result;
    public String message;
    public ArrayList<LoaiSanPham> data;

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
