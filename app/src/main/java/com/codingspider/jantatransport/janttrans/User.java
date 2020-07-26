package com.codingspider.jantatransport.janttrans;

import android.content.Context;
import android.content.SharedPreferences;


public class User {

    Context context;

    public void remove(){
        sharedPreferences.edit().clear().commit();
    }

    public String getPhone() {

       phone = sharedPreferences.getString("phonedata","");
        return phone;

    }

    public void setPhone(String phone) {
        this.phone = phone;
        sharedPreferences.edit().putString("phonedata",phone).commit();
    }

    private String phone;


    SharedPreferences sharedPreferences;

    public User(Context context){

        this.context=context;
        sharedPreferences=context.getSharedPreferences("userphone",Context.MODE_APPEND);

    }

}
