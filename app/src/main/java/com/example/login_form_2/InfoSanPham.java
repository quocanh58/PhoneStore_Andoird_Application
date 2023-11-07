package com.example.login_form_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.login_form_2.model.LoaiSanPham;
import com.example.login_form_2.model.Product;
import com.example.login_form_2.model.product.GetProductReponse;
import com.example.login_form_2.model.product.ProductRequest;
import com.example.login_form_2.retrofit.APIClient;
import com.example.login_form_2.retrofit_interface.ProductServices;
import com.example.login_form_2.store.GlobalStore;
import com.example.login_form_2.utils.Alert;
import com.example.login_form_2.utils.LoadingDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoSanPham extends AppCompatActivity {
    Context that = this;
    Product product = null;
    Button btnInfoThemMoiProduct,btnInfoSuaProduct,btnInfoXoaroduct;
    EditText txtInfoTitleProduct,txtInfoHinhAnhProduct,txtInfoGiaProduct,txtInfoMoTaProduct;


    Spinner spinnerInfoLoaiProduct;
    ImageView imgInfoAnhSanPham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_san_pham);
        addControl();
        addEvent();
    }

    private void addEvent() {
        txtInfoHinhAnhProduct.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String url = txtInfoHinhAnhProduct.getText().toString();
                    Picasso.get().load(url).into(imgInfoAnhSanPham);

                    return true;
                }
                return false;
            }
        });

        btnInfoThemMoiProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingDialog.setLoading(that,true);
                ProductRequest request = new ProductRequest();
                request.type = "insert";
                request.tensanpham = txtInfoTitleProduct.getText().toString();
                request.hinhanhsanpham = txtInfoHinhAnhProduct.getText().toString();
                request.giasanpham = Integer.parseInt(txtInfoGiaProduct.getText().toString());
                request.mota = txtInfoMoTaProduct.getText().toString();
                request.idsanpham = Integer.parseInt(spinnerInfoLoaiProduct.getSelectedItem().toString().split("|")[0]);

                Call<GetProductReponse> call = APIClient.getClient().create(ProductServices.class).insertSanPham(request);
                call.enqueue(new Callback<GetProductReponse>() {
                    @Override
                    public void onResponse(Call<GetProductReponse> call, Response<GetProductReponse> response) {
                        LoadingDialog.setLoading(that,false);
                        if(response.isSuccessful() && response.body() != null && response.body().result == 1){
                            Alert.alert(that,response.body().message);
                        }
                        else{
                            Alert.error(that,"Đã có lôi xẩy ra khi thêm sản phẩm");
                        }
                    }

                    @Override
                    public void onFailure(Call<GetProductReponse> call, Throwable t) {
                        LoadingDialog.setLoading(that,false);
                        t.printStackTrace();
                    }
                });
            }
        });

        btnInfoSuaProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingDialog.setLoading(that,true);
                ProductRequest request = new ProductRequest();
                request.type = "update";
                request.Id = Integer.parseInt(product.id);
                request.tensanpham = txtInfoTitleProduct.getText().toString();
                request.hinhanhsanpham = txtInfoHinhAnhProduct.getText().toString();
                request.giasanpham = Integer.parseInt(txtInfoGiaProduct.getText().toString());
                request.mota = txtInfoMoTaProduct.getText().toString();
                request.idsanpham = Integer.parseInt(spinnerInfoLoaiProduct.getSelectedItem().toString().split("|")[0]);

                Call<GetProductReponse> call = APIClient.getClient().create(ProductServices.class).insertSanPham(request);
                call.enqueue(new Callback<GetProductReponse>() {
                    @Override
                    public void onResponse(Call<GetProductReponse> call, Response<GetProductReponse> response) {
                        LoadingDialog.setLoading(that,false);
                        if(response.isSuccessful() && response.body() != null && response.body().result == 1){
                            Alert.alert(that,response.body().message);
                        }
                        else{
                            Alert.error(that,"Đã có lôi xẩy ra khi sửa sản phẩm");
                        }
                    }

                    @Override
                    public void onFailure(Call<GetProductReponse> call, Throwable t) {
                        LoadingDialog.setLoading(that,false);
                        t.printStackTrace();
                    }
                });
            }
        });

        btnInfoXoaroduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alert.confirm(that, "Bạn có thực sự muốn xoá không", new Alert.OnDialogButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick() {
                        LoadingDialog.setLoading(that,true);
                        ProductRequest request = new ProductRequest();
                        request.type = "delete";
                        request.Id = Integer.parseInt(product.id);
                        Call<GetProductReponse> call = APIClient.getClient().create(ProductServices.class).insertSanPham(request);
                        call.enqueue(new Callback<GetProductReponse>() {
                            @Override
                            public void onResponse(Call<GetProductReponse> call, Response<GetProductReponse> response) {
                                LoadingDialog.setLoading(that,false);
                                if(response.isSuccessful() && response.body() != null && response.body().result == 1){
                                    Alert.alert(that,response.body().message);
                                }
                                else{
                                    Alert.error(that,"Đã có lôi xẩy ra khi xoá sản phẩm");
                                }
                            }
                            @Override
                            public void onFailure(Call<GetProductReponse> call, Throwable t) {
                                LoadingDialog.setLoading(that,false);
                                t.printStackTrace();
                            }
                        });
                    }

                    @Override
                    public void onNegativeButtonClick() {

                    }
                });
            }
        });

    }

    private void addControl() {
        btnInfoSuaProduct = findViewById(R.id.btnInfoSuaProduct);
        btnInfoThemMoiProduct = findViewById(R.id.btnInfoThemMoiProduct);
        btnInfoXoaroduct = findViewById(R.id.btnInfoXoaroduct);

        txtInfoTitleProduct = findViewById(R.id.txtInfoTitleProduct);
        txtInfoHinhAnhProduct = findViewById(R.id.txtInfoHinhAnhProduct);
        txtInfoGiaProduct = findViewById(R.id.txtInfoGiaProduct);
        txtInfoMoTaProduct = findViewById(R.id.txtInfoMoTaProduct);

        imgInfoAnhSanPham = findViewById(R.id.imgInfoAnhSanPham);

        spinnerInfoLoaiProduct = findViewById(R.id.spinnerInfoLoaiProduct);

        List<String> values = new ArrayList<>();
        values.add("----Loại sản phẩm----");
        for(LoaiSanPham loaiSanPham : GlobalStore.currentArrLoaiSanPham){
            String loai = loaiSanPham.Id + "|" + loaiSanPham.tenLoaisp;
            values.add(loai);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInfoLoaiProduct.setAdapter(adapter);
        spinnerInfoLoaiProduct.setSelection(0);

        product = (Product) getIntent().getSerializableExtra("product");
        if(product != null){
            btnInfoThemMoiProduct.setVisibility(View.GONE);
            btnInfoSuaProduct.setVisibility(View.VISIBLE);
            btnInfoXoaroduct.setVisibility(View.VISIBLE);

            txtInfoTitleProduct.setText(product.tensanpham);
            txtInfoHinhAnhProduct.setText(product.hinhanhsanpham);
            txtInfoGiaProduct.setText(product.giasanpham);
            txtInfoMoTaProduct.setText(product.mota);

            Picasso.get().load(product.hinhanhsanpham).into(imgInfoAnhSanPham);

            for(int i = 0 ; i < values.size() ;i++){
                if(values.get(i).split("|")[0].equals(product.idloaisanpham)){
                    spinnerInfoLoaiProduct.setSelection(i);
                    break;
                }
            }

        }
    }


    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
        super.onBackPressed();

    }

}