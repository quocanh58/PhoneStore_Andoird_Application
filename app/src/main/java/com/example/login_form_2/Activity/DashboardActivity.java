package com.example.login_form_2.Activity;

import static androidx.core.view.GravityCompat.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.login_form_2.R;
import com.example.login_form_2.adapter.LoaispAdapter;
import com.example.login_form_2.model.LoaiSanPham;
import com.example.login_form_2.model.category.CategoryReponse;
import com.example.login_form_2.retrofit.APIClient;
import com.example.login_form_2.retrofit_interface.CategoryServices;
import com.example.login_form_2.utils.Alert;
import com.example.login_form_2.utils.CheckConnection;
import com.example.login_form_2.utils.LoadingDialog;
import com.example.login_form_2.utils.Server;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class DashboardActivity extends AppCompatActivity {
    Context that = this;
    private FirebaseAuth mAuth;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewDashboard;
    NavigationView navigationView;
    ListView listViewDashboard;
    DrawerLayout drawerLayout;
    TextView txtNew;
    Button btnSignOut;
    ArrayList<LoaiSanPham> mangLoaisp;
    LoaispAdapter loaispAdapter;
    int id;

    Boolean result = true;
    String message = "";
    String tenloaisanpham = "";
    String hinhanhloaisanpham = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        addControl();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            addEventActionBar();
            addEventViewFlipper();
            getDataLoaiSanPham();
            //addTitleDashboard();
            addEvent();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiếm tra lại kết nối.");
            finish();
        }
    }

//    private void getMenuSidebar() {
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Server.link, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                if (response != null) {
//                    for (int i = 0; i < response.length(); i++) {
//                        try {
//                            JsonObject jsonObject = response.getJSONObject();
//
//                        }
//                    }
//                }
//            }
//        })
//    }

    private void getDataLoaiSanPham() {
        LoadingDialog.setLoading(that,true);
        Call<CategoryReponse> call = APIClient.getClient().create(CategoryServices.class).getAllLoaiSanPham();
        call.enqueue(new Callback<CategoryReponse>() {
            @Override
            public void onResponse(Call<CategoryReponse> call, retrofit2.Response<CategoryReponse> response) {
                LoadingDialog.setLoading(that,false);
                if(response.isSuccessful() && response.body() != null){
                    CategoryReponse reponse = response.body();
                    System.out.println(reponse.toString());
                    for(LoaiSanPham loai : reponse.data){
                        mangLoaisp.add(loai);
                    }

                }
                else{
                    Alert.alert(that,"Lỗi get dữ liệu");
                }

                mangLoaisp.add(new LoaiSanPham(0, "Liên hệ", "https://toppng.com/uploads/preview/iphone-telephone-logo-computer-icons-red-call-icon-11553520215xtqxhnnj6r.png"));
                mangLoaisp.add(new LoaiSanPham(0, "Thông tin", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/Messagebox_info.svg/1200px-Messagebox_info.svg.png"));
                loaispAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CategoryReponse> call, Throwable t) {
                LoadingDialog.setLoading(that,false);
                t.printStackTrace();
            }
        });
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.link, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                if (response != null) {
//                    for (int i = 0; i < response.length(); i++) {
//                        try {
//                            JSONObject jsonObject = response.getJSONObject(i);
//                            result = true;
//                            message = "Lấy dữ liệu thành công";
//
//                            id = jsonObject.getInt("Id");
//                            tenloaisanpham = jsonObject.getString("tenloaisanpham");
//                            hinhanhloaisanpham = jsonObject.getString("hinhanhloaisanpham");
//                            mangLoaisp.add(new LoaiSanPham(id, tenloaisanpham, hinhanhloaisanpham));
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    mangLoaisp.add(new LoaiSanPham(0, "Liên hệ", "https://toppng.com/uploads/preview/iphone-telephone-logo-computer-icons-red-call-icon-11553520215xtqxhnnj6r.png"));
//                    mangLoaisp.add(new LoaiSanPham(0, "Thông tin", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/Messagebox_info.svg/1200px-Messagebox_info.svg.png"));
//                    loaispAdapter.notifyDataSetChanged();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                CheckConnection.showToast_Short(getApplicationContext(), error.toString());
//            }
//        });
//        //gọi lại dữ liệu
//        requestQueue.add(jsonArrayRequest);
    }

    private void addEvent() {
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                builder.setTitle("Confirm logout");
                builder.setMessage("Are you sure you want to sign out?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Đoạn mã xử lý khi người dùng chọn "Đồng ý" để đăng xuất

                        // Gọi phương thức đăng xuất tại đây
                        signOut();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Đoạn mã xử lý khi người dùng chọn "Hủy"
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        // Thực hiện các tác vụ khác sau khi đăng xuất, ví dụ: chuyển đến màn hình đăng nhập

        // Ví dụ: Chuyển đến màn hình đăng nhập (LoginActivity)
        Intent intent2 = new Intent(DashboardActivity.this, LoginActivity.class);
        startActivity(intent2);
        finish(); // Đóng hoạt động hiện tại sau khi chuyển màn hình
    }

    private void addTitleDashboard() {
        mAuth = FirebaseAuth.getInstance();
        String email = mAuth.getCurrentUser().getEmail();
        txtNew.setText("Hi, " + email);
    }

    private void addEventViewFlipper() {
        ArrayList<String> mangQuangCao = new ArrayList<>();
        mangQuangCao.add("https://genk.mediacdn.vn/139269124445442048/2023/4/16/iphone-15-1681543442446965002535-1681548325115-1681548325988374793089-1681610427688-16816104294971514316573.jpg");
        mangQuangCao.add("https://prinsli.com/wp-content/uploads/2023/01/Apple-iPhone-15-Pro-iPhone-15-Pro-Max-iPhone-15-Ultra-Apple-iPhone-15-series-Features-jpg.webp");
        mangQuangCao.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT6yai9EmpyFJ5PeYx8Z5HiIt4V5L66PElO3UinHkBCMG4e2MDI-T6TzJ3d60HqJ0Zqpow&usqp=CAU");
        mangQuangCao.add("https://asset.kompas.com/crops/BXGtmRG5-s5jLC-HRl2hJFhiTmY=/0x0:780x520/750x500/data/photo/2022/10/31/635f38885d598.jpg");
        for (int i = 0; i < mangQuangCao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(mangQuangCao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void addEventActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(START);
            }
        });
    }

    private void addControl() {
        toolbar = findViewById(R.id.toolbarDashboard);
        viewFlipper = findViewById(R.id.viewFlipper);
        recyclerViewDashboard = findViewById(R.id.recyclerView);
        navigationView = findViewById(R.id.navigationView);
        listViewDashboard = findViewById(R.id.listViewDashboard);
        drawerLayout = findViewById(R.id.drawerLayout);
        txtNew = findViewById(R.id.txtNew);
        btnSignOut = findViewById(R.id.btnSignOut);
        mangLoaisp = new ArrayList<>();
        mangLoaisp.add(new LoaiSanPham(0, "Trang chính", "https://e7.pngegg.com/pngimages/526/91/png-clipart-website-home-page-home-inspection-house-size-icon-homepage-miscellaneous-blue-thumbnail.png"));
//        mangLoaisp.add(1, new Loaisp(0, "Liên hệ", "https://toppng.com/uploads/preview/iphone-telephone-logo-computer-icons-red-call-icon-11553520215xtqxhnnj6r.png"));
//        mangLoaisp.add(2, new Loaisp(0, "Thông tin", "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a5/Messagebox_info.svg/1200px-Messagebox_info.svg.png"));
        loaispAdapter = new LoaispAdapter(mangLoaisp, getApplicationContext());
        listViewDashboard.setAdapter(loaispAdapter);
    }

}