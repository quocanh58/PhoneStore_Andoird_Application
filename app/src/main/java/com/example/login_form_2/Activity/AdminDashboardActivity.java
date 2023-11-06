package com.example.login_form_2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;

import com.example.login_form_2.R;

public class AdminDashboardActivity extends AppCompatActivity {

    Button btnQuanLyTaiKhoan, btnQuanLySP, btnQuanLyDonHang, btnQuanLyLoaiSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        addControl();
        addEvents();

    }

    private void addEvents() {
        btnQuanLyTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashboardActivity.this, AdminUserActivity.class);
                startActivity(intent);
            }
        });
        btnQuanLySP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(AdminDashboardActivity.this, AdminProductActivity.class);
                startActivity(intent2);
            }
        });
        btnQuanLyDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(AdminDashboardActivity.this, AdminOrderActivity.class);
                startActivity(intent3);
            }
        });
        btnQuanLyLoaiSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(AdminDashboardActivity.this, AdminProductCategoryActivity.class);
                startActivity(intent4);
            }
        });
    }

    private void addControl() {
        btnQuanLyTaiKhoan = findViewById(R.id.btnQuanLyTaiKhoan);
        btnQuanLySP = findViewById(R.id.btnQuanLySP);
        btnQuanLyDonHang = findViewById(R.id.btnQuanLyDonHang);
    }
}