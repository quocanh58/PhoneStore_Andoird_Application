package com.example.login_form_2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.login_form_2.R;

public class AdminOrderActivity extends AppCompatActivity {
    Context that = this;
    ListView lv;
    TextView tvAdminOrderFullname, tvAdminOrderDate, tvAdminOrderTotal, tvAdminOrderStatus;
    Button btnAdminOrderChangStatus, btnAdminOrderCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_admin_order);

        addControl();
        addEvents();
    }

    private void addEvents() {

    }

    private void addControl() {
        tvAdminOrderFullname = findViewById(R.id.tvAdminOrderFullname);
        tvAdminOrderDate = findViewById(R.id.tvAdminOrderDate);
        tvAdminOrderTotal = findViewById(R.id.tvAdminOrderTotal);
        tvAdminOrderStatus = findViewById(R.id.tvAdminOrderStatus);
        btnAdminOrderChangStatus = findViewById(R.id.btnAdminOrderChangStatus);
        btnAdminOrderCancel = findViewById(R.id.btnAdminOrderCancel);
    }
}