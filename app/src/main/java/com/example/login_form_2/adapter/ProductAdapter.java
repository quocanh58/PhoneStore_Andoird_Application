package com.example.login_form_2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_form_2.R;
import com.example.login_form_2.model.Product;
import com.example.login_form_2.model.cart.CartRequest;
import com.example.login_form_2.model.cart.GetCartReponse;
import com.example.login_form_2.retrofit.APIClient;
import com.example.login_form_2.retrofit_interface.CartServices;
import com.example.login_form_2.store.GlobalStore;
import com.example.login_form_2.utils.Alert;
import com.example.login_form_2.utils.Function;
import com.example.login_form_2.utils.LoadingDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    private List<Product> productList; // Thay Product bằng lớp sản phẩm thực tế của bạn

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        // Gán dữ liệu sản phẩm vào các thành phần giao diện của thẻ sản phẩm
        // Ví dụ: holder.imageView.setImageResource(product.getImageResId());
        //        holder.titleTextView.setText(product.getTitle());
        //        ...

        holder.txtTitle.setText(product.tensanpham);
        holder.txtGia.setText(Function.formatCurrency(Function.getDoubleNumber(product.giasanpham)));
        holder.txtSLconLai.setText(product.soluong);
        String imageUrl = product.hinhanhsanpham; // Đường dẫn đến hình ảnh
        Picasso.get()
                .load(imageUrl)
                .into(holder.imgAnhSanPham
                );
        holder.itemView.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });
        holder.btnAddtoCart.setOnClickListener(v -> {
            LoadingDialog.setLoading(v.getContext(),true);
            CartRequest cartRequest = new CartRequest();

            cartRequest.type = "insert";
            cartRequest.userID = Integer.parseInt(GlobalStore.currentUser.id);
            cartRequest.productID = Function.getIntNumber(product.id);
            cartRequest.quantity = 1;

            Call<GetCartReponse> call = APIClient.getClient().create(CartServices.class).addProductToCart(cartRequest);
            call.enqueue(new Callback<GetCartReponse>() {
                @Override
                public void onResponse(Call<GetCartReponse> call, Response<GetCartReponse> response) {
                    LoadingDialog.setLoading(v.getContext(),false);
                    if(response.isSuccessful() && response.body() != null && response.body().result == 1){
                        GlobalStore.currentDataCart = response.body().data;
                        Alert.alert(v.getContext(), "Giỏ hàng", response.body().message);
                    }
                    else{
                        Alert.alert(v.getContext(), response.body().message);
                    }
                }

                @Override
                public void onFailure(Call<GetCartReponse> call, Throwable t) {
                    LoadingDialog.setLoading(v.getContext(),false);
                    t.printStackTrace();

                }
            });
           // Alert.alert(v.getContext(), "Thêm vào giỏ hàng","Chức năng đang phát trieern " + product.id);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Khai báo các thành phần giao diện cho sản phẩm (ImageView, TextView, ...)
        // Ví dụ: ImageView imageView;
        //        TextView titleTextView;
        //        ...
        TextView txtTitle, txtGia,txtSLconLai;
        ImageView imgAnhSanPham;
        Button btnAddtoCart;
        public ViewHolder(View itemView) {
            super(itemView);
            // Ánh xạ các thành phần giao diện vào biến
            // Ví dụ: imageView = itemView.findViewById(R.id.imageView);
            //        titleTextView = itemView.findViewById(R.id.titleTextView);
            //        ...
            txtGia = itemView.findViewById(R.id.txtGiaProduct);
            txtTitle = itemView.findViewById(R.id.txtTitleProduct);
            imgAnhSanPham = itemView.findViewById(R.id.imgProduct);
            btnAddtoCart = itemView.findViewById(R.id.addProductToCard);
            txtSLconLai = itemView.findViewById(R.id.txtsoLuongProduct);
        }
    }
}
