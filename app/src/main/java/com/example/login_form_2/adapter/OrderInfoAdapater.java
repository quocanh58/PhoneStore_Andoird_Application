package com.example.login_form_2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.login_form_2.R;
import com.example.login_form_2.model.LoaiSanPham;
import com.example.login_form_2.model.Product;
import com.example.login_form_2.model.order.Chitiet;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderInfoAdapater extends ArrayAdapter<Chitiet> {
    private final Context context;
    private final int resource;
    private final List<Chitiet> objects;

    public OrderInfoAdapater(@NonNull Context context, int resource, @NonNull List<Chitiet> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pay, parent, false);
        }

        Product product = objects.get(position).sanpham;
        ImageView imgPay = convertView.findViewById(R.id.imgPay);
        TextView txtTilePay = convertView.findViewById(R.id.txtTilePay);
        TextView txtLoaiSPPay = convertView.findViewById(R.id.txtLoaiSPPay);
        TextView txtPricePay = convertView.findViewById(R.id.txtPricePay);
        TextView txtQuantityPay = convertView.findViewById(R.id.txtQuantityPay);

        Picasso.get()
                .load(product.hinhanhsanpham)
                .into(imgPay);
        txtTilePay.setText(product.tensanpham);
        txtLoaiSPPay.setText(LoaiSanPham.getNameofCategory(Long.parseLong(product.idloaisanpham)));
        txtPricePay.setText(product.giasanpham);
        txtQuantityPay.setText(objects.get(position).soluong);

        return convertView;
    }
}
