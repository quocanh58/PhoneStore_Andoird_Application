package com.example.login_form_2.Activity;

import static com.example.login_form_2.utils.Function.isValidEmail;
import static com.example.login_form_2.utils.Function.isValidPassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login_form_2.R;
import com.example.login_form_2.model.register.RegisterReponse;
import com.example.login_form_2.model.register.RegisterRequest;
import com.example.login_form_2.retrofit.APIClient;
import com.example.login_form_2.retrofit_interface.RegisterServices;
import com.example.login_form_2.utils.Alert;
import com.example.login_form_2.utils.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText edtEmail, edtPassword, txtRepass, txtAddress, txtPhone, txtName;
    Button btnLoginEmail, btnSendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_email);
        addControl();
        addEvent();
    }

    private void addEvent() {
        mAuth = FirebaseAuth.getInstance();
        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String password2 = txtRepass.getText().toString();
                String addRess = txtAddress.getText().toString();
                String phone = txtPhone.getText().toString();
                String name = txtName.getText().toString();
                if (name.isEmpty()) {
                    Alert.alert(SignUpActivity.this, "Không được để trống tên");
                    return;
                }
                if (email.isEmpty()) {
                    Alert.alert(SignUpActivity.this, "Không được để trống email");
                    return;
                }
                if (!isValidEmail(email)) {
                    Alert.alert(SignUpActivity.this, "Email không hợp lệ");
                    return;
                }

                if (password.isEmpty()) {
                    Alert.alert(SignUpActivity.this, "Không được để trống password");
                    return;
                }
                if (!isValidPassword(password)) {
                    Alert.alert(SignUpActivity.this, "Mật khẩu không đủ mạnh (thêm yêu cầu về mật khẩu)");
                    return;
                }
                if (password2.isEmpty()) {
                    Alert.alert(SignUpActivity.this, "Không được để trống re-password");
                    return;
                }
                if (!password2.equals(password)) {
                    Alert.alert(SignUpActivity.this, "Mật khẩu nhập lại không khớp");
                    return;
                }

                if (addRess.isEmpty()) {
                    Alert.alert(SignUpActivity.this, "Không được để trống địa chỉ");
                    return;
                }

                if (phone.isEmpty()) {
                    Alert.alert(SignUpActivity.this, "Không được để trống số điện thoại");
                    return;
                }
//                RegisterRequest request = new RegisterRequest(email, password, name, addRess, phone);
//                Alert.alert(SignUpActivity.this,request.toString());

                LoadingDialog.setLoading(SignUpActivity.this, true);
                mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(),
                                edtPassword.getText().toString())
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Alert.alert(SignUpActivity.this, e.getMessage());
                                LoadingDialog.setLoading(SignUpActivity.this, false);
                            }
                        })
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {
                                    mAuth.getCurrentUser().sendEmailVerification()

                                            .addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    RegisterRequest request = new RegisterRequest(email, password, name, addRess, phone);

                                                    Call<RegisterReponse> call = APIClient.getClient().create(RegisterServices.class).registerUser(request);
                                                    call.enqueue(new Callback<RegisterReponse>() {
                                                        @Override
                                                        public void onResponse(Call<RegisterReponse> call, Response<RegisterReponse> response) {
                                                            LoadingDialog.setLoading(SignUpActivity.this, false);
                                                            if (response.isSuccessful() && response.body() != null) {
                                                                RegisterReponse reponse = response.body();
                                                                if (reponse.result == 1) {
                                                                    Alert.confirm(SignUpActivity.this, reponse.message, new Alert.OnDialogButtonClickListener() {
                                                                        @Override
                                                                        public void onPositiveButtonClick() {
                                                                            Intent login = new Intent(SignUpActivity.this, LoginActivity.class);
                                                                            startActivity(login);
                                                                            finish();
                                                                        }

                                                                        @Override
                                                                        public void onNegativeButtonClick() {

                                                                        }
                                                                    });

                                                                } else {
                                                                    Alert.alert(SignUpActivity.this, reponse.message);

                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<RegisterReponse> call, Throwable t) {
                                                            LoadingDialog.setLoading(SignUpActivity.this, false);
                                                            t.printStackTrace(); // In ra thông báo lỗi của Throwable
                                                        }
                                                    });

                                                } else {
                                                    LoadingDialog.setLoading(SignUpActivity.this, false);
                                                    Alert.alert(SignUpActivity.this, "Failed to send email verification");
                                                    ;
                                                }
                                            });
                                }
                            }
                        });
            }
        });

        btnLoginEmail.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void addControl() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword2);
        txtRepass = findViewById(R.id.txtRepass);
        btnLoginEmail = findViewById(R.id.btnLoginEmail);
        btnSendEmail = findViewById(R.id.btnSendEmail);
        txtAddress = findViewById(R.id.txtAddress);
        txtPhone = findViewById(R.id.txtPhone);
        txtName = findViewById(R.id.txtName);
    }


    private ActionCodeSettings getEmailActionCodeSettings() {
        return ActionCodeSettings.newBuilder()
                .setUrl("https://quocanhtt.page.link/verify") // Thay đổi URL xác minh thành URL của ứng dụng của bạn
                .setHandleCodeInApp(true)
                .setAndroidPackageName(
                        "com.example.login_form_2", // Thay đổi thành tên package của ứng dụng của bạn
                        true, /* installIfNotAvailable */
                        "12" /* minimumVersion */)
                .build();
    }

//    mAuth.sendSignInLinkToEmail("at050801@gmail.com", getEmailActionCodeSettings())
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            // Gửi email xác minh thành công
//                            Toast.makeText(getApplicationContext(), "Email verification sent", Toast.LENGTH_SHORT).show();
//                        } else {
//                            // Gửi email xác minh thất bại
//                            Toast.makeText(getApplicationContext(), "Failed to send email verification", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

}