package com.example.login_form_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.login_form_2.adapter.CartAdapter;
import com.example.login_form_2.adapter.PayAdapter;
import com.example.login_form_2.model.cart.DataCart;
import com.example.login_form_2.store.GlobalStore;
import com.example.login_form_2.utils.Alert;
import com.example.login_form_2.utils.Function;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class PayActivity extends AppCompatActivity {
    TextView txtPayInfo,txtPayAddress,txtTotalPricePay;
    Context that = this;
    public static ListView lvPay;
    public static PayAdapter payAdapter;
    Button btnDatHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        addControl();
        addEvent();
    }



    private void addControl() {
        long sum = 0;
        lvPay = findViewById(R.id.lvPay);
        ArrayList<DataCart> dataCarts = new ArrayList<>();
        for (Map.Entry<DataCart, String> entry : CartActivity.dataCartsSeleted.entrySet()) {
            dataCarts.add(entry.getKey());
            sum += Function.getLongNumber(entry.getKey().product.giasanpham) * Function.getLongNumber( entry.getKey().quantity);
        }
        payAdapter = new PayAdapter(that, R.layout.item_pay, dataCarts);
        lvPay.setAdapter(payAdapter);
        txtPayAddress = findViewById(R.id.txtPayAddress);
        txtPayInfo = findViewById(R.id.txtPayInfo);

        txtPayAddress.setText(GlobalStore.currentUser.address);
        txtPayInfo.setText(GlobalStore.currentUser.name + " | " + GlobalStore.currentUser.phone);

        btnDatHang = findViewById(R.id.btnPay);
        txtTotalPricePay = findViewById(R.id.txtTotalPricePay);
        txtTotalPricePay.setText(Function.formatCurrency(sum));
    }
    private void addEvent() {
        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alert.alert(that,"Chức năng đang phát triển");
            }
        });
    }
}