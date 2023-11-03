package com.example.login_form_2.retrofit_interface;

import com.example.login_form_2.model.login.LoginReponse;
import com.example.login_form_2.model.login.LoginRequest;
import com.example.login_form_2.model.order.OrderReponse;
import com.example.login_form_2.model.order.OrderRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderServices {
    @POST("order.php")
    Call<OrderReponse> addOrder (@Body OrderRequest user);
}