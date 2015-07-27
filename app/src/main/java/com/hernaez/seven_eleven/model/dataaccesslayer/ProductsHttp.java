package com.hernaez.seven_eleven.model.dataaccesslayer;

import android.util.Log;

import com.hernaez.seven_eleven.domain.Product;
import com.hernaez.seven_eleven.other.HttpConstant;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TAS on 7/9/2015.
 */
public class ProductsHttp {
    HttpAdapter httpAdapter;

    public ProductsHttp(HttpAdapter httpAdapter) {
        this.httpAdapter = httpAdapter;

    }

    public List<Product> getAllProducts() throws Exception {
        List<Product> products = new ArrayList<Product>();
        String jsonString = httpAdapter.post(HttpConstant.HTTP_GET_ALL_PRODUCTS);
        jsonString.replaceAll("\\s+", " ");
        Log.e("allProducts", jsonString);

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

        String jsonString = httpAdapter.post(HttpConstant.HTTP_GET_ALL_PRODUCTS).replaceAll("\\s+", " ");

        JSONArray jasonArray = new JSONArray(jsonString);

        for (int i = 0, count = jasonArray.length(); i < count; i++) {
            JSONObject jsonObject = jasonArray.getJSONObject(i);

            if (Integer.parseInt(jsonObject.getString("product_qty")) > 0) {
                myList.add(jsonObject.getString("product_name"));
            }
        }
        return myList;
    }

    public Product getSpecificProduct(String product_name) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("product_name", product_name));

        String jsonString = httpAdapter.post(HttpConstant.HTTP_GET_SPECIFIC_PRODUCT, nameValuePairs).replaceAll("\\s+", " ");

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

    List<Product> products = new ArrayList<Product>();

    public List<Product> getReOrderProducts() throws Exception {

        String jsonString = httpAdapter.post(HttpConstant.HTTP_GET_ALL_PRODUCTS).replaceAll("\\s+", " ");

        JSONArray jasonArray = new JSONArray(jsonString);

        for (int i = 0, count = jasonArray.length(); i < count; i++) {
            JSONObject jsonObject = jasonArray.getJSONObject(i);

            /*Product product = new Product(jsonObject.getString("product_name"),
                    jsonObject.getString("product_price"),
                    jsonObject.getString("product_qty"),
                    jsonObject.getString("image_path"));*/

            Product product = new Product();
            product.product_name = jsonObject.getString("product_name");
            product.product_price = null;
            product.product_qty = jsonObject.getInt("product_qty");
            product.product_imgpath = jsonObject.getString("image_path");

            if (product.product_qty < 10) {
                products.add(product);
            }

        }
        return products;
    }

}