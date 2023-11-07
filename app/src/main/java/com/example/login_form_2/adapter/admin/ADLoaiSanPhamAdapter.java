package com.example.login_form_2.adapter.admin;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.login_form_2.InfoLoaiSPActivity;
import com.example.login_form_2.R;
import com.example.login_form_2.model.LoaiSanPham;
import com.example.login_form_2.model.category.GetCategoryReponse;
import com.example.login_form_2.model.category.GetCategoryRequest;
import com.example.login_form_2.retrofit.APIClient;
import com.example.login_form_2.retrofit_interface.CategoryServices;
import com.example.login_form_2.utils.Alert;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ADLoaiSanPhamAdapter extends ArrayAdapter<LoaiSanPham> {
    private final Context context;
    private final int resource;
    private final List<LoaiSanPham> objects;

    public ADLoaiSanPhamAdapter(@NonNull Context context, int resource, @NonNull List<LoaiSanPham> objects) {
        super(context, resource, objects);
        this.context= context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quanly_loai_sanpham, parent, false);
        }
        LoaiSanPham loai = objects.get(position);
        TextView txtAdminQLLoaiSPTitle = convertView.findViewById(R.id.txtAdminQLLoaiSPTitle);
        ImageView imgAdminQLLoaiSP = convertView.findViewById(R.id.imgAdminQLLoaiSP);
        Button btnQuanLyLoaiSPSua = convertView.findViewById(R.id.btnQuanLyLoaiSPSua);
        Button  btnQuanLyLoaiSPXoa = convertView.findViewById(R.id.btnQuanLyLoaiSPXoa);



        Picasso.get().load(loai.hinhAnhLoaisp).into(imgAdminQLLoaiSP);
        txtAdminQLLoaiSPTitle.setText(loai.tenLoaisp);
        btnQuanLyLoaiSPSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiSanPham loai = objects.get(position);
                Intent intent = new Intent(v.getContext(), InfoLoaiSPActivity.class);
                intent.putExtra("loai",loai);
                ((Activity) context).startActivityForResult(intent, 1);
            }
        });
        btnQuanLyLoaiSPXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alert.confirm(v.getContext(), "Bạn có muốn xoá sản phẩm này", new Alert.OnDialogButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick() {




                        GetCategoryRequest request = new GetCategoryRequest();
                        request.id = (int) loai.Id;
                        request.type = "delete";

                        Call<GetCategoryReponse> call = APIClient.getClient().create(CategoryServices.class).DeletaCategory(request);
                        call.enqueue(new Callback<GetCategoryReponse>() {
                            @Override
                            public void onResponse(Call<GetCategoryReponse> call, Response<GetCategoryReponse> response) {
                                if(response.code() == 200 && response.isSuccessful() && response.body() != null){
                                    notifyDataSetChanged();
                                    Toast.makeText(v.getContext(), response.body().message, Toast.LENGTH_SHORT).show();


                                    LoaiSanPham loaiToRemove = objects.get(position);
                                    objects.remove(loaiToRemove);

                                    // Update the ListView
                                    notifyDataSetChanged();
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
        return convertView;
    }


}
