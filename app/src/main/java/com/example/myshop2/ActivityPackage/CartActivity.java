package com.example.myshop2.ActivityPackage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myshop2.Adapter.CartAdapter;
import com.example.myshop2.DataLocal.SessionManager;
import com.example.myshop2.ObjectClass.Cart;
import com.example.myshop2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    RecyclerView recycleCart;
    RecyclerView.Adapter adapterCart;
    ArrayList<Cart> listCart;
    TextView txtCartTotalPrice;
    Button btnTotalMua;
    private ArrayList<Cart> currentSelectedItem;
    private int order_id_get;
    ImageView btnBackCart;
    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        AnhXa();
        SetCart("https://nickkimbum.000webhostapp.com/myShopGetPreOrder.php");
        btnBackCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, HomeScreenActivity.class);
                startActivity(intent);
            }
        });

    }
    private void AnhXa(){
        recycleCart = findViewById(R.id.recycleCart);
        txtCartTotalPrice = findViewById(R.id.txtCartTotalPrice);
        btnTotalMua = findViewById(R.id.btnTotalMua);


    }
    public void SetTotalPrice(int totalPrice){
        txtCartTotalPrice.setText("đ"+totalPrice);

    }
    public void SetAffterMua(int newNum, int posision){
        listCart.get(posision).setSoluong(newNum);
    }

    private void SetCart(String url) {

        recycleCart.setHasFixedSize(true);
        recycleCart.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        listCart = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                listCart.add(new Cart(object.getString("Thumbnail"), object.getString("Title"), object.getInt("Price"),object.getInt("ID"),object.getInt("Product_id"),object.getInt("Num"),object.getInt("SoLuongTrongKho")));
                            }
                            adapterCart.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CartActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map params = new HashMap();
                params.put("user_id_post",String.valueOf(new SessionManager(CartActivity.this).getUserDetailFormSession().getId()));

                return params;
            }
        };
        requestQueue.add(stringRequest);
        currentSelectedItem = new ArrayList();
        adapterCart = new CartAdapter(listCart, this, new CartAdapter.OnItemCheckListener() {
            @Override
            public void onItemCheck(Cart cart) {
                currentSelectedItem.add(cart);
            }

            @Override
            public void onItemUncheck(Cart cart) {
                currentSelectedItem.remove(cart);

            }
        });
        recycleCart.setAdapter(adapterCart);
        btnTotalMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i=0; i<currentSelectedItem.size();i++){
                    currentSelectedItem.get(i).setSoLuongTorngKho(currentSelectedItem.get(i).getSoLuongTorngKho()-currentSelectedItem.get(i).getSoluong());
                    Log.d("item",currentSelectedItem.get(i).getSoLuongTorngKho()-currentSelectedItem.get(i).getSoluong()+"");

                }
                if(currentSelectedItem.size()==0){
                    Toast.makeText(CartActivity.this, "Chưa chọn sản phẩm", Toast.LENGTH_SHORT).show();


                }else {
                    StringRequest stringRequest1 = new StringRequest(Request.Method.POST, "https://nickkimbum.000webhostapp.com/myShopInsertToOrder.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("resopne",response);
                                    order_id_get = Integer.parseInt(response.trim());
                                    Toast.makeText(CartActivity.this, "Thêm order thành công", Toast.LENGTH_SHORT).show();
                                    StringRequest stringRequest2 = new StringRequest(Request.Method.POST, "https://nickkimbum.000webhostapp.com/myShopInsertOrderDetail.php",
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    Log.d("response2",order_id_get + response);
                                                    for (int i=0; i<currentSelectedItem.size();i++){
                                                        Log.d("item",currentSelectedItem.get(i).getSoLuongTorngKho()-currentSelectedItem.get(i).getSoluong()+"");
                                                    }

                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(CartActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                                }
                                            }){
                                        @Nullable
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map params = new HashMap();
                                            for(int i=0; i<currentSelectedItem.size();i++){
                                                params.put("order_id_post",String.valueOf(order_id_get));
                                                params.put("product_id_post["+i+"]",String.valueOf(currentSelectedItem.get(i).getProduct_id()));
                                                params.put("num["+i+"]",String.valueOf(currentSelectedItem.get(i).getSoluong()));
                                            }
                                            return params;
                                        }
                                    };
                                    requestQueue.add(stringRequest2);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("error134",error.toString());
                                }
                            }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map params = new HashMap();
                            params.put("user_id_post",String.valueOf(new SessionManager(CartActivity.this).getUserDetailFormSession().getId()));
                            params.put("note_post","note_post");
                            return params;
                        }
                    };
                    //Xuly
                    StringRequest stringUpdate = new StringRequest(Request.Method.POST, "https://nickkimbum.000webhostapp.com/myShopConUpdateNewNumProductWhenOrder.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("resopnse3",response.toString());
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
                            for (int i=0; i<currentSelectedItem.size();i++){
                                params.put("product_id_post["+i+"]",String.valueOf(currentSelectedItem.get(i).getProduct_id()));
                                params.put("newNum["+i+"]",String.valueOf(currentSelectedItem.get(i).getSoLuongTorngKho()));
                            }
                            return params;
                        }
                    };
                    requestQueue.add(stringRequest1);
                    requestQueue.add(stringUpdate);
                    ArrayList<Integer> listRemove = new ArrayList();
                    for(int i=0; i<currentSelectedItem.size();i++){
                        for(int j=0; j<listCart.size(); j++){
                            if(listCart.get(j).getProduct_id() == currentSelectedItem.get(i).getProduct_id()){
                                listRemove.add(listCart.get(j).getProduct_id());
                                listCart.remove(j);
                            }
                        }
                    }
                    //update pre_order_details: xoa ban gi va cap nhat so luong
                    //xoa ban ghi
                    //remove
                    StringRequest stringRemove = new StringRequest(Request.Method.POST, "https://nickkimbum.000webhostapp.com/yShopRemovePreAfterOrder.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("response 235",response);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(CartActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map params = new HashMap();
                            params.put("user_id_post", String.valueOf(new SessionManager(CartActivity.this).getUserDetailFormSession().getId()));
                            for (int i=0; i<listRemove.size();i++){
                                params.put("product_id_post["+i+"]",String.valueOf(listRemove.get(i)));
                            }
                            return params;
                        }
                    };
                    requestQueue.add(stringRemove);
                    adapterCart.notifyDataSetChanged();
                }
            }
        });
    }

}