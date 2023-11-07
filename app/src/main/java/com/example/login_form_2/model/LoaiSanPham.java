package com.example.login_form_2.model;

import androidx.annotation.NonNull;

import com.example.login_form_2.store.GlobalStore;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoaiSanPham implements Serializable {
    public long Id;
    @SerializedName("tenloaisanpham")
    public String tenLoaisp;
    @SerializedName("hinhanhloaisanpham")
    public String hinhAnhLoaisp;

    public LoaiSanPham(long id, String tenLoaisp, String hinhAnhLoaisp) {
        Id = id;
        this.tenLoaisp = tenLoaisp;
        this.hinhAnhLoaisp = hinhAnhLoaisp;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getTenLoaisp() {
        return tenLoaisp;
    }

    public void setTenLoaisp(String tenLoaisp) {
        this.tenLoaisp = tenLoaisp;
    }

    public String getHinhAnhLoaisp() {
        return hinhAnhLoaisp;
    }

    public void setHinhAnhLoaisp(String hinhAnhLoaisp) {
        this.hinhAnhLoaisp = hinhAnhLoaisp;
    }


    public static String getNameofCategory(long id){
        for(LoaiSanPham loaiSanPham : GlobalStore.currentArrLoaiSanPham){
            if (loaiSanPham.Id == id){
                return loaiSanPham.tenLoaisp;
            }
        }
        return "";
    }
    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
