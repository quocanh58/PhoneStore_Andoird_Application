package com.example.login_form_2.model.register;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class RegisterReponse {
    public int result;
    public String message;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
