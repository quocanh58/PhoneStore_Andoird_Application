package com.example.login_form_2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.login_form_2.R;
import com.example.login_form_2.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

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
        holder.txtGia.setText(product.giasanpham);
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
        TextView txtTitle, txtGia;
        ImageView imgAnhSanPham;

        public ViewHolder(View itemView) {
            super(itemView);
            // Ánh xạ các thành phần giao diện vào biến
            // Ví dụ: imageView = itemView.findViewById(R.id.imageView);
            //        titleTextView = itemView.findViewById(R.id.titleTextView);
            //        ...
            txtGia = itemView.findViewById(R.id.txtGiaProduct);
            txtTitle = itemView.findViewById(R.id.txtTitleProduct);
            imgAnhSanPham = itemView.findViewById(R.id.imgProduct);

        }
    }
}
