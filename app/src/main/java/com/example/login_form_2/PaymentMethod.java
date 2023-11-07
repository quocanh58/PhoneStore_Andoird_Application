package com.example.login_form_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.login_form_2.utils.Alert;

public class PaymentMethod extends AppCompatActivity {
    Button btnthanhtoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        btnthanhtoan = findViewById(R.id.btnPaymentMethod);
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alert.confirm(PaymentMethod.this, "Bạn có chắc chắn thanh toán không", new Alert.OnDialogButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick() {
                        Intent resultIntent = new Intent();
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }

                    @Override
                    public void onNegativeButtonClick() {

                        finish();
                    }
                });
            }
        });
    }
}