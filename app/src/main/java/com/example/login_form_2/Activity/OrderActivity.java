package com.example.login_form_2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.login_form_2.OrderInfoActivity;
import com.example.login_form_2.R;
import com.example.login_form_2.adapter.OrderAdapter;
import com.example.login_form_2.model.order.DataOrder;
import com.example.login_form_2.model.order.Donhang;
import com.example.login_form_2.model.order.GetOrderByUserReponse;
import com.example.login_form_2.retrofit.APIClient;
import com.example.login_form_2.retrofit_interface.OrderServices;
import com.example.login_form_2.store.GlobalStore;
import com.example.login_form_2.utils.Alert;
import com.example.login_form_2.utils.LoadingDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_ORDER_INFO = 1;
    Context that = this;
    ListView listView;
    OrderAdapter orderAdapter ;

    ArrayList<DataOrder> dataOrders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        dataOrders = new ArrayList<>();
        addControl();
        getDataOrder();
        addEvent();

    }

    private void addEvent() {
        listView.setOnItemClickListener((parent, view, position, id) -> {

            try {
                Donhang donHang = dataOrders.get(position).donhang;
                Intent intent = new Intent(that, OrderInfoActivity.class);
                intent.putExtra("donHang", donHang);
                startActivityForResult(intent, REQUEST_CODE_ORDER_INFO);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ORDER_INFO) {
            if (resultCode == RESULT_OK) {
                getDataOrder();
            }
        }
    }

    private void getDataOrder() {
        LoadingDialog.setLoading(that,true);
        ;
        Call<GetOrderByUserReponse> call = APIClient.getClient().create(OrderServices.class).getAllOrderOfUser(Integer.parseInt(GlobalStore.currentUser.id));
        call.enqueue(new Callback<GetOrderByUserReponse>() {
            @Override
            public void onResponse(Call<GetOrderByUserReponse> call, Response<GetOrderByUserReponse> response) {

                LoadingDialog.setLoading(that,false);
                if(response.code() == 200 && response.isSuccessful() && response.body() != null){

                    dataOrders.clear();
                    dataOrders.addAll(response.body().data);
                    if(dataOrders.size() > 0){
                        orderAdapter = new OrderAdapter(that, R.layout.item_order,dataOrders);
                        listView.setAdapter(orderAdapter);
                    }
                    else{
                        Alert.confirm(that, "Bạn chưa có đơn hàng nào", new Alert.OnDialogButtonClickListener() {
                            @Override
                            public void onPositiveButtonClick() {
                                finish();
                            }

                            @Override
                            public void onNegativeButtonClick() {
                                finish();
                            }
                        });
                    }
                }
                else{
                    Alert.alert(that,"Lỗi giỏ hàng");
                }
            }

            @Override
            public void onFailure(Call<GetOrderByUserReponse> call, Throwable t) {
                LoadingDialog.setLoading(that,false);
                t.printStackTrace();
                Alert.alert(that,t.getMessage());
            }
        });
    }

    private void addControl() {
        listView = findViewById(R.id.lvOrder);
//        orderAdapter = new OrderAdapter(that, R.layout.item_order,dataOrders);
//        listView.setAdapter(orderAdapter);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}