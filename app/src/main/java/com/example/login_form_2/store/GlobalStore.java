package com.example.login_form_2.store;

import com.example.login_form_2.model.LoaiSanPham;
import com.example.login_form_2.model.User;
import com.example.login_form_2.model.cart.DataCart;

import java.util.ArrayList;

public class GlobalStore {
    public static User currentUser;
    public static ArrayList<DataCart> currentDataCart = new ArrayList<>();

    public static ArrayList<LoaiSanPham> currentArrLoaiSanPham = new ArrayList<>();
}
