package com.example.myshop2.ActivityPackage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myshop2.DataLocal.SessionManager;
import com.example.myshop2.ObjectClass.Pre_order;
import com.example.myshop2.ObjectClass.Product;
import com.example.myshop2.ObjectClass.User;
import com.example.myshop2.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProductActivity2 extends AppCompatActivity {
    ImageButton btnProductBack;
    int user_id;
    User user;
    Pre_order preOrder;
    LinearLayout btnMuaNgay;
    LinearLayout layoutAddtocart;
    ImageView btnProductCancelAdd,imgOrderDetail;
    ImageView imgProductMain;
    TextView txtProductTitle,txtProductDescription,txtProductPrice,txtProductRate,txtOrderTitle,txtAddtocartPrice;
    RatingBar ratingProduct;
    private Product _product;
    LinearLayout btnThemVaoGioHang,layoutProduct;
    ImageView btnMinusCard, btnPlusCard;
    TextView txtBottomSoLuong,txtBottomSoLuongMax;
    Button btnBottomThem;
    int _idProduct;
    String urlUpdateSoLuongPre="https://nickkimbum.000webhostapp.com/myShopUpdateSoLuongPre.php";
    String url = "https://nickkimbum.000webhostapp.com/get1ProductById.php";
    String urlInsertPre = "https://nickkimbum.000webhostapp.com/myShopInsertPreOrder.php";
    String urlCheckProductExist ="https://nickkimbum.000webhostapp.com/checkPreProductExist.php";
    private int soluong;
    private int product_id;

    BottomSheetBehavior bottomSheetBehavior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poduct_and_addtocart);
        AnhXa();
        bottomSheetBehavior = BottomSheetBehavior.from(layoutAddtocart);
        btnThemVaoGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new SessionManager(ProductActivity2.this).checkLogin()==false){
                    Toast.makeText(ProductActivity2.this, "Chưa đăng nhập", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(ProductActivity2.this,LoginActivity.class);
                    ProductActivity2.this.startActivity(intent2);
                }else {
                    Intent intent = new Intent(ProductActivity2.this, CartActivity.class);
                    ProductActivity2.this.startActivity(intent);

                }
            }
        });
        btnMuaNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new SessionManager(ProductActivity2.this).checkLogin()==false){
                    Toast.makeText(ProductActivity2.this, "Chưa đăng nhập", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(ProductActivity2.this,LoginActivity.class);
                    ProductActivity2.this.startActivity(intent2);
                }else {
                    if(bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }else {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }
            }
        });
        btnProductCancelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });
//        layoutProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_EXPANDED){
//                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                }
//            }
//        });

        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }
        _idProduct = bundle.getInt("_idProduct");
        soluong =1;
        txtBottomSoLuong.setText(soluong+"");
        SetDataProduct(_idProduct, url);
        btnBottomThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                ButtomThemSanPham(urlCheckProductExist);
            }
        });
        btnProductBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity2.this,HomeScreenActivity.class);
                ProductActivity2.this.startActivity(intent);
            }
        });
    }
    private void AnhXa() {
        btnProductBack = findViewById(R.id.btnProductBack);
        btnBottomThem = findViewById(R.id.btnBottomThem);
        layoutProduct = findViewById(R.id.layoutProduct);
        txtAddtocartPrice = findViewById(R.id.txtAddtocartPrice);
        imgOrderDetail = findViewById(R.id.imgOrderDetail);
        txtOrderTitle = findViewById(R.id.txtOrderTitle);
        btnMuaNgay = findViewById(R.id.btnMuaNgay);
        layoutAddtocart = findViewById(R.id.layoutAddtocart);
        btnProductCancelAdd = findViewById(R.id.btnProductCancelAdd);
        btnThemVaoGioHang = findViewById(R.id.btnThemVaoGioHang);
        btnMinusCard = findViewById(R.id.btnMinusCard);
        txtBottomSoLuong = findViewById(R.id.txtBottomSoLuong);
        btnPlusCard = findViewById(R.id.btnPlusCard);
        txtBottomSoLuongMax = findViewById(R.id.txtBottomSoLuongMax);
        imgProductMain = findViewById(R.id.imgProductMain);
        txtProductDescription = findViewById(R.id.txtProductDescription);
        txtProductTitle = findViewById(R.id.txtProductTitle);
        txtProductPrice = findViewById(R.id.txtProductPrice);
        txtProductRate = findViewById(R.id.txtProductRate);
        ratingProduct = findViewById(R.id.ratingProduct);
    }

    private void SetDataProduct(int id, String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            //int id, int soluong, int category_id, int rate, int price, String title, String thumbnail, String description
                            _product = new Product(object.getInt("ID"), object.getInt("SoLuong"), object.getInt("Category_id"),object.getInt("Rate"),object.getInt("Price"), object.getString("Title"),object.getString("Thumbnail"),object.getString("Description"));
                            Glide.with(ProductActivity2.this).load(_product.getThumbnail()).into(imgProductMain);
                            txtProductTitle.setText(_product.getTitle());
                            product_id = _product.getId();
                            txtProductPrice.setText("đ"+_product.getPrice());
                            txtProductDescription.setText(_product.getDescription());
                            txtProductRate.setText(_product.getRate()+" | Đã bán 999 ");
                            ratingProduct.setRating(_product.getRate());
                            txtBottomSoLuongMax.setText("Trong kho: "+_product.getSoluong());
                            Glide.with(getApplicationContext()).load(_product.getThumbnail()).into(imgOrderDetail);
                            if(_product.getTitle().length()<50){
                                txtOrderTitle.setText(_product.getTitle());
                            }else{
                                txtOrderTitle.setText(_product.getTitle().substring(0,50)+"...");
                            }
                            txtAddtocartPrice.setText("đ"+_product.getPrice());
                            btnMinusCard.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(soluong>1){
                                        soluong -=1;
                                        txtBottomSoLuong.setText(soluong+"");
                                        txtAddtocartPrice.setText("đ"+soluong*_product.getPrice());
                                    }
                                }
                            });
                            btnPlusCard.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if(soluong<_product.getSoluong()){
                                        soluong +=1;
                                        txtBottomSoLuong.setText(""+soluong);
                                        txtAddtocartPrice.setText("đ"+soluong*_product.getPrice());
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProductActivity2.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map params = new HashMap();
                params.put("id_product",String.valueOf(id));
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void ThemSanPhamVaoGioHangIfNull(String url){
        //insert into pre_order_detail
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        int finalUser_id = user_id;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            Toast.makeText(ProductActivity2.this, "Thêm sản phẩm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(ProductActivity2.this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProductActivity2.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap();
                params.put("user_id_post",String.valueOf(finalUser_id));
                params.put("product_id_post",String.valueOf(product_id));
                params.put("soluong_post",String.valueOf(soluong));
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void ButtomThemSanPham(String url){
        //check login
        //lay user_id
        user_id=0;
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        if(sessionManager.checkLogin()==true){
            user = sessionManager.getUserDetailFormSession();
            user_id = user.getId();
        }else {
            //login
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            getApplicationContext().startActivity(intent);
            return;
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("null")){
                            //xu ly khi khong co ban gi
                            ThemSanPhamVaoGioHangIfNull(urlInsertPre);
                            Log.d("InsertNull267","true");
                        }
                        else{
                            //lay ban ghi
                            Log.d("InsertNotNull271","true");
                            try {
                                JSONObject object = new JSONObject(response);
                                preOrder = new Pre_order(object.getInt("ID"),object.getInt("User_id"), object.getInt("Product_id"), object.getInt("Num"));
                                int newNum = soluong + object.getInt("Num");
                                XuLyKhiDaCoBanGhi(urlUpdateSoLuongPre, newNum, object.getInt("ID"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            //xu ly khi da co ban ghi

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("error287",error.toString());
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map params = new HashMap();
                params.put("user_id_post",String.valueOf(user_id));
                params.put("product_id_post",String.valueOf(product_id));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void XuLyKhiDaCoBanGhi(String url, int newNum, int pre_id) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("respose", response);
                        Log.d("newNum", newNum+"");

                        if(response.trim().equals("success")){
                            Toast.makeText(ProductActivity2.this, "Thêm số luong thành công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ProductActivity2.this, "lỗi thêm số lượng", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error319",error.toString());
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map params= new HashMap();
                params.put("newNum_post",String.valueOf(newNum));
                params.put("pre_id_post",String.valueOf(pre_id));
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}