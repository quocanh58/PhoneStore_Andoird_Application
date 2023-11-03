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
import com.example.login_form_2.model.cart.DataCart;
import com.example.login_form_2.utils.Function;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PayAdapter extends ArrayAdapter<DataCart> {

    private final Context context;
    private final int resource;
    private final List<DataCart> objects;

    public PayAdapter(@NonNull Context context, int resource, @NonNull List<DataCart> objects) {
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
        TextView txtTitle = convertView.findViewById(R.id.txtTilePay);
        TextView txtQuantity = convertView.findViewById(R.id.txtQuantityPay);
        TextView txtLoai = convertView.findViewById(R.id.txtLoaiSPPay);
        TextView txtPricePay = convertView.findViewById(R.id.txtPricePay);
        TextView txtQuantityPay = convertView.findViewById(R.id.txtQuantityPay);
        ImageView imgSp =  convertView.findViewById(R.id.imgPay);

        DataCart  dataCart = objects.get(position);
        txtTitle.setText(dataCart.product.tensanpham);
        txtQuantity.setText(dataCart.product.soluong);
        txtLoai.setText(LoaiSanPham.getNameofCategory(Function.getLongNumber(dataCart.product.idloaisanpham)));
        txtPricePay.setText(Function.formatCurrency(Function.getDoubleNumber(dataCart.product.giasanpham)));
        txtQuantityPay.setText(dataCart.quantity);
        Picasso.get()
                .load(dataCart.product.hinhanhsanpham)
                .into(imgSp
                );
        return convertView;
    }
}
