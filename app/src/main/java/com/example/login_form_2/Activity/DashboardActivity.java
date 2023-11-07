package com.example.login_form_2.Activity;

import static androidx.core.view.GravityCompat.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.login_form_2.Notification.NotificationHelper;
import com.example.login_form_2.R;
import com.example.login_form_2.adapter.LoaispAdapter;
import com.example.login_form_2.adapter.ProductAdapter;
import com.example.login_form_2.model.LoaiSanPham;
import com.example.login_form_2.model.Product;
import com.example.login_form_2.model.cart.GetCartReponse;
import com.example.login_form_2.model.category.GetCategoryReponse;
import com.example.login_form_2.model.product.GetProductReponse;
import com.example.login_form_2.retrofit.APIClient;
import com.example.login_form_2.retrofit_interface.CartServices;
import com.example.login_form_2.retrofit_interface.CategoryServices;
import com.example.login_form_2.retrofit_interface.ProductServices;
import com.example.login_form_2.store.GlobalStore;
import com.example.login_form_2.utils.Alert;
import com.example.login_form_2.utils.CheckConnection;
import com.example.login_form_2.utils.LoadingDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private ProductAdapter productAdapter;
    private ArrayList<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        addControl();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            addEventActionBar();
            addEventViewFlipper();
            getDataLoaiSanPham();
            getDataSanPham();
            getDataCart(Long.parseLong(GlobalStore.currentUser.id));
            //addTitleDashboard();
            addEvent();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiếm tra lại kết nối.");
            finish();
        }
    }

    private void getDataCart(long id) {
        LoadingDialog.setLoading(that,true);
        Call<GetCartReponse> call = APIClient.getClient().create(CartServices.class).getAllCartOfUser(id);
        call.enqueue(new Callback<GetCartReponse>() {
            @Override
            public void onResponse(Call<GetCartReponse> call, Response<GetCartReponse> response) {
                    LoadingDialog.setLoading(that,false);
                    if(response.isSuccessful() && response.body() != null){
                        GlobalStore.currentDataCart = response.body().data;
                        // notify when opening app
                        if(GlobalStore.isCurrentOpenningApp ){
                            if(GlobalStore.currentDataCart.size() > 0){
                                NotificationHelper.showNotification(that,"Thông báo", "Bạn đang có " + GlobalStore.currentDataCart.size() + " đơn hàng chưa đặt.\n Đặt ngay nhé\n Happy day <3", "com.example.login_form_2.Activity.CartActivity");
                                GlobalStore.isCurrentOpenningApp = false;
                            }
                        }
                    }
                    else{
                        Alert.alert(that,"Lỗi không get được cart onResponse");
                    }
            }

            @Override
            public void onFailure(Call<GetCartReponse> call, Throwable t) {
                LoadingDialog.setLoading(that,false);
                t.printStackTrace();

            }
        });
    }

    private void getDataSanPham() {
        LoadingDialog.setLoading(that,true);
        Call<GetProductReponse> call =  APIClient.getClient().create(ProductServices.class).getAllSanPham();
        call.enqueue(new Callback<GetProductReponse>() {
            @Override
            public void onResponse(Call<GetProductReponse> call, Response<GetProductReponse> response) {
                LoadingDialog.setLoading(that,false);
                if(response.isSuccessful() && response.body() != null){
                    if(response.body().result == 1){
                        productList = response.body().data;
                        recyclerViewDashboard.setLayoutManager(new GridLayoutManager(that, 2)); // Sử dụng 2 cột
                        productAdapter = new ProductAdapter(productList); // productList là danh sách sản phẩm
                        productAdapter.setOnItemClickListener(position1 -> {
                            Product product = productList.get(position1);
                            Intent intent = new Intent(that, ProductDetailActivity.class);
                            intent.putExtra("product", product);
                            startActivityForResult(intent,1);
                        });
                        recyclerViewDashboard.setAdapter(productAdapter);

                    }
                    else{
                        Alert.alert(that, "Không lấy được sản phẩm");
                        productList = response.body().data;
                        recyclerViewDashboard.setLayoutManager(new GridLayoutManager(that, 2)); // Sử dụng 2 cột
                        productAdapter = new ProductAdapter(productList); // productList là danh sách sản phẩm
                        recyclerViewDashboard.setAdapter(productAdapter);
                    }
                    // Đóng Navigation Drawer sau khi chọn mục
                }
            }

            @Override
            public void onFailure(Call<GetProductReponse> call, Throwable t) {
                LoadingDialog.setLoading(that,false);
                Alert.alert(that,t.getMessage());
            }
        });
    }


    private void getDataLoaiSanPham() {
        LoadingDialog.setLoading(that,true);
        Call<GetCategoryReponse> call = APIClient.getClient().create(CategoryServices.class).getAllLoaiSanPham();
        call.enqueue(new Callback<GetCategoryReponse>() {
            @Override
            public void onResponse(Call<GetCategoryReponse> call, retrofit2.Response<GetCategoryReponse> response) {
                LoadingDialog.setLoading(that,false);
                if(response.isSuccessful() && response.body() != null){
                    GetCategoryReponse reponse = response.body();
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
                GlobalStore.currentArrLoaiSanPham = mangLoaisp;
                loaispAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GetCategoryReponse> call, Throwable t) {
                LoadingDialog.setLoading(that,false);
                t.printStackTrace();
            }
        });
    }

    private void addEvent() {
        btnSignOut.setOnClickListener(view -> {
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
            builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                // Đoạn mã xử lý khi người dùng chọn "Hủy"
                dialogInterface.dismiss();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
        listViewDashboard.setOnItemClickListener((parent, view, position, id) -> {
            if(position == 0){
                getDataSanPham();
                drawerLayout.closeDrawer(GravityCompat.START);
                return;
            }
            LoaiSanPham loai = mangLoaisp.get(position);
            if(position != 0 && position != mangLoaisp.size() -1 && position != mangLoaisp.size() -2){
                LoadingDialog.setLoading(that,true);
                Call<GetProductReponse> call = APIClient.getClient().create(ProductServices.class).getSanPhamByCategoryID(loai.Id);
                call.enqueue(new Callback<GetProductReponse>() {
                    @Override
                    public void onResponse(Call<GetProductReponse> call, Response<GetProductReponse> response) {
                        LoadingDialog.setLoading(that,false);
                        if(response.isSuccessful() && response.body() != null){
                            if(response.body().result == 1){
                                productList = response.body().data;
                                recyclerViewDashboard.setLayoutManager(new GridLayoutManager(that, 2)); // Sử dụng 2 cột
                                productAdapter = new ProductAdapter(productList); // productList là danh sách sản phẩm
                                productAdapter.setOnItemClickListener(position1 -> {
                                    Product product = productList.get(position1);
                                    /*Alert.alert(that,product.toString());*/
                                    Intent intent = new Intent(that, ProductDetailActivity.class);
                                    intent.putExtra("product", product);
                                    startActivityForResult(intent,1);
                                });
                                recyclerViewDashboard.setAdapter(productAdapter);

                            }
                            else{
                                Alert.alert(that, "Không lấy được loại sản phẩm theo categoryID");
                                productList = response.body().data;
                                recyclerViewDashboard.setLayoutManager(new GridLayoutManager(that, 2)); // Sử dụng 2 cột
                                productAdapter = new ProductAdapter(productList); // productList là danh sách sản phẩm
                                recyclerViewDashboard.setAdapter(productAdapter);
                            }
                            // Đóng Navigation Drawer sau khi chọn mục
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetProductReponse> call, Throwable t) {
                        LoadingDialog.setLoading(that,false);
                        t.printStackTrace();
                        Alert.alert(that,t.getMessage());
                    }
                });
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
        String[] imageUrls = {
                "https://genk.mediacdn.vn/139269124445442048/2023/4/16/iphone-15-1681543442446965002535-1681548325115-1681548325988374793089-1681610427688-16816104294971514316573.jpg",
                "https://prinsli.com/wp-content/uploads/2023/01/Apple-iPhone-15-Pro-iPhone-15-Pro-Max-iPhone-15-Ultra-Apple-iPhone-15-series-Features-jpg.webp",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT6yai9EmpyFJ5PeYx8Z5HiIt4V5L66PElO3UinHkBCMG4e2MDI-T6TzJ3d60HqJ0Zqpow&usqp=CAU",
                "https://asset.kompas.com/crops/BXGtmRG5-s5jLC-HRl2hJFhiTmY=/0x0:780x520/750x500/data/photo/2022/10/31/635f38885d598.jpg"
        };

        for (String imageUrl : imageUrls) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(imageUrl).into(imageView);
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
        toolbar.setNavigationOnClickListener(view -> drawerLayout.openDrawer(START));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_right_button) {
            Intent intent = new Intent(that, CartActivity.class);
            startActivityForResult(intent,1);
            return true;
        }

        if(id == R.id.my_order_button){
            //Alert.alert(that,"Đang phát triển");
            Intent intent = new Intent(that, OrderActivity.class);
            startActivityForResult(intent,1);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        loaispAdapter = new LoaispAdapter(mangLoaisp, getApplicationContext());
        listViewDashboard.setAdapter(loaispAdapter);
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                getDataLoaiSanPham();
                getDataSanPham();
                getDataCart(Long.parseLong(GlobalStore.currentUser.id));
            }
        }
    }


}