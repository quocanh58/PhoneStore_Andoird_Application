package com.example.login_form_2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.login_form_2.R;
import com.example.login_form_2.model.LoaiSanPham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaispAdapter extends BaseAdapter {
    ArrayList<LoaiSanPham> loaispsArr;
    Context context;

    public LoaispAdapter(ArrayList<LoaiSanPham> loaispsArr, Context context) {
        this.loaispsArr = loaispsArr;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (loaispsArr.size() == 0) {
            return 0;
        }
        return loaispsArr.size();
    }
    @Override
    public Object getItem(int i) {
        return loaispsArr.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder {
        TextView txtTenLoaiSp;
        ImageView imgLoaiSanPham;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) { //gán id vào
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.donglistview_loaisp, null);

            viewHolder.txtTenLoaiSp = view.findViewById(R.id.txtLoaiSanPham);
            viewHolder.imgLoaiSanPham = view.findViewById(R.id.imgLoaiSp);
            view.setTag(viewHolder);
        }else { // gán những id đã có dữ liệu ánh xạ
            viewHolder = (ViewHolder) view.getTag();
        }
        LoaiSanPham loaisp = (LoaiSanPham) getItem(i);
        viewHolder.txtTenLoaiSp.setText(loaisp.getTenLoaisp());
        Picasso.get().load(loaisp.getHinhAnhLoaisp())
                .placeholder(R.drawable.loadsuccess)
                .error(R.drawable.loadfail).into(viewHolder.imgLoaiSanPham);

        return view;
    }
}
