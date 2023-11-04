package com.example.login_form_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.login_form_2.model.Product;
import com.example.login_form_2.utils.Alert;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {

    Context that = this;

    TextView tvProductName, tvLoaisp, tvProductPrice, tvProductDescription, textViewProductQuantity;

    ImageView imgProduct;
    Button btnMua, btnThemGioHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Intent intent = getIntent();
        Product products = (Product) intent.getSerializableExtra("product");
        Alert.alert(that, products.toString());

        imgProduct = findViewById(R.id.imgProduct);
        tvProductName = findViewById(R.id.tvProductName);
        tvLoaisp = findViewById(R.id.tvLoaisp);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvProductDescription = findViewById(R.id.tvProductDescription);
        textViewProductQuantity = findViewById(R.id.textViewProductQuantity);
        btnMua = findViewById(R.id.btnMua);
        btnThemGioHang = findViewById(R.id.btnThemGioHang);

        Picasso.get()
                .load(products.hinhanhsanpham)
                .into(imgProduct
                );
        tvProductName.setText(products.tensanpham);
        tvLoaisp.setText(products.idloaisanpham);
        tvProductPrice.setText(products.giasanpham);
        tvProductDescription.setText(products.mota);
        textViewProductQuantity.setText(products.idloaisanpham);
    }

    private void addControl() {
        tvProductName = findViewById(R.id.tvProductName);
        tvLoaisp = findViewById(R.id.tvLoaisp);
        tvProductPrice = findViewById(R.id.tvProductPrice);
        tvProductDescription = findViewById(R.id.tvProductDescription);
        textViewProductQuantity = findViewById(R.id.textViewProductQuantity);
        btnMua = findViewById(R.id.btnMua);
        btnThemGioHang = findViewById(R.id.btnThemGioHang);


    }
}