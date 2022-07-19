package com.example.myshop2.ActivityPackage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myshop2.ObjectClass.Product;
import com.example.myshop2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProductActivity extends AppCompatActivity {
    ImageView imgProductMain;
    TextView txtProductTitle,txtProductDescription,txtProductPrice,txtProductRate;
    RatingBar ratingProduct;
    Product _product;
    String url = "https://nickkimbum.000webhostapp.com/get1ProductById.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }
        int _idProduct = bundle.getInt("_idProduct");
        SetDataProduct(_idProduct, url);
        AnhXa();
    }

    private void AnhXa() {
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
                            Glide.with(ProductActivity.this).load(_product.getThumbnail()).into(imgProductMain);
                            txtProductTitle.setText(_product.getTitle());
                            txtProductPrice.setText("đ"+_product.getPrice());
                            txtProductDescription.setText(_product.getDescription());
                            txtProductRate.setText(_product.getRate()+" | Đã bán 999 ");
                            ratingProduct.setRating(_product.getRate());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProductActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

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
}