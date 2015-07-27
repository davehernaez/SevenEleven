package com.hernaez.seven_eleven.model.dataaccesslayer;

import android.util.Log;

import com.hernaez.seven_eleven.domain.User;
import com.hernaez.seven_eleven.other.HttpConstant;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaenelleisidro on 7/8/15.
 */
public class LoginHttpAdapter{
    User user=new User();
    //http://seveneleven.net46.net/android_connect/test.php
    //public static final String HTTP_DOMAIN="http://seveneleven.net46.net";
    /*public static final String HTTP_DOMAIN = "http://seveneleven.esy.es";
    public static final String HTTP_LOGIN="/android_connect/login.php";*/




    HttpAdapter httpAdapter;

    public LoginHttpAdapter(HttpAdapter httpAdapter){
        //httpAdapter = new HttpAdapter();
        this.httpAdapter=httpAdapter;
    }

    public User getUser(String userName,String password) throws Exception{
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("username", userName));
        nameValuePairs.add(new BasicNameValuePair("password", password));

        String jsonString=httpAdapter.post(HttpConstant.HTTP_LOGIN,nameValuePairs);
        Log.e("jsonString",jsonString);

        jsonString = jsonString.replaceAll("\\s+", " ");
        JSONArray jasonArray = new JSONArray(jsonString);
        JSONObject jsonObject = jasonArray.getJSONObject(0);

        user.userType=jsonObject.getString("userType");
        user.userName=userName;
        user.userId=jsonObject.getInt("userId");
        return user;
    }
}
