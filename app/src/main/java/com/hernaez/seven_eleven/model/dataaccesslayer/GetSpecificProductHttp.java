package com.hernaez.seven_eleven.model.dataaccesslayer;

import android.content.ContentValues;
import android.provider.Settings;
import android.util.Log;

import com.hernaez.seven_eleven.domain.Product;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TAS on 7/10/2015.
 */
public class GetSpecificProductHttp {
    HttpAdapter httpAdapter;
    Product product;
    public static final String HTTP_DOMAIN="http://seveneleven.esy.es";
    public static final String HTTP="/android_connect/get_price.php";

    public GetSpecificProductHttp(){
        httpAdapter = new HttpAdapter();

    }

    public Product getSpecificProduct(String product_name) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add (new BasicNameValuePair("product_name", product_name));

        String jsonString = httpAdapter.post(HTTP_DOMAIN + HTTP, nameValuePairs).replaceAll("\\s+", " ");

        JSONArray jsonArray = new JSONArray(jsonString);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        String productname = jsonObject.getString("product_name");
        Log.e("GetSpecificProduct", "accessed "+productname);
        product = new Product(jsonObject.getString("product_name"),
                jsonObject.getString("product_price"),
                jsonObject.getString("product_qty"),
                null,
                jsonObject.getString("image_path"));

        return product;
    }
}
