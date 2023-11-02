package com.example.login_form_2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.login_form_2.R;

public class SendOTPActivity extends AppCompatActivity {
    Context that = this;
    Button btnSignUp, btnBackLogin;
    EditText edtUsername, edtPassword, edtPasswordConfirm, edtPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);
        addControl();
        addEvent();
    }

    private void addEvent() {
//        String userName = edtUsername.getText().toString();
//        String password = edtPassword.getText().toString();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = edtPhone.getText().toString();
                Intent intent = new Intent(that, VerifyOTPActivity.class);
                intent.putExtra("sdt", phone);
                startActivity(intent);
            }
        });

        btnBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(that, LoginActivity.class));
            }
        });
    }

    private void addControl() {
        btnSignUp = findViewById(R.id.btnSignUp);
        edtPhone = findViewById(R.id.edtPhone);
        btnBackLogin = findViewById(R.id.btnBackLogin);
    }


}