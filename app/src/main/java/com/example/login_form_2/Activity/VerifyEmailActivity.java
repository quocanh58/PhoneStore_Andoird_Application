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
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class VerifyEmailActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText edtEmail, edtPassword;
    Button btnLoginEmail, btnSendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
        addControl();
        addEvent();
    }

    private void addEvent() {
        mAuth = FirebaseAuth.getInstance();
        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(),
                                                     edtPassword.getText().toString())
//                        mAuth.createUserWithEmailAndPassword() được sử dụng để tạo người dùng mới bằng cách
//                        sử dụng Firebase Authentication. Phương thức này trả về một Task<AuthResult>
//                        để theo dõi quá trình tạo người dùng.
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            // addOnCompleteListener() được gắn kết với Task<AuthResult> để xử lý sự kiện
                            // khi quá trình tạo người dùng hoàn thành.
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Trong onComplete(), chúng ta kiểm tra xem nhiệm vụ có thành công không (task.isSuccessful()).
                                if (task.isSuccessful()) {
                                    // Nếu thành công, chúng ta gửi một email xác minh tới người dùng hiện tại bằng cách
                                    // sử dụng mAuth.getCurrentUser().sendEmailVerification().
                                    mAuth.getCurrentUser().sendEmailVerification()
                                    // sendEmailVerification() trả về một Task<Void> để theo dõi quá trình gửi email xác minh.
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                // AddOnCompleteListener() được gắn kết với Task<Void> để xử lý sự kiện
                                                // khi quá trình gửi email xác minh hoàn thành.
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                //Trong onComplete(), chúng ta kiểm tra xem nhiệm vụ có thành công không.
                                                // Nếu thành công, chúng ta hiển thị một thông báo cho người dùng thông qua Toast,
                                                // sau đó chuyển đến màn hình đăng nhập (LoginActivity) bằng cách tạo một Intent và gọi startActivity(login).
                                                        Toast.makeText(VerifyEmailActivity.this, "Register successfully. Please check your email for verification.",
                                                                Toast.LENGTH_SHORT).show();
                                                        Intent login = new Intent(VerifyEmailActivity.this, LoginActivity.class);
                                                        startActivity(login);
                                                    } else {
                                                        Toast.makeText(VerifyEmailActivity.this, "Failed to send email verification",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            }
                        });
            }
        });

        btnLoginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VerifyEmailActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControl() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword2);
        btnLoginEmail = findViewById(R.id.btnLoginEmail);
        btnSendEmail = findViewById(R.id.btnSendEmail);
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