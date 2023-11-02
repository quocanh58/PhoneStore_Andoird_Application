package com.example.login_form_2.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login_form_2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText edtUsername, edtPassword;
    Button btnLogin, btnSignUpEmail, btnCheckOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControl();
        addEvent();

    }

    private void addEvent() {
        btnCheckOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SendOTPActivity.class);
                startActivity(i);
            }
        });

        btnSignUpEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, VerifyEmailActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(edtUsername.getText().toString(),
                        edtPassword.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    if (mAuth.getCurrentUser().isEmailVerified()){
                                        Toast.makeText(LoginActivity.this, "Login with email successful.",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intents = new Intent(LoginActivity.this, DashboardActivity.class);
                                        startActivity(intents);
                                    }else {
                                        Toast.makeText(LoginActivity.this, "Please verify your email address.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(LoginActivity.this, "Email or password is incorrect.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void addControl() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin1);
        btnSignUpEmail = findViewById(R.id.btnSignUpEmail);
        btnCheckOTP = findViewById(R.id.btnCheckOTP);
    }
}