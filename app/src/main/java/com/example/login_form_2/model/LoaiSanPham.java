package com.example.login_form_2.model;

public class LoaiSanPham {
    public long Id;
    public String tenLoaisp;
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
}
