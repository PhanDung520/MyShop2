package com.example.myshop2.ActivityPackage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import com.example.myshop2.Adapter.MostViewAdapter;
import com.example.myshop2.ObjectClass.MostViewClass;
import com.example.myshop2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChiTietTimKiemActivity extends AppCompatActivity {
    SearchView searchAfter;
    ImageView btnBackAfterSearch;
    ArrayList<MostViewClass> arrayList;
    MostViewAdapter adapter;
    RecyclerView recycleSearchChiTiet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_tim_kiem);
        AnhXa();

        Intent intent = getIntent();
        String query = intent.getStringExtra("query");
        searchAfter.setQuery(query,false);
        searchAfter.clearFocus();
        Toast.makeText(this, query, Toast.LENGTH_SHORT).show();

        btnBackAfterSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ChiTietTimKiemActivity.this,HomeScreenActivity.class);
                ChiTietTimKiemActivity.this.startActivity(intent1);
            }
        });
        SetDataUpdate(query);
    }
    private void AnhXa() {
        recycleSearchChiTiet = findViewById(R.id.recycleSearchChiTiet);
        arrayList = new ArrayList<>();
        searchAfter = findViewById(R.id.searchAfter);
        btnBackAfterSearch = findViewById(R.id.btnBackAfterSearch);
    }
    private void SetDataUpdate(String query) {
        recycleSearchChiTiet.hasFixedSize();
        recycleSearchChiTiet.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://nickkimbum.000webhostapp.com/myShopGetSearchData.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response",response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i=0; i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                arrayList.add(new MostViewClass(object.getString("Thumbnail"),object.getString("Title"),object.getString("Description"),object.getInt("Rate"),object.getInt("ID")));
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChiTietTimKiemActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map params = new HashMap();
                params.put("query_post",query);
                return params;
            }
        };
        requestQueue.add(stringRequest);
        adapter = new MostViewAdapter(arrayList,this);
        recycleSearchChiTiet.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


}