package com.example.login_form_2.retrofit_interface;

import com.example.login_form_2.model.category.CategoryReponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface CategoryServices {
    @GET("LoaiSanPham.php?getAllSanPham")
    Call<CategoryReponse> getAllLoaiSanPham();
}
