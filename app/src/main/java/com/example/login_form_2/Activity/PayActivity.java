package com.example.login_form_2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.login_form_2.R;
import com.example.login_form_2.adapter.PayAdapter;
import com.example.login_form_2.model.Product;
import com.example.login_form_2.model.cart.CartRequest;
import com.example.login_form_2.model.cart.DataCart;
import com.example.login_form_2.model.cart.GetCartReponse;
import com.example.login_form_2.model.order.Order;
import com.example.login_form_2.model.order.OrderReponse;
import com.example.login_form_2.model.order.OrderRequest;
import com.example.login_form_2.retrofit.APIClient;
import com.example.login_form_2.retrofit_interface.CartServices;
import com.example.login_form_2.retrofit_interface.OrderServices;
import com.example.login_form_2.store.GlobalStore;
import com.example.login_form_2.utils.Alert;
import com.example.login_form_2.utils.Function;
import com.example.login_form_2.utils.LoadingDialog;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayActivity extends AppCompatActivity {
    TextView txtPayInfo, txtPayAddress, txtTotalPricePay;
    Context that = this;
    public static ListView lvPay;
    public static PayAdapter payAdapter;
    Button btnDatHang;
    private ArrayList<DataCart> dataCarts;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        addControl();
        addEvent();
    }

    long sum = 0;

    private void addControl() {

        lvPay = findViewById(R.id.lvPay);
        dataCarts = new ArrayList<>();
        Intent intent = getIntent();

        for (Map.Entry<DataCart, String> entry : CartActivity.dataCartsSeleted.entrySet()) {
            dataCarts.add(entry.getKey());
            sum += Function.getLongNumber(entry.getKey().product.giasanpham) * Function.getLongNumber(entry.getKey().quantity);
        }

        Intent intent2 = getIntent();
        product = (Product) intent.getSerializableExtra("muangay");
        if (product != null) {
            dataCarts.clear();
            DataCart dataCart = new DataCart();
            dataCart.product = product;
            dataCart.userID = GlobalStore.currentUser.id;
            dataCart.quantity = "1";
            dataCart.isChecked = false;
            dataCarts.add(dataCart);
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
                LoadingDialog.setLoading(that, true);
                OrderRequest request = new OrderRequest();
                //sum += Function.getLongNumber(product.giasanpham);

                request.time = System.currentTimeMillis();
                request.UserID = Integer.parseInt(GlobalStore.currentUser.id);
                request.totalPrice = sum; //cập nhật giá khi tạo mới 1 đơn hàng
                request.data = new ArrayList<>();
                for (DataCart dataCart : dataCarts) {
                    Order order = new Order();
                    order.dongia = Function.getDoubleNumber(dataCart.product.giasanpham);
                    order.soluong = Integer.parseInt(dataCart.quantity);
                    order.idsanpham = Integer.parseInt(dataCart.product.id);
                    request.data.add(order);
                }

                Call<OrderReponse> call = APIClient.getClient().create(OrderServices.class).addOrder(request);
                call.enqueue(new Callback<OrderReponse>() {
                    @Override
                    public void onResponse(Call<OrderReponse> call, Response<OrderReponse> response) {
                        LoadingDialog.setLoading(that, false);
                        if (response.code() == 200 && response.isSuccessful() && response.body() != null) {
                            Alert.alert(that, response.body().message);
                            for (DataCart dataCart : dataCarts) {
                                CartRequest request = new CartRequest();
                                request.type = "delete";
                                request.cartID = Function.getIntNumber(dataCart.id);
                                request.userID = Function.getIntNumber(GlobalStore.currentUser.id);
                                Call<GetCartReponse> call2 = APIClient.getClient().create(CartServices.class).deleteProductFromCart(request);
                                call2.enqueue(new Callback<GetCartReponse>() {
                                    @Override
                                    public void onResponse(Call<GetCartReponse> call, Response<GetCartReponse> response) {
                                        LoadingDialog.setLoading(v.getContext(), false);
                                        if (response.isSuccessful() && response.body() != null && response.body().result == 1) {
                                            GlobalStore.currentDataCart = response.body().data;
                                            if (product != null) {
                                                product = null;
                                            } else {
                                                CartActivity.UpdateListView();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<GetCartReponse> call, Throwable t) {
                                        t.printStackTrace();
                                        LoadingDialog.setLoading(v.getContext(), false);
                                    }
                                });
                            }
                            dataCarts.clear();
                            payAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderReponse> call, Throwable t) {
                        LoadingDialog.setLoading(that, false);
                        t.printStackTrace();
                    }
                });
                System.out.println(request.toString());
            }
        });
    }
}