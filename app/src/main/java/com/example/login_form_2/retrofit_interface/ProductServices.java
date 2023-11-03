package com.example.login_form_2.retrofit_interface;

import com.example.login_form_2.model.category.GetCategoryReponse;
import com.example.login_form_2.model.product.GetProductReponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductServices {
    @GET("SanPham.php?getSanPhamByIdLoaiSanPham&")
    Call<GetProductReponse> getSanPhamByCategoryID(@Query("idloaisanpham") long idloaisanpham);
    @GET("SanPham.php?getAllSanPham")
    Call<GetProductReponse> getAllSanPham();
}
