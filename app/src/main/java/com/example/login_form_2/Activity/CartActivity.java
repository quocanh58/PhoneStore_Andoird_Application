package com.example.login_form_2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.login_form_2.R;
import com.example.login_form_2.adapter.CartAdapter;
import com.example.login_form_2.model.cart.DataCart;
import com.example.login_form_2.store.GlobalStore;
import com.example.login_form_2.utils.Alert;
import com.example.login_form_2.utils.Function;

import java.util.HashMap;

public class CartActivity extends AppCompatActivity  {
    public static HashMap<DataCart,String> dataCartsSeleted = new HashMap<>();
    Context that = this;
    public  static  ListView lvCart;
    CheckBox checkAllCart;
    static TextView totalPrice,txtThongbao;
    Button btnMuaHang;
    public static CartAdapter cartAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        addControl();
        addEvent();
    }

    private void addEvent() {
        lvCart.setOnItemClickListener((parent, view, position, id) -> {
            try {
                DataCart dataCart = GlobalStore.currentDataCart.get(position);
                Alert.alert(that, dataCart.toString());
            } catch (Exception e) {
                e.printStackTrace();
                Alert.alert(that, "Đã có lỗi xảy ra", e.getMessage());
            }
        });
        checkAllCart.setOnClickListener(v -> {
            double sum = 0;

            for (DataCart dataCart : GlobalStore.currentDataCart) {
                dataCart.isChecked = checkAllCart.isChecked();
                if (dataCart.isChecked) {
                    sum += Function.getIntNumber(dataCart.quantity) * Function.getDoubleNumber(dataCart.product.giasanpham);

                }
            }
            cartAdapter.notifyDataSetChanged();
            totalPrice.setText(Function.formatCurrency(sum));
        });
        btnMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalPrice.getText().toString().trim().equals("0 VND")){
                    return;
                }
                CartActivity.dataCartsSeleted.clear();
                for(DataCart dataCart : GlobalStore.currentDataCart){
                    if(dataCart.isChecked){
                        dataCartsSeleted.put(dataCart,"");
                    }
                }
                System.out.println(dataCartsSeleted.toString());
                Intent intent = new Intent(that,PayActivity.class);
                startActivity(intent);
            }
        });
    }

    public static void updateTotal(double price){
        totalPrice.setText(Function.formatCurrency(price));
    }
    public static void UpdateListView(){
        CartActivity.cartAdapter = new CartAdapter(lvCart.getContext(), R.layout.item_card, GlobalStore.currentDataCart);
        CartActivity.lvCart.setAdapter(  CartActivity.cartAdapter);
    }
    private void addControl() {
        lvCart = findViewById(R.id.lvCart);
        cartAdapter = new CartAdapter(that, R.layout.item_card, GlobalStore.currentDataCart);
        lvCart.setAdapter(cartAdapter);
        checkAllCart = findViewById(R.id.btnCheckAllCart);
        totalPrice = findViewById(R.id.txtTotalPriceCart);
        txtThongbao = findViewById(R.id.txtThongbao);
        btnMuaHang = findViewById(R.id.btnMuaHang);
        totalPrice.setText(Function.formatCurrency(CartAdapter.getTotalPriceCart()));
        // thêm cart

        if(GlobalStore.currentDataCart.size() == 0){
            txtThongbao.setVisibility(View.VISIBLE);
            lvCart.setVisibility(View.GONE);
        }

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }


}