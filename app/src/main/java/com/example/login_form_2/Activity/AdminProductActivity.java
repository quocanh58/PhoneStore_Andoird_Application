package com.example.login_form_2.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.login_form_2.InfoSanPham;
import com.example.login_form_2.R;
import com.example.login_form_2.adapter.ProductAdapter;
import com.example.login_form_2.adapter.admin.ADSanPhamAdapater;
import com.example.login_form_2.model.LoaiSanPham;
import com.example.login_form_2.model.Product;
import com.example.login_form_2.model.category.GetCategoryReponse;
import com.example.login_form_2.model.product.GetProductReponse;
import com.example.login_form_2.retrofit.APIClient;
import com.example.login_form_2.retrofit_interface.CategoryServices;
import com.example.login_form_2.retrofit_interface.ProductServices;
import com.example.login_form_2.store.GlobalStore;
import com.example.login_form_2.utils.Alert;
import com.example.login_form_2.utils.LoadingDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminProductActivity extends AppCompatActivity {
    Context that = this;
    ListView listView;
    Button btnThem;

    ADSanPhamAdapater adapter;
    private ArrayList<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_product_admin);
        addControl();
        addEvent();
    }


    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    getDataLoaiSanPham();
                    getDataSanPham();
                }
            });

    private void addEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Product product = productList.get(position);
                    Intent intent = new Intent(that, InfoSanPham.class);
                    intent.putExtra("product",product);
                    someActivityResultLauncher.launch(intent);
                }
                catch (Exception e){

                }
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(that, InfoSanPham.class);
                someActivityResultLauncher.launch(intent);
            }
        });
    }

    private void addControl() {
        listView = findViewById(R.id.lvProductItemAdmin);
        btnThem = findViewById(R.id.btnAdminAddProductItem);
        getDataLoaiSanPham();
        getDataSanPham();


    }


    private void getDataSanPham() {
        LoadingDialog.setLoading(that,true);
        Call<GetProductReponse> call =  APIClient.getClient().create(ProductServices.class).getAllSanPham();
        call.enqueue(new Callback<GetProductReponse>() {
            @Override
            public void onResponse(Call<GetProductReponse> call, Response<GetProductReponse> response) {
                LoadingDialog.setLoading(that,false);
                if(response.isSuccessful() && response.body() != null){
                    if(response.body().result == 1){
                        productList = response.body().data;
                        adapter = new ADSanPhamAdapater(that,R.layout.item_admin_product,productList);
                        listView.setAdapter(adapter);
                    }
                    else{
                        Alert.alert(that, "Không lấy được sản phẩm");
                        productList = response.body().data;

                    }
                }
            }

            @Override
            public void onFailure(Call<GetProductReponse> call, Throwable t) {
                LoadingDialog.setLoading(that,false);
                Alert.alert(that,t.getMessage());
            }
        });
    }


    private void getDataLoaiSanPham() {
        LoadingDialog.setLoading(that,true);
        Call<GetCategoryReponse> call = APIClient.getClient().create(CategoryServices.class).getAllLoaiSanPham();
        call.enqueue(new Callback<GetCategoryReponse>() {
            @Override
            public void onResponse(Call<GetCategoryReponse> call, retrofit2.Response<GetCategoryReponse> response) {
                LoadingDialog.setLoading(that,false);
                if(response.isSuccessful() && response.body() != null){
                    GetCategoryReponse reponse = response.body();
                    GlobalStore.currentArrLoaiSanPham = response.body().data;

                }
                else{
                    Alert.alert(that,"Lỗi get dữ liệu");
                }

            }

            @Override
            public void onFailure(Call<GetCategoryReponse> call, Throwable t) {
                LoadingDialog.setLoading(that,false);
                t.printStackTrace();
            }
        });
    }

}