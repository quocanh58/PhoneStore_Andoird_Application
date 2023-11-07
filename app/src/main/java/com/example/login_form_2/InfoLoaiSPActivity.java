package com.example.login_form_2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.login_form_2.model.LoaiSanPham;
import com.example.login_form_2.model.category.GetCategoryReponse;
import com.example.login_form_2.model.category.GetCategoryRequest;
import com.example.login_form_2.retrofit.APIClient;
import com.example.login_form_2.retrofit_interface.CategoryServices;
import com.example.login_form_2.utils.Alert;
import com.example.login_form_2.utils.LoadingDialog;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoLoaiSPActivity extends AppCompatActivity {
    Context that = this;
    EditText txtTenLoai,txtHinhanh;
    ImageView imgHinhAnh;
    LoaiSanPham loaiSanPham = null;

    Button btnSua, btnXoa, btnThemmoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_loai_spactivity);
        addControl();
        loaiSanPham = (LoaiSanPham) getIntent().getSerializableExtra("loai");
        if(loaiSanPham != null){
            btnThemmoi.setVisibility(View.GONE);
            btnXoa.setVisibility(View.VISIBLE);
            btnSua.setVisibility(View.VISIBLE);
            txtTenLoai.setText(loaiSanPham.tenLoaisp);
            txtHinhanh.setText(loaiSanPham.hinhAnhLoaisp);
            Picasso.get().load(loaiSanPham.hinhAnhLoaisp).into(imgHinhAnh);
        }
        addEvent();
    }

    private void addEvent() {
        txtHinhanh.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                     String url = txtHinhanh.getText().toString();
                     Picasso.get().load(url).into(imgHinhAnh);

                    return true;
                }
                return false;
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alert.confirm(that, "Bạn có có chắc chắn muốn sủa không", new Alert.OnDialogButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick() {
                        GetCategoryRequest request = new GetCategoryRequest();
                        request.type= "update";
                        request.id = (int) loaiSanPham.Id;
                        request.hinhanhloaisanpham = txtHinhanh.getText().toString();
                        request.tenloaisanpham = txtTenLoai.getText().toString();
                        Call<GetCategoryReponse> call = APIClient.getClient().create(CategoryServices.class).updateCategory(request);
                        call.enqueue(new Callback<GetCategoryReponse>() {
                            @Override
                            public void onResponse(Call<GetCategoryReponse> call, Response<GetCategoryReponse> response) {
                                if(response.code() == 200 && response.isSuccessful() && response.body() != null ){
                                    Alert.alert(that,response.body().message);
                                }
                                else{
                                    Alert.alert(that,"Đã cập nhật thất bại");
                                }
                            }

                            @Override
                            public void onFailure(Call<GetCategoryReponse> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onNegativeButtonClick() {

                    }
                });
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alert.confirm(that, "Bạn có muốn xoá sản phẩm này", new Alert.OnDialogButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick() {
                        GetCategoryRequest request = new GetCategoryRequest();
                        request.id = (int) loaiSanPham.Id;
                        request.type = "delete";
                        Call<GetCategoryReponse> call = APIClient.getClient().create(CategoryServices.class).DeletaCategory(request);
                        call.enqueue(new Callback<GetCategoryReponse>() {
                            @Override
                            public void onResponse(Call<GetCategoryReponse> call, Response<GetCategoryReponse> response) {
                                if(response.code() == 200 && response.isSuccessful() && response.body() != null){
                                    Toast.makeText(that, response.body().message, Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }
                            }

                            @Override
                            public void onFailure(Call<GetCategoryReponse> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onNegativeButtonClick() {

                    }
                });
            }
        });

        btnThemmoi.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LoadingDialog.setLoading(that,true);
                GetCategoryRequest request = new GetCategoryRequest();
                request.hinhanhloaisanpham = txtHinhanh.getText().toString();
                request.tenloaisanpham = txtTenLoai.getText().toString();
                request.type = "insert";
                Call<GetCategoryReponse> call = APIClient.getClient().create(CategoryServices.class).insertCategory(request);
                call.enqueue(new Callback<GetCategoryReponse>() {
                    @Override
                    public void onResponse(Call<GetCategoryReponse> call, Response<GetCategoryReponse> response) {
                        LoadingDialog.setLoading(that,false);
                        if(response.code() == 200 && response.isSuccessful() && response.body() != null){
                            Alert.alert(that,response.body().message);
                            onBackPressed();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetCategoryReponse> call, Throwable t) {
                        LoadingDialog.setLoading(that,false);
                        t.printStackTrace();
                    }
                });
            }
        });
    }

    private void addControl() {
        txtHinhanh = findViewById(R.id.txtHinhanhLoaiSP);
        txtTenLoai = findViewById(R.id.txtTenLoaiSP);
        imgHinhAnh = findViewById(R.id.imgInfoLoaiSP);
        btnSua = findViewById(R.id.btnSuaLoai);
        btnXoa = findViewById(R.id.btnXoaLoai);
        btnThemmoi = findViewById(R.id.btnThemMoiLoai);
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }

}