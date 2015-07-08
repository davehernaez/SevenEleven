package com.hernaez.seven_eleven.com.hernaez.seven_eleven.model.businesslayer;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.hernaez.seven_eleven.com.hernaez.seven_eleven.domain.User;
import com.hernaez.seven_eleven.com.hernaez.seven_eleven.model.dataaccesslayer.HttpAdapter;
import com.hernaez.seven_eleven.com.hernaez.seven_eleven.model.dataaccesslayer.LoginHttpAdapter;
import com.hernaez.seven_eleven.com.hernaez.seven_eleven.viewcontroller.activity.AdminPageActivity;
import com.hernaez.seven_eleven.com.hernaez.seven_eleven.viewcontroller.activity.CustomerOrderActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaenelleisidro on 7/8/15.
 */
public class Login {
    LoginHttpAdapter loginHttpAdapter;
    public Login(){
        loginHttpAdapter=new LoginHttpAdapter();
    }
    public User userLogin(String userName, String password) throws Exception {
        User user=loginHttpAdapter.getUser(userName,password);
        return user;
    }
}
