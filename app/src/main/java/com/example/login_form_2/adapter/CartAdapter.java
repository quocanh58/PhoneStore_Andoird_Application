package com.example.login_form_2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.login_form_2.CartActivity;
import com.example.login_form_2.R;
import com.example.login_form_2.model.cart.CartRequest;
import com.example.login_form_2.model.cart.DataCart;
import com.example.login_form_2.model.cart.GetCartReponse;
import com.example.login_form_2.retrofit.APIClient;
import com.example.login_form_2.retrofit_interface.CartServices;
import com.example.login_form_2.store.GlobalStore;
import com.example.login_form_2.utils.Alert;
import com.example.login_form_2.utils.Function;
import com.example.login_form_2.utils.LoadingDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        ImageView imgProduct   = convertView.findViewById(R.id.imgCart);
        TextView title   = convertView.findViewById(R.id.txtTileCart);
        TextView price   = convertView.findViewById(R.id.txtGiaCart);
        TextView quantity   = convertView.findViewById(R.id.txtSlCart);
        Button plus = convertView.findViewById(R.id.btnPlusItem);
        Button sub = convertView.findViewById(R.id.btnSubItem);
        Button remove = convertView.findViewById(R.id.btnRemoveItemCart);


        quantity.setText(dataCart.quantity);
        Picasso.get()
                .load(dataCart.product.hinhanhsanpham)
                .into(imgProduct
                );
        title.setText(dataCart.product.tensanpham);
        price.setText(Function.formatCurrency(Function.getDoubleNumber(dataCart.product.giasanpham)));


        // sự kiện
        if(dataCart.isChecked){
            checkcart.setChecked(true);
            CartActivity.dataCartsSeleted.put(dataCart,"");
        }
        else{
            checkcart.setChecked(false);
            CartActivity.dataCartsSeleted.remove(dataCart);
        }
        checkcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataCart.isChecked = checkcart.isChecked();
                CartActivity.updateTotal(getTotalPriceCart());
                if(!checkcart.isChecked()){
                    CartActivity.dataCartsSeleted.remove(dataCart);
                }
                else{
                    CartActivity.dataCartsSeleted.put(dataCart,"");
                }
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingDialog.setLoading(v.getContext(), true);
                CartRequest request = new CartRequest();
                request.type = "delete";
                request.cartID = Function.getIntNumber( dataCart.id);
                request.userID = Function.getIntNumber( GlobalStore.currentUser.id);
                Call<GetCartReponse> call = APIClient.getClient().create(CartServices.class).deleteProductFromCart(request);
                call.enqueue(new Callback<GetCartReponse>() {
                    @Override
                    public void onResponse(Call<GetCartReponse> call, Response<GetCartReponse> response) {
                        LoadingDialog.setLoading(v.getContext(), false);
                        if(response.isSuccessful() && response.body() != null && response.body().result == 1){
                            GlobalStore.currentDataCart = response.body().data;
                            CartActivity.UpdateListView();
                            Alert.alert(getContext(),response.body().message);

                        }
                    }

                    @Override
                    public void onFailure(Call<GetCartReponse> call, Throwable t) {
                        t.printStackTrace();
                        LoadingDialog.setLoading(v.getContext(), false);
                    }
                });
            }
        });


        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Function.getIntNumber(dataCart.quantity) + 1;
                if(quantity < 0){
                    return;
                }

                CartRequest cartRequest = new CartRequest();
                cartRequest.type = "update";
                cartRequest.cartID = Integer.parseInt(dataCart.id);
                cartRequest.quantity = quantity;
                cartRequest.userID = Integer.parseInt(GlobalStore.currentUser.id);
                Call<GetCartReponse> call = APIClient.getClient().create(CartServices.class).deleteProductFromCart(cartRequest);
                call.enqueue(new Callback<GetCartReponse>() {
                    @Override
                    public void onResponse(Call<GetCartReponse> call, Response<GetCartReponse> response) {
                        if(response.body().result == 1){
                            GlobalStore.currentDataCart = response.body().data;
                            CartActivity.UpdateListView();
                        }
                        else{
                            Alert.alert(getContext(),response.body().message);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetCartReponse> call, Throwable t) {

                    }
                });
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Function.getIntNumber(dataCart.quantity) - 1;
                if(quantity < 0){
                    return;
                }

                CartRequest cartRequest = new CartRequest();
                cartRequest.type = "update";
                cartRequest.cartID = Integer.parseInt(dataCart.id);
                cartRequest.quantity = quantity;
                cartRequest.userID = Integer.parseInt(GlobalStore.currentUser.id);
                Call<GetCartReponse> call = APIClient.getClient().create(CartServices.class).deleteProductFromCart(cartRequest);
                call.enqueue(new Callback<GetCartReponse>() {
                    @Override
                    public void onResponse(Call<GetCartReponse> call, Response<GetCartReponse> response) {
                        if(response.code() == 200 && response.isSuccessful() && response.body() != null){
                            if(response.body().result == 1){
                                GlobalStore.currentDataCart = response.body().data;
                                CartActivity.UpdateListView();
                            }
                            else{
                                Alert.alert(getContext(),response.body().message);
                            }
                        }
                        else{
                            Alert.alert(context,"Lỗi");
                        }
                    }

                    @Override
                    public void onFailure(Call<GetCartReponse> call, Throwable t) {

                    }
                });
            }
        });
        return convertView;
    }
}
