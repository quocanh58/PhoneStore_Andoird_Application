package com.example.login_form_2.retrofit_interface;

import com.example.login_form_2.model.register.RegisterReponse;
import com.example.login_form_2.model.register.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterServices {
    @POST("register.php")
    Call<RegisterReponse> registerUser (@Body RegisterRequest user);

}
