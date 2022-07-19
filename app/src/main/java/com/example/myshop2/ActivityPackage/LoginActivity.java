package com.example.myshop2.ActivityPackage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.example.myshop2.DataLocal.SessionManager;
import com.example.myshop2.ObjectClass.User;
import com.example.myshop2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextView btnCreateAccount;
    LinearLayout btnLogin;
    EditText editLoginEmail, editLoginPass;
    User userget = null;
    String url="https://nickkimbum.000webhostapp.com/get1UserByEmailAndPassword.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AnhXa();
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginClickCheck(url);

            }
        });
    }

    private void LoginClickCheck(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            userget = new User(object.getInt("ID"),object.getInt("Role_id"),object.getString("Fullname"), object.getString("Email"), object.getString("Phone_number"), object.getString("Address"), object.getString("Password"),object.getString("Avatar"));
                            if (userget == null){
                                Toast.makeText(LoginActivity.this, "Password or email are wrong!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("user",userget);
//                    intent.putExtras(bundle);
                                SessionManager sessionManager = new SessionManager(getApplicationContext());
                                sessionManager.initLoginSession(userget.getId(), userget.getRole_id(), userget.getFullname(), userget.getEmail(), userget.getPhone_number(), userget.getAddress(), userget.getPassword(), userget.getAvatar());
                                LoginActivity.this.startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("LoginError:", error.toString());
                        userget = null;
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map params = new HashMap();
                params.put("EmailUser",editLoginEmail.getText().toString());
                params.put("PassUser",editLoginPass.getText().toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void AnhXa() {
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        btnLogin = findViewById(R.id.btnLogin);
        editLoginEmail = findViewById(R.id.editLoginEmail);
        editLoginPass = findViewById(R.id.editLoginPass);
    }
}