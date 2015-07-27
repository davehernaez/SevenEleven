package com.hernaez.seven_eleven.model.dataaccesslayer;

import android.util.Log;

import com.hernaez.seven_eleven.domain.Product;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TAS on 7/10/2015.
 */
public class ReOrderHttp {
    HttpAdapter httpAdapter;
    //public static final String HTTP_DOMAIN = "http://seveneleven.net46.net";
    public static final String HTTP_DOMAIN = "http://seveneleven.esy.es";
    public static final String HTTP = "/android_connect/reorder.php";

    public ReOrderHttp(HttpAdapter httpAdapter) {
        this.httpAdapter = httpAdapter;
    }

    public void reOrder(String product_name, String product_qty) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("product_name", product_name));
        nameValuePairs.add(new BasicNameValuePair("product_qty", product_qty));

        String jsonString = httpAdapter.post(HTTP_DOMAIN + HTTP, nameValuePairs).replaceAll("\\s+"," ");


        Log.e("httAdapter", " has been executed. " + HTTP_DOMAIN + HTTP + " " + nameValuePairs);
        Log.e("Status", jsonString);

    }
}
