package com.example.login_form_2.model.order;

import com.google.gson.Gson;

public class OrderReponse {
    public int result;
    public String message;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
