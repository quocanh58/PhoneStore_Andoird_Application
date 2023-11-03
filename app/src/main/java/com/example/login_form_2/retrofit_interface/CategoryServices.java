package com.example.login_form_2.retrofit_interface;

import com.example.login_form_2.model.category.GetCategoryReponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryServices {
    @GET("LoaiSanPham.php?getAllSanPham")
    Call<GetCategoryReponse> getAllLoaiSanPham();


}
