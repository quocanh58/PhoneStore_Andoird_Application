package com.example.login_form_2.adapter.admin;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.login_form_2.InfoSanPham;
import com.example.login_form_2.R;
import com.example.login_form_2.model.LoaiSanPham;
import com.example.login_form_2.model.Product;
import com.example.login_form_2.model.product.GetProductReponse;
import com.example.login_form_2.model.product.ProductRequest;
import com.example.login_form_2.retrofit.APIClient;
import com.example.login_form_2.retrofit_interface.ProductServices;
import com.example.login_form_2.utils.Alert;
import com.example.login_form_2.utils.LoadingDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ADSanPhamAdapater extends ArrayAdapter<Product> {

    private final Context context;
    private final int resource;
    private final List<Product> objects;

    public ADSanPhamAdapater(@NonNull Context context, int resource, @NonNull List<Product> objects) {
        super(context, resource, objects);
        this.context= context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_product, parent, false);
        }

        Product product = objects.get(position);
        ImageView item_admin_product_img = convertView.findViewById(R.id.item_admin_product_img);
        TextView item_admin_product_title = convertView.findViewById(R.id.item_admin_product_title);
        TextView item_admin_product_categoryName = convertView.findViewById(R.id.item_admin_product_categoryName);
        TextView item_admin_product_price = convertView.findViewById(R.id.item_admin_product_price);
        Button item_admin_product_btnSua = convertView.findViewById(R.id.item_admin_product_btnSua);
        Button item_admin_product_btnXoa = convertView.findViewById(R.id.item_admin_product_btnXoa);
        if(product != null){
            Picasso.get().load(product.hinhanhsanpham).into(item_admin_product_img);
            item_admin_product_title.setText(product.tensanpham);
            item_admin_product_categoryName.setText(LoaiSanPham.getNameofCategory(Long.parseLong(product.id)));
            item_admin_product_price.setText(product.giasanpham);
            item_admin_product_btnSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Product product = objects.get(position);
                        Intent intent = new Intent(v.getContext(), InfoSanPham.class);
                        intent.putExtra("product",product);
                        ((Activity) context).startActivityForResult(intent, 1);
                    }
                    catch (Exception e){

                    }
                }
            });
            item_admin_product_btnXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context that = v.getContext();
                    Alert.confirm(v.getContext(), "Bạn có thực sự muốn xoá không", new Alert.OnDialogButtonClickListener() {
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
                                        objects.remove(product);
                                        notifyDataSetChanged();
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
        return convertView;
    }
}
