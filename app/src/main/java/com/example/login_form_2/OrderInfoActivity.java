package com.example.login_form_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.login_form_2.Activity.PayActivity;
import com.example.login_form_2.adapter.OrderInfoAdapater;
import com.example.login_form_2.model.Product;
import com.example.login_form_2.model.order.Chitiet;
import com.example.login_form_2.model.order.DataOrder;
import com.example.login_form_2.model.order.Donhang;
import com.example.login_form_2.model.order.GetOrderByUserReponse;
import com.example.login_form_2.model.order.OrderRequest;
import com.example.login_form_2.retrofit.APIClient;
import com.example.login_form_2.retrofit_interface.OrderServices;
import com.example.login_form_2.store.GlobalStore;
import com.example.login_form_2.utils.Alert;
import com.example.login_form_2.utils.Function;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderInfoActivity extends AppCompatActivity {

    Context that = this;
    TextView txtOrderInfo,txtOrderAddress,txtOrderInfoTongThanhToan,txtOrderInfoTrangThai;
    Button btnOrderInfoHuyDon,btnOrderInfoMuaLai;

    ListView lvOrderInfo ;

    Donhang donHang;
    ArrayList<Chitiet> chitiets = new ArrayList<>();


    OrderInfoAdapater infoAdapater ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);
        addControl();
        addEvent();
    }

    private void addEvent() {
        btnOrderInfoHuyDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderRequest request = new OrderRequest();
                request.UserID = Integer.parseInt(GlobalStore.currentUser.id);
                request.type = "delete";
                request.orderId = Integer.parseInt(donHang.id);
                Call<GetOrderByUserReponse> call = APIClient.getClient().create(OrderServices.class).DeleteOrder(request);
                call.enqueue(new Callback<GetOrderByUserReponse>() {
                    @Override
                    public void onResponse(Call<GetOrderByUserReponse> call, Response<GetOrderByUserReponse> response) {
                        if(response.code() == 200 && response.isSuccessful() && response.body() != null){
                            GetOrderByUserReponse reponse_new = response.body();
                            Alert.confirm(that, reponse_new.message, new Alert.OnDialogButtonClickListener() {
                                @Override
                                public void onPositiveButtonClick() {
                                    Intent resultIntent = new Intent();
                                    setResult(RESULT_OK, resultIntent);
                                    finish();
                                }

                                @Override
                                public void onNegativeButtonClick() {
                                    Intent resultIntent = new Intent();
                                    setResult(RESULT_OK, resultIntent);
                                    finish();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<GetOrderByUserReponse> call, Throwable t) {

                    }
                });
            }
        });

        btnOrderInfoMuaLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(that, PayActivity.class);
                ArrayList<Chitiet> muaLai = new ArrayList<>();
                muaLai.addAll(chitiets);
                intent.putExtra("mualai",muaLai);
                startActivity(intent);

            }
        });
    }

    private void addControl() {
        txtOrderInfo = findViewById(R.id.txtOrderInfo);
        txtOrderAddress = findViewById(R.id.txtOrderAddress);
        txtOrderInfoTongThanhToan = findViewById(R.id.txtOrderInfoTongThanhToan);
        txtOrderInfoTrangThai = findViewById(R.id.txtOrderInfoTrangThai);
        btnOrderInfoHuyDon = findViewById(R.id.btnOrderInfoHuyDon);
        btnOrderInfoMuaLai = findViewById(R.id.btnOrderInfoMuaLai);
        lvOrderInfo = findViewById(R.id.lvOrderInfo);

        Intent intent = getIntent();
        donHang = (Donhang) intent.getSerializableExtra("donHang");
        if(donHang != null){
            chitiets = donHang.chitiet;
        }
        infoAdapater = new OrderInfoAdapater(that,R.layout.item_pay,chitiets);
        lvOrderInfo.setAdapter(infoAdapater);
        txtOrderAddress.setText(GlobalStore.currentUser.address);
        txtOrderInfo.setText(GlobalStore.currentUser.name + " | " + GlobalStore.currentUser.phone);


        txtOrderInfoTrangThai.setText(donHang.status);

        switch (donHang.status) {
            case "Chưa xử lý":
                int xanhColor = ContextCompat.getColor(that, R.color.colorXanh);
                txtOrderInfoTrangThai.setBackgroundColor(xanhColor);
                break;
            case "Đã xử lý":
                txtOrderInfoTrangThai.setBackgroundColor(ContextCompat.getColor(that, R.color.colorDo));
                break;
            case "Đang giao":
                txtOrderInfoTrangThai.setBackgroundColor(ContextCompat.getColor(that, R.color.colorVang));
                break;
            case "Hoàn thành":
                txtOrderInfoTrangThai.setBackgroundColor(ContextCompat.getColor(that, R.color.colorXanhLa));
                break;
            default:
                // Xử lý trường hợp khác nếu cần
        }

        txtOrderInfoTongThanhToan.setText(Function.formatCurrency(Function.getLongNumber(donHang.tongtien)));

        // check hoàn thành đơn
        if(donHang.status.equals("Hoàn thành")){
            btnOrderInfoHuyDon.setVisibility(View.GONE);
        }

    }
}