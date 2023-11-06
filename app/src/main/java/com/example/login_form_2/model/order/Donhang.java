package com.example.login_form_2.model.order;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Donhang implements Serializable {
    @SerializedName("Id")
    public String id;
    public Object idkhachhang;
    public String ngaydat;
    public String tongtien;
    public String userId;
    public String status;
    public ArrayList<Chitiet> chitiet;
}
