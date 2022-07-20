package com.example.myshop2.ActivityPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.myshop2.Adapter.AllCategoryAdapter;
import com.example.myshop2.Adapter.MostViewAdapter;
import com.example.myshop2.DataLocal.SessionManager;
import com.example.myshop2.ObjectClass.AllCategoryClass;
import com.example.myshop2.ObjectClass.MostViewClass;
import com.example.myshop2.ObjectClass.User;
import com.example.myshop2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeScreenActivity extends AppCompatActivity {
    RecyclerView recycleAllcate, recyclerMostView;
    RelativeLayout layoutSearch;
    ImageView btnAddUser,btnBackCart;
    RecyclerView.Adapter adapterAllcate, adapterMostView;
    ArrayList<MostViewClass> arrayMostView;
    String url="https://nickkimbum.000webhostapp.com/myshopGetData.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        AnhXa();
        SetAllCate();
        SetMostView(url);
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this, LoginActivity.class);
                HomeScreenActivity.this.startActivity(intent);
            }
        });
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        if(sessionManager.checkLogin()==true){
            User user = sessionManager.getUserDetailFormSession();
            Toast.makeText(this,"user session: "+ user.getFullname().toString(), Toast.LENGTH_SHORT).show();
            Glide.with(this).load(user.getAvatar()).into(btnAddUser);
        }
        else{
            Toast.makeText(this, "Chua dang nhap", Toast.LENGTH_SHORT).show();
        }
        layoutSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScreenActivity.this,SearchActivity.class);
                HomeScreenActivity.this.startActivity(intent);
            }
        });
//        btnBackCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(HomeScreenActivity.this,DonHangActivity.class);
//                HomeScreenActivity.this.startActivity(intent);
//            }
//        });
    }
    private void AnhXa() {
        btnAddUser = findViewById(R.id.btnAddUser);
        layoutSearch = findViewById(R.id.layoutSearch);
        recycleAllcate = findViewById(R.id.recycleAllcate);
        recyclerMostView = findViewById(R.id.recycleMostView);
        btnBackCart = findViewById(R.id.btnBackCart);
    }
    private void SetAllCate(){
        //Smooth
        recycleAllcate.setHasFixedSize(true);
        recycleAllcate.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ArrayList<AllCategoryClass> arrayAllCate = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(HomeScreenActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://nickkimbum.000webhostapp.com/myshopGetCategory.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i=0; i< jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                arrayAllCate.add(new AllCategoryClass(object.getString("Thumbnail"), object.getString("Name"), object.getString("Description"), 4, object.getInt("ID")));

                            }
                            adapterAllcate.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HomeScreenActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(stringRequest);

//        arrayAllCate.add(new AllCategoryClass(R.drawable.laro_logo,"La Roche-Posay's","The formulation charter for each La Roche-Posay product goes",3));
//        arrayAllCate.add(new AllCategoryClass(R.drawable.laro_logo,"La Roche-Posay's","The formulation charter for each La Roche-Posay product goes",4));
//        arrayAllCate.add(new AllCategoryClass(R.drawable.laro_logo,"La Roche-Posay's","The formulation charter for each La Roche-Posay product goes",5));
//        arrayAllCate.add(new AllCategoryClass(R.drawable.laro_logo,"La Roche-Posay's","The formulation charter for each La Roche-Posay product goes",3));

        adapterAllcate = new AllCategoryAdapter(arrayAllCate, HomeScreenActivity.this);
        recycleAllcate.setAdapter(adapterAllcate);
        adapterAllcate.notifyDataSetChanged();
    }

    private void SetMostView(String url){
        recyclerMostView.setHasFixedSize(true);
        recyclerMostView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        arrayMostView = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i=0; i<response.length();i++){
                            try {
                                JSONObject dataRow = response.getJSONObject(i);
                                arrayMostView.add(new MostViewClass(dataRow.getString("Thumbnail"),dataRow.getString("Title"),dataRow.getString("Description"),dataRow.getInt("Rate"),dataRow.getInt("ID")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapterMostView.notifyDataSetChanged();
                    }
                }
                , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HomeScreenActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsonArrayRequest);
        adapterMostView = new MostViewAdapter(arrayMostView, this);
        recyclerMostView.setAdapter(adapterMostView);
        adapterMostView.notifyDataSetChanged();
    }
}