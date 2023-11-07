package com.example.login_form_2;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class CustomImageDialog extends Dialog {

    private ImageView imageView;

    public CustomImageDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_layout);

        // Đảm bảo dialog có kích thước cố định và không thể huỷ bằng cách chạm vào bên ngoài
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        setCancelable(false);

        imageView = findViewById(R.id.imageView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Cài đặt tấm ảnh trong ImageView
        imageView.setImageResource(R.drawable.qr);

        // Sử dụng Handler để đóng dialog sau 30 giây
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss(); // Đóng dialog
            }
        }, 30000); // 30 giây
    }
}