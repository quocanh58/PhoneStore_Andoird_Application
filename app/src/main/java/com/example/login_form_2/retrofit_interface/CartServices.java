package com.example.login_form_2.retrofit_interface;

import com.example.login_form_2.model.cart.CartRequest;
import com.example.login_form_2.model.cart.GetCartReponse;
import com.example.login_form_2.model.login.LoginReponse;
import com.example.login_form_2.model.login.LoginRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CartServices {
    @GET("cart.php?getUserCart&")
    Call<GetCartReponse> getAllCartOfUser(@Query("userID") long userID);

    @POST("cart.php")
    Call<GetCartReponse> addProductToCart (@Body CartRequest user);
}
