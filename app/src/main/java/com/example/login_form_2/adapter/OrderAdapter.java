package com.example.login_form_2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.login_form_2.R;
import com.example.login_form_2.model.cart.DataCart;
import com.example.login_form_2.model.order.Chitiet;
import com.example.login_form_2.model.order.DataOrder;
import com.example.login_form_2.model.order.Donhang;
import com.example.login_form_2.model.order.GetOrderByUserReponse;
import com.example.login_form_2.utils.Alert;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends ArrayAdapter<DataOrder> {
    private final Context context;
    private final int resource;
    private static List<DataOrder> objectss;

    public OrderAdapter(@NonNull Context context, int resource, @NonNull List<DataOrder> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        objectss = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        }
        DataOrder dataOrder = objectss.get(position);

        ImageView img = convertView.findViewById(R.id.imgItemOrder);
        TextView txtTitleItemOrder = convertView.findViewById(R.id.txtTitleItemOrder);
        TextView txtOrderItemQuantity = convertView.findViewById(R.id.txtOrderItemQuantity);
        TextView txtItemOrderPrice = convertView.findViewById(R.id.txtItemOrderPrice);
        TextView txtItemOrderTotalProduct = convertView.findViewById(R.id.txtItemOrderTotalProduct);
        TextView txtItemStatusOrder = convertView.findViewById(R.id.txtItemStatusOrder);
        Button btnItemOrderDanhGia = convertView.findViewById(R.id.btnItemOrderDanhGia);
        Picasso.get()
                .load(dataOrder.donhang.chitiet.get(0).sanpham.hinhanhsanpham)
                .into(img
                );
        txtTitleItemOrder.setText(dataOrder.donhang.chitiet.get(0).sanpham.tensanpham);
        txtOrderItemQuantity.setText("x" + dataOrder.donhang.chitiet.get(0).soluong);
        txtItemOrderTotalProduct.setText(dataOrder.donhang.chitiet.size() + " sản phẩm");
        txtItemStatusOrder.setText(dataOrder.donhang.status);

        txtItemOrderPrice.setText(dataOrder.donhang.chitiet.get(0).sanpham.giasanpham);
        btnItemOrderDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alert.alert(v.getContext(), "Chức năng đang phát triển");
            }
        });
        return convertView;
    }
}
