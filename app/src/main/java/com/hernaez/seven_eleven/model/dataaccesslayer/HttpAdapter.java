package com.hernaez.seven_eleven.model.dataaccesslayer;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by jaenelleisidro on 7/8/15.
 */
public class HttpAdapter {

    HttpClient httpclient;
    public HttpAdapter(){
        httpclient = new DefaultHttpClient();
    }

    public String post(String url) throws Exception{
        return post(url,null);
    }

    public String post(String url,List<NameValuePair> nameValuePairs) throws Exception{
        Log.e("HttAdapter","Connecting to server...");
        HttpPost httppostURL = new HttpPost(url);
        if(nameValuePairs!=null){
            httppostURL.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        }
        HttpResponse response = httpclient.execute(httppostURL);
        HttpEntity entity = response.getEntity();
        InputStream inputstream = entity.getContent();
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputstream, "iso-8859-1"), 8);
        StringBuilder stringBuilder = new StringBuilder();
        String singleLine = null;

        while ((singleLine = bufferedReader.readLine()) != null) {
            stringBuilder.append(singleLine + "\n");
        }

        inputstream.close();
        return stringBuilder.toString();
    }
}
