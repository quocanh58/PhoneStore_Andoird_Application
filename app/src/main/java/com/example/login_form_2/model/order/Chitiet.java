package com.example.login_form_2.model.order;

import com.example.login_form_2.model.Product;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Chitiet implements Serializable {
    @SerializedName("Id")
    public String id;
    public String iddonhang;
    public String idsanpham;
    public String soluong;
    public String dongia;
    public Product sanpham;
}
