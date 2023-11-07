package com.example.login_form_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.login_form_2.Activity.CartActivity;
import com.example.login_form_2.adapter.CartAdapter;
import com.example.login_form_2.adapter.admin.ADLoaiSanPhamAdapter;
import com.example.login_form_2.databinding.ActivityQlloaiSanPhamBinding;
import com.example.login_form_2.model.LoaiSanPham;
import com.example.login_form_2.model.category.GetCategoryReponse;
import com.example.login_form_2.retrofit.APIClient;
import com.example.login_form_2.retrofit_interface.CategoryServices;
import com.example.login_form_2.store.GlobalStore;
import com.example.login_form_2.utils.Alert;
import com.example.login_form_2.utils.LoadingDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class QLLoaiSanPham extends AppCompatActivity {
    Context that = this;
    private ADLoaiSanPhamAdapter adapter;
    private ArrayList<LoaiSanPham> loaiSanPhams;

    private ListView lvQLLoai;
            Button btnThemLoai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_qlloai_san_pham);
        addControl();
        addEvent();
    }
    static final int REQUEST_CODE = 1;
    private void addEvent() {
        btnThemLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(that, InfoLoaiSPActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        lvQLLoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //  Alert.alert(that,"Đang nhấn vào " + position);
                LoaiSanPham loai = loaiSanPhams.get(position);
                Intent intent = new Intent(that, InfoLoaiSPActivity.class);
                intent.putExtra("loai",loai);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                getDataLoaiSanPham();
            }
        }
    }


    private void addControl() {
        lvQLLoai = findViewById(R.id.lvQLLoaiSanPham);
        btnThemLoai = findViewById(R.id.btnThemMoiLoaiSanPham);
        getDataLoaiSanPham();
    }

    private void getDataLoaiSanPham() {
        LoadingDialog.setLoading(that, true);
        Call<GetCategoryReponse> call = APIClient.getClient().create(CategoryServices.class).getAllLoaiSanPham();
        call.enqueue(new Callback<GetCategoryReponse>() {
            @Override
            public void onResponse(Call<GetCategoryReponse> call, retrofit2.Response<GetCategoryReponse> response) {
                LoadingDialog.setLoading(that, false);
                if (response.isSuccessful() && response.body() != null) {
                    GetCategoryReponse reponse = response.body();
                    System.out.println(reponse.toString());
                    GlobalStore.currentArrLoaiSanPham = response.body().data;
                    loaiSanPhams = response.body().data;
                    adapter = new ADLoaiSanPhamAdapter(that, R.layout.activity_qlloai_san_pham,loaiSanPhams);
                    lvQLLoai.setAdapter(adapter);
                } else {
                    Alert.alert(that, "Lỗi get dữ liệu");
                }
            }

            @Override
            public void onFailure(Call<GetCategoryReponse> call, Throwable t) {
                LoadingDialog.setLoading(that, false);
                t.printStackTrace();
            }
        });
    }


}