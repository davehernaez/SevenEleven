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
    public static final String HTTP_DOMAIN="http://seveneleven.net46.net";
    public static final String HTTP="/android_connect/get_price.php";

    public GetSpecificProductHttp(HttpAdapter httpAdapter){
        this.httpAdapter = httpAdapter;

    }

    public Product getSpecificProduct(String product_name) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add (new BasicNameValuePair("product_name", product_name));

        String jsonString = httpAdapter.post(HTTP_DOMAIN + HTTP, nameValuePairs).replaceAll("\\s+", " ");

        JSONArray jsonArray = new JSONArray(jsonString);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        String productname = jsonObject.getString("product_name");

        Product product = new Product();

        product.id = jsonObject.getInt("product_id");
        product.product_name = null;
        product.product_price = jsonObject.getDouble("product_price");
        product.product_qty = jsonObject.getInt("product_qty");
        product.product_imgpath = jsonObject.getString("image_path");

        return product;
    }
}
