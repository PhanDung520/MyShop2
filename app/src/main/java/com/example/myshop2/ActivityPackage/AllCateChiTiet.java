package com.example.myshop2.ActivityPackage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myshop2.Adapter.BannerAdapter;
import com.example.myshop2.Adapter.MostViewAdapter;
import com.example.myshop2.ObjectClass.MostViewClass;
import com.example.myshop2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AllCateChiTiet extends AppCompatActivity {
    ArrayList<MostViewClass> arrayList;
    MostViewAdapter adapter;
    RecyclerView recycleChiTietAllCate,recycleBanner;
    ArrayList<Integer> listBanner;
    BannerAdapter bannerAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cate_chi_tiet);
        recycleChiTietAllCate= findViewById(R.id.recycleChiTietAllCate);
        recycleBanner = findViewById(R.id.recycleBanner);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null){
            return;
        }
        int _idcate = bundle.getInt("_idCate");
        Toast.makeText(this, ""+_idcate, Toast.LENGTH_SHORT).show();
        arrayList = new ArrayList<>();
        listBanner = new ArrayList<>();
        SetDataCate(_idcate);
        listBanner.add(R.drawable.banner2);
        listBanner.add(R.drawable.banner3);
        listBanner.add(R.drawable.banner4);
        bannerAdapter = new BannerAdapter(listBanner,this);

        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recycleBanner.setLayoutManager(linearLayoutManager);
        recycleBanner.setAdapter(bannerAdapter);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recycleBanner);
        Timer timer =new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition()<bannerAdapter.getItemCount()-1){
                    linearLayoutManager.smoothScrollToPosition(recycleBanner,new RecyclerView.State(),linearLayoutManager.findLastCompletelyVisibleItemPosition()+1);
                }else if (linearLayoutManager.findLastCompletelyVisibleItemPosition()<bannerAdapter.getItemCount()-1){
                    linearLayoutManager.smoothScrollToPosition(recycleBanner,new RecyclerView.State(),0);
                }

            }
        },0,3000
        );

    }

    private void SetDataCate(int idCate) {
        recycleChiTietAllCate.hasFixedSize();
        recycleChiTietAllCate.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://nickkimbum.000webhostapp.com/myShopGetDataByCate.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response55",response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i=0; i< jsonArray.length();i++){
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
                        Toast.makeText(AllCateChiTiet.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map params = new HashMap();
                params.put("cate_id_post",String.valueOf(idCate));
                return params;
            }
        };
        requestQueue.add(stringRequest);
        adapter = new MostViewAdapter(arrayList,this);
        recycleChiTietAllCate.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}