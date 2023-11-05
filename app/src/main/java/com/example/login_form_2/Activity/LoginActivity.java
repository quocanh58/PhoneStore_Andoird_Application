package com.example.login_form_2.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login_form_2.R;
import com.example.login_form_2.model.login.LoginReponse;
import com.example.login_form_2.model.login.LoginRequest;
import com.example.login_form_2.retrofit.APIClient;
import com.example.login_form_2.retrofit_interface.LoginServices;
import com.example.login_form_2.store.GlobalStore;
import com.example.login_form_2.utils.Alert;
import com.example.login_form_2.utils.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Context that = this;
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


        btnSignUpEmail.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(view -> {


            LoadingDialog.setLoading(that, true);
            mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(edtUsername.getText().toString(),
                            edtPassword.getText().toString())
                    .addOnFailureListener(e -> {
                        e.printStackTrace();
//                        Alert.alert(that,"Lỗi",e.getMessage());
                        LoadingDialog.setLoading(that, false);
                    })
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (mAuth.getCurrentUser().isEmailVerified()) {
                                LoginRequest request = new LoginRequest(edtUsername.getText().toString(), edtPassword.getText().toString());

                                Call<LoginReponse> call = APIClient.getClient().create(LoginServices.class).loginUser(request);
                                call.enqueue(new Callback<LoginReponse>() {
                                    @Override
                                    public void onResponse(Call<LoginReponse> call, Response<LoginReponse> response) {

                                        LoadingDialog.setLoading(that, false);
                                        if(response.isSuccessful() ){
                                            LoginReponse reponse = response.body();
                                            System.out.println(reponse);
                                            if (reponse != null && reponse.result == 1) {
                                                Alert.confirmLogin(that, "Đăng nhập", reponse.message, new Alert.OnDialogButtonClickListener() {
                                                    @Override
                                                    public void onPositiveButtonClick() {
                                                        GlobalStore.currentUser = reponse.user;
                                                        Intent intents = null;
                                                       if(reponse.user.role.equals("user")){
                                                            intents = new Intent( LoginActivity.this, DashboardActivity.class);

                                                       }
                                                       else{
                                                           // intetn sang màn hình admin
                                                       }

                                                        startActivity(intents);
                                                        finish();
                                                    }

                                                    @Override
                                                    public void onNegativeButtonClick() {

                                                    }
                                                });
                                            }
                                            else{
                                                Alert.alert(that,"Lỗi đăng nhập");
                                            }
                                        }
                                        else{
                                            Alert.alert(that,"Server đang bảo trì");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<LoginReponse> call, Throwable t) {
                                        t.printStackTrace();
                                        LoadingDialog.setLoading(that, false);
                                        Alert.alert(that, "onFailure ", t.getMessage());
                                    }
                                });
                            } else {
                                Alert.alert(that, "Please verify your email address.");

                            }
                        } else {
                            Alert.alert(that, "Email or password is incorrect.");

                        }
                    });
        });
    }

    private void addControl() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin1);
        btnSignUpEmail = findViewById(R.id.btnSignUpEmail);

    }
}