package com.example.login_form_2.Activity;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOTPActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String verificationId;
    Button verifyOtp;
    EditText otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otpactivity);
        mAuth = FirebaseAuth.getInstance();
        addControl();
        String phone = getIntent().getStringExtra("sdt");
        String newPhone = "+84" + phone.substring(1, phone.length());
        sendVerificationCode(newPhone);
        addEvent();
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            // - Biến mCallBack là một đối tượng PhoneAuthProvider.OnVerificationStateChangedCallbacks. Đây
            //  là một callbacks để lắng nghe sự kiện trong quá trình xác minh số điện thoại.
            // - Callbacks này được khởi tạo bằng cách tạo một đối tượng v
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        // khởi tạo các callbacks để xử lý sự kiện trong quá trình xác minh số điện thoại sử dụng Firebase.
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            // - Phương thức này được gọi khi OTP được gửi từ Firebase thành công.
            // - Tham số s chứa mã OTP duy nhất, và nó được lưu trữ trong biến verificationId
            //   để sử dụng sau này.
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            // Phương thức này được gọi khi người dùng đã nhận được OTP từ Firebase.
            // Trong phương thức này, chúng ta sử dụng phoneAuthCredential.getSmsCode()
            // để lấy mã OTP đã gửi trong phoneAuthCredential.
            // Nếu mã không rỗng (code != null), chúng ta gán giá trị mã OTP vào trường
            // nhập liệu OTP (otp.setText(code)) và sau đó gọi phương thức
            // verifyCode(code) để xác minh mã.
            final String code = phoneAuthCredential.getSmsCode();

            // kiểm tra xem mã có rỗng hay không.
            if (code != null) {
                // nếu mã không rỗng thì
                // chúng tôi đang đặt mã đó thành
                // trường edittext OTP của chúng tôi.
                otp.setText(code);

                // sau khi đặt mã này
                // tới trường edittext OTP chúng ta
                // đang gọi phương thức verifycode của chúng tôi.
                verifyCode(code);
            }
        }

        // this method is called when firebase doesn't
        // sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            // Phương thức này được gọi khi Firebase không gửi được mã OTP do lỗi hoặc vấn đề gì đó.
            // Trong phương thức này, chúng ta hiển thị thông báo lỗi sử dụng
            // Toast.makeText() và ghi log lỗi sử dụng Log.e().
            Toast.makeText(VerifyOTPActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("Looxi ", e.getMessage());
        }
    };

    private void signInWithCredential(PhoneAuthCredential credential) {
        // dùng để xác minh và đăng nhập người dùng sử dụng
        // thông tin xác minh (PhoneAuthCredential) từ Firebase.
        mAuth.signInWithCredential(credential)
                // Phương thức mAuth.signInWithCredential(credential) được gọi để thực hiện
                // xác minh và đăng nhập người dùng với thông tin xác minh đã được cung cấp.
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    //Chúng ta sử dụng addOnCompleteListener()
                    // để thêm một OnCompleteListener cho tác vụ (task) xác minh đăng nhập.
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Trong phương thức onComplete(), chúng ta kiểm tra xem tác vụ
                        // có thành công hay không bằng cách sử dụng task.isSuccessful().
                        if (task.isSuccessful()) {
                            // Nếu tác vụ thành công, chúng ta hiển thị một thông báo
                            // thành công (Toast.makeText()) và chuyển người dùng
                            // đến một hoạt động mới (DashboardActivity) bằng cách tạo một Intent và
                            // gọi startActivity(). Sau đó, chúng ta kết thúc (finish) hoạt động hiện tại.
                            Toast.makeText(VerifyOTPActivity.this, "Xác nhận thành công", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(VerifyOTPActivity.this, DashboardActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            //Nếu tác vụ không thành công, chúng ta ghi log lỗi (Log.e())
                            // và hiển thị một thông báo lỗi cho người dùng (Toast.makeText()).
                            Log.e("signInWithCredential", task.getException().getMessage());
                            Toast.makeText(VerifyOTPActivity.this, "OTP không chính xác. Xác nhận thất bại !", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String number) {
        // Phương thức này được sử dụng để gửi mã OTP đến số điện thoại của người dùng.
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number)            // số điện thoại cần xác minh
                        .setTimeout(60L, TimeUnit.SECONDS) // đặt thời gian chờ nhận OTP
                        .setActivity(this)                 // đặt hoạt động hiện tại
                        .setCallbacks(mCallBack)           // đặt callbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // Cuối cùng, chúng ta gọi PhoneAuthProvider.verifyPhoneNumber(options)
        // để gửi yêu cầu xác minh số điện thoại đến Firebase.
    }

    private void verifyCode(String code) {
        // Phương thức này được sử dụng để xác minh mã OTP đã nhập từ người dùng.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // để lấy thông tin xác minh (PhoneAuthCredential) từ mã OTP và mã xác minh đã nhận được
        signInWithCredential(credential);
    }

    private void addEvent() {
        //tạo sự kiện
        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = otp.getText().toString();
                verifyCode(code);
            }
        });
    }

    // tạo ánh xạ
    private void addControl() {
        otp = findViewById(R.id.edtOTP);
        verifyOtp = findViewById(R.id.btnSendOTP);
    }
}