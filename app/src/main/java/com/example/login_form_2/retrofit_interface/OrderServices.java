package com.example.login_form_2.retrofit_interface;

import com.example.login_form_2.model.cart.GetCartReponse;
import com.example.login_form_2.model.login.LoginReponse;
import com.example.login_form_2.model.login.LoginRequest;
import com.example.login_form_2.model.order.GetOrderByUserReponse;
import com.example.login_form_2.model.order.OrderReponse;
import com.example.login_form_2.model.order.OrderRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OrderServices {
    @POST("order.php")
    Call<OrderReponse> addOrder (@Body OrderRequest user);


    @GET("order.php?getAllOrder")
    Call<GetOrderByUserReponse> getAllOrderOfUser(@Query("userID") int userID);
}
