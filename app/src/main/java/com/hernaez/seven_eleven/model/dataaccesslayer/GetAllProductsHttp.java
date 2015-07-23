package com.hernaez.seven_eleven.model.dataaccesslayer;

import android.util.Log;

import com.hernaez.seven_eleven.domain.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TAS on 7/9/2015.
 */
public class GetAllProductsHttp {
    //http://seveneleven.net46.net/android_connect/get_all_products.php
    public static final String HTTP_DOMAIN = "http://seveneleven.net46.net";
    public static final String HTTP = "/android_connect/get_all_products.php";

    HttpAdapter httpAdapter;

    public GetAllProductsHttp(HttpAdapter httpAdapter) {
        this.httpAdapter = httpAdapter;
        /*httpAdapter = new HttpAdapter();*/
    }

    public List<Product> getAllProducts() throws Exception {
        List<Product> products = new ArrayList<Product>();
        String jsonString = httpAdapter.post(HTTP_DOMAIN + HTTP);
        jsonString.replaceAll("\\s+", " ");
        Log.e("allProducts",jsonString);

        JSONArray jasonArray = new JSONArray(jsonString);

        for (int i = 0, count = jasonArray.length(); i < count; i++) {
            JSONObject jsonObject = jasonArray.getJSONObject(i);

            Product product = new Product();

            product.id = jsonObject.getInt("product_id");
            product.product_name = jsonObject.getString("product_name");
            product.product_price = jsonObject.getDouble("product_price");
            product.product_qty = jsonObject.getInt("product_qty");
            product.product_imgpath = jsonObject.getString("image_path");
            products.add(product);
        }
        return products;
    }

    public List<String> getAllProductsName() throws Exception {
        List<String> myList = new ArrayList<String>();

        String jsonString = httpAdapter.post(HTTP_DOMAIN + HTTP).replaceAll("\\s+", " ");

        JSONArray jasonArray = new JSONArray(jsonString);

        for (int i = 0, count = jasonArray.length(); i < count; i++) {
            JSONObject jsonObject = jasonArray.getJSONObject(i);

            if (Integer.parseInt(jsonObject.getString("product_qty")) > 0) {
                myList.add(jsonObject.getString("product_name"));
            }
        }
        return myList;
    }

}
