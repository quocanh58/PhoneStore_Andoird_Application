package com.example.login_form_2.retrofit_interface;

import com.example.login_form_2.model.LoaiSanPham;
import com.example.login_form_2.model.category.GetCategoryReponse;
import com.example.login_form_2.model.category.GetCategoryRequest;
import com.example.login_form_2.model.login.LoginReponse;
import com.example.login_form_2.model.login.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CategoryServices {
    @GET("LoaiSanPham.php?getAllSanPham")
    Call<GetCategoryReponse> getAllLoaiSanPham();
    @POST("LoaiSanPham.php")
    Call<GetCategoryReponse> insertCategory (@Body GetCategoryRequest categoryRequest);
    @POST("LoaiSanPham.php")
    Call<GetCategoryReponse> updateCategory (@Body GetCategoryRequest categoryRequest);

    @POST("LoaiSanPham.php")
    Call<GetCategoryReponse> DeletaCategory (@Body GetCategoryRequest categoryRequest);
}
