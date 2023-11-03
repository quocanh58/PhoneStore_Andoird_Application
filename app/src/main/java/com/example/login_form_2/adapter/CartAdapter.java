package com.example.login_form_2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.login_form_2.CartActivity;
import com.example.login_form_2.R;
import com.example.login_form_2.model.cart.DataCart;
import com.example.login_form_2.utils.Function;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends ArrayAdapter<DataCart> {

    private final Context context;
    private final int resource;
    private static List<DataCart> objects;

    public CartAdapter(@NonNull Context context, int resource, @NonNull List<DataCart> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    public static double getTotalPriceCart(){
        int sum = 0;
        for(DataCart dataCart : objects){
            if(dataCart.isChecked){
                sum += Function.getIntNumber(dataCart.quantity) * Function.getDoubleNumber(dataCart.product.giasanpham);
            }
        }
        return sum ;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        }
        DataCart dataCart = objects.get(position);
        CheckBox checkcart = convertView.findViewById(R.id.checkCart);
        checkcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dataCart.isChecked = checkcart.isChecked();
                CartActivity.updateTotal(getTotalPriceCart());
            }
        });
        ImageView imgProduct   = convertView.findViewById(R.id.imgCart);
        TextView title   = convertView.findViewById(R.id.txtTileCart);
        TextView price   = convertView.findViewById(R.id.txtGiaCart);
        TextView quantity   = convertView.findViewById(R.id.txtSlCart);
        if(dataCart.isChecked){
            checkcart.setChecked(true);
        }
        else{
            checkcart.setChecked(false);
        }
        quantity.setText(dataCart.quantity);
        Picasso.get()
                .load(dataCart.product.hinhanhsanpham)
                .into(imgProduct
                );
        title.setText(dataCart.product.tensanpham);
        price.setText(Function.formatCurrency(Function.getDoubleNumber(dataCart.product.giasanpham)));
        return convertView;
    }
}
