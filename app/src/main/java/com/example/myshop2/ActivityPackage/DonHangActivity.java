package com.example.myshop2.ActivityPackage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myshop2.Adapter.DonHangAdapter;
import com.example.myshop2.DataLocal.SessionManager;
import com.example.myshop2.ObjectClass.DonHang;
import com.example.myshop2.ObjectClass.Product;
import com.example.myshop2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DonHangActivity extends AppCompatActivity {
    ImageView btnBackDonHang;
    RecyclerView recycleDonHang;
    ArrayList<DonHang> arrayDonHang;
    ArrayList<Product> productArrayList;
    DonHangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);
        AnhXa();
        btnBackDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DonHangActivity.this,CartActivity.class);
                DonHangActivity.this.startActivity(intent);

            }
        });
        SetDonHangData();
    }
    private void AnhXa() {
        arrayDonHang = new ArrayList<>();
        productArrayList = new ArrayList<>();
        btnBackDonHang = findViewById(R.id.btnBackDonHang);
        recycleDonHang = findViewById(R.id.recycleDonHang);
    }
    public static String convertFormat(String inputDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        java.util.Date date = null;
        try {
            date = simpleDateFormat.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (date == null) {
            return "";
        }

        SimpleDateFormat convetDateFormat = new SimpleDateFormat("hh:mm a dd-MM-yyyy");

        return convetDateFormat.format(date);
    }

    private void SetDonHangData() {
        recycleDonHang.hasFixedSize();
        recycleDonHang.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://nickkimbum.000webhostapp.com/myShopSelectOrderByUserId.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("DonHangrespon1",response);

                            JSONArray jsonArray = new JSONArray(response);
                            for (int i=0; i< jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                DonHang donHang = new DonHang();
                                donHang.setOrder_id(object.getInt("ID"));
                                donHang.setDate(convertFormat(object.getString("Order_date")));
                                arrayDonHang.add(donHang);
                            }
                            //lay product
                            StringRequest stringRequest1 = new StringRequest(Request.Method.POST, "https://nickkimbum.000webhostapp.com/myShopSelectOrderdetailByOrderId.php",
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONArray jsonArray1 = new JSONArray(response);
                                                for(int i=0; i<jsonArray1.length();i++){
                                                    JSONObject object = jsonArray1.getJSONObject(i);
                                                    Product product = new Product();
                                                    product.setTitle(object.getString("Title"));
                                                    product.setId(object.getInt("Product_id"));
                                                    product.setOrder_id(object.getInt("Order_id"));
                                                    product.setPrice(object.getInt("Price"));
                                                    product.setSoluong(object.getInt("Num"));
                                                    product.setThumbnail(object.getString("Thumbnail"));
                                                    productArrayList.add(product);
                                                }

                                                for(int i=0; i< arrayDonHang.size(); i++){
                                                    ArrayList<Product> arrayTemp = new ArrayList<>();
                                                    for(int j=0; j< productArrayList.size();j++){
                                                        if(arrayDonHang.get(i).getOrder_id()==productArrayList.get(j).getOrder_id()){
                                                            arrayTemp.add(productArrayList.get(j));
                                                        }
                                                    }
                                                    arrayDonHang.get(i).setProductArrayList(arrayTemp);
                                                    arrayDonHang.get(i).setTitle(arrayTemp.get(0).getTitle()+"...");

                                                    arrayDonHang.get(i).setImage(arrayTemp.get(0).getThumbnail());
                                                    int sum =0;
                                                    for(int k=0; k<arrayTemp.size();k++){
                                                        sum+=arrayTemp.get(k).getPrice()*arrayTemp.get(k).getSoluong();
                                                    }
                                                    arrayDonHang.get(i).setTotalprice(sum);
                                                }

                                                adapter = new DonHangAdapter(arrayDonHang,DonHangActivity.this);
                                                recycleDonHang.setAdapter(adapter);
                                                adapter.notifyDataSetChanged();


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }){
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map params = new HashMap();
                                    for(int i=0; i<arrayDonHang.size();i++){
                                        params.put("order_id_post["+i+"]",String.valueOf(arrayDonHang.get(i).getOrder_id()));
                                    }
                                    return params;
                                }
                            };
                            requestQueue.add(stringRequest1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map params = new HashMap();
                params.put("user_id_post",String.valueOf(new SessionManager(DonHangActivity.this).getUserDetailFormSession().getId()));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }


}