package com.example.myshop2.ActivityPackage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myshop2.DataLocal.SessionManager;
import com.example.myshop2.ObjectClass.User;
import com.example.myshop2.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText editRegisterEmail,editRegisterPassword,editRegisterFullName,editRegisterPhoneNumber,editRegisterAdsress;
    LinearLayout btnCreateUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AnhXa();
        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String fullname, String email, String phone_number, String address, String password, String avatar
                User user = new User(editRegisterFullName.getText().toString(),editRegisterEmail.getText().toString(),editRegisterPhoneNumber.getText().toString(),editRegisterAdsress.getText().toString(),editRegisterPassword.getText().toString());
                if(editRegisterEmail.getText().equals("")||editRegisterPassword.getText().equals("")||editRegisterFullName.getText().equals("")||editRegisterPhoneNumber.getText().equals("")||editRegisterAdsress.getText().equals("")){
                    Toast.makeText(RegisterActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    //insert user
                    RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://nickkimbum.000webhostapp.com/myShopInsertUser.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("response",response);
                                    user.setId(Integer.parseInt(response));
                                    //clear session
                                    SessionManager sesion = new SessionManager(RegisterActivity.this);
                                    sesion.logoutFromSession();
                                    SessionManager sessionNew = new SessionManager(RegisterActivity.this);
                                    sessionNew.initLoginSession(user.getId(),user.getRole_id(),user.getFullname(),user.getEmail(),user.getPhone_number(),user.getAddress(),user.getPassword(),user.getAvatar());
                                    // create session
                                    Intent intent = new Intent(RegisterActivity.this,HomeScreenActivity.class);
                                    RegisterActivity.this.startActivity(intent);


                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }){
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map params = new HashMap();
                            params.put("email_post",user.getEmail());
                            params.put("password_post",user.getPassword());
                            params.put("fullname_post",user.getFullname());
                            params.put("phonenumber_post",user.getPhone_number());
                            params.put("address_post",user.getAddress());

                            return params;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });

    }
    private void AnhXa() {
        editRegisterEmail = findViewById(R.id.editRegisterEmail);
        editRegisterPassword = findViewById(R.id.editRegisterPassword);
        editRegisterFullName = findViewById(R.id.editRegisterFullName);
        editRegisterPhoneNumber = findViewById(R.id.editRegisterPhoneNumber);
        editRegisterAdsress = findViewById(R.id.editRegisterAdsress);
        btnCreateUser= findViewById(R.id.btnCreateUser);
    }
}