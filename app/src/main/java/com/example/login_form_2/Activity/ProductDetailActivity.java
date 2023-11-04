package com.example.login_form_2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.login_form_2.R;
import com.example.login_form_2.model.LoaiSanPham;
import com.example.login_form_2.model.Product;
import com.example.login_form_2.model.cart.CartRequest;
import com.example.login_form_2.model.cart.GetCartReponse;
import com.example.login_form_2.retrofit.APIClient;
import com.example.login_form_2.retrofit_interface.CartServices;
import com.example.login_form_2.store.GlobalStore;
import com.example.login_form_2.utils.Alert;
import com.example.login_form_2.utils.Function;
import com.example.login_form_2.utils.LoadingDialog;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                .resize(300, 200)
                .into(imgProduct);
        tvProductName.setText("Tên sản phẩm: " + products.tensanpham);
        tvLoaisp.setText("Loại sản phẩm: " + LoaiSanPham.getNameofCategory(Function.getLongNumber(products.idloaisanpham)));
        tvProductPrice.setText("Giá : " + products.giasanpham);
        tvProductDescription.setText("Mô tả: " + products.mota);
        textViewProductQuantity.setText("Số lượng: " + products.idloaisanpham);

        btnThemGioHang.setOnClickListener(v -> {

            LoadingDialog.setLoading(v.getContext(), true);
            CartRequest cartRequest = new CartRequest();

            cartRequest.type = "insert";
            cartRequest.userID = Integer.parseInt(GlobalStore.currentUser.id);
            cartRequest.productID = Function.getIntNumber(products.id);
            cartRequest.quantity = 1;

            Call<GetCartReponse> call = APIClient.getClient().create(CartServices.class).addProductToCart(cartRequest);
            call.enqueue(new Callback<GetCartReponse>() {
                @Override
                public void onResponse(Call<GetCartReponse> call, Response<GetCartReponse> response) {
                    LoadingDialog.setLoading(v.getContext(), false);
                    if (response.isSuccessful() && response.body() != null && response.body().result == 1) {
                        GlobalStore.currentDataCart = response.body().data;
                        Alert.alert(v.getContext(), "Giỏ hàng", response.body().message);
                    } else {
                        Alert.alert(v.getContext(), response.body().message);
                    }
                }

                @Override
                public void onFailure(Call<GetCartReponse> call, Throwable t) {
                    LoadingDialog.setLoading(v.getContext(), false);
                    t.printStackTrace();

                }
            });
        });

        btnMua.setOnClickListener(v -> {
            Alert.alert(v.getContext(), "Chức năng mua ngay", "Chức năng đang phát trieern " + products.id);
        });
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