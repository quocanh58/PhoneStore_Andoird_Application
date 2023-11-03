package com.example.login_form_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.login_form_2.adapter.CartAdapter;
import com.example.login_form_2.model.cart.DataCart;
import com.example.login_form_2.store.GlobalStore;
import com.example.login_form_2.utils.Alert;

public class CartActivity extends AppCompatActivity {
    Context that = this;
    ListView lvCart ;
    CheckBox checkAllCart;
    TextView totalPrice;
    Button btnMuaHang;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        addControl();
        addEvent();
    }

    private void addEvent() {
        lvCart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               try{
                   DataCart dataCart = GlobalStore.currentDataCart.get(position);
                   Alert.alert(that,dataCart.toString());
               }
               catch (Exception e){
                   e.printStackTrace();
                   Alert.alert(that,"Đã có lỗi xảy ra", e.getMessage());
               }
            }
        });
    }

    private void addControl() {
        lvCart = findViewById(R.id.lvCart);
        checkAllCart = findViewById(R.id.btnCheckAllCart);
        totalPrice = findViewById(R.id.txtTotalPriceCart);
        btnMuaHang = findViewById(R.id.btnMuaHang);
        // thêm cart
        cartAdapter = new CartAdapter(that,R.layout.item_card, GlobalStore.currentDataCart);
        lvCart.setAdapter(cartAdapter);
    }
}