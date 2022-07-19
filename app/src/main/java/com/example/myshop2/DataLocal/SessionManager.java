package com.example.myshop2.DataLocal;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myshop2.ObjectClass.User;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences userSP;
    SharedPreferences.Editor editor;
    Context mContext;

    private static final String IS_LOGIN ="IS_LOGIN";

    private static final String KEY_ID ="KEY_ID";
    private static final String KEY_ROLE_ID ="KEY_ROLE_ID";
    private static final String KEY_FULLNAME ="KEY_FULLNAME";
    private static final String KEY_EMAIL ="KEY_EMAIL";
    private static final String KEY_PHONE_NUMBER ="KEY_PHONE_NUMBER";
    private static final String KEY_ADDRESS ="KEY_ADDRESS";
    private static final String KEY_PASSWORD ="KEY_PASSWORD";
    private static final String KEY_AVATAR ="KEY_AVATAR";

    public SessionManager(Context context) {
        this.mContext = context;
        userSP = context.getSharedPreferences("userLoginSession", Context.MODE_PRIVATE);
        editor = userSP.edit();

    }
    //them dữ liệu vào trong sharedprerence
    public void initLoginSession(int id, int role_id, String fullname, String email, String phone_number, String address, String password, String avatar){
        editor.putBoolean(IS_LOGIN, true);

        editor.putInt(KEY_ID, id);
        editor.putInt(KEY_ROLE_ID, role_id);
        editor.putString(KEY_FULLNAME, fullname);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PHONE_NUMBER, phone_number);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_AVATAR, avatar);
        editor.commit();
    }
    public User getUserDetailFormSession(){
        User userData = new User();
        userData.setId(userSP.getInt(KEY_ID,-1));
        userData.setRole_id(userSP.getInt(KEY_ROLE_ID,-1));
        userData.setFullname(userSP.getString(KEY_FULLNAME,null));
        userData.setEmail(userSP.getString(KEY_EMAIL,null));
        userData.setPhone_number(userSP.getString(KEY_PHONE_NUMBER,null));
        userData.setAddress(userSP.getString(KEY_ADDRESS,null));
        userData.setPassword(userSP.getString(KEY_PASSWORD,null));
        userData.setAvatar(userSP.getString(KEY_AVATAR,null));
        return userData;
    }
    public boolean checkLogin(){
        if(userSP.getBoolean(IS_LOGIN,false)){
            return true;
        }
        else return false;
    }
    public void logoutFromSession(){
        editor.clear();
        editor.commit();
    }
}
