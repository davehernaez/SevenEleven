package com.hernaez.seven_eleven.model.dataaccesslayer;

import com.hernaez.seven_eleven.domain.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TAS on 7/9/2015.
 */
public class GetAllProductsHttp {

    public static final List<Product> rowItems = new ArrayList<Product>();
    public static final String HTTP_DOMAIN = "http://seveneleven.esy.es";
    public static final String HTTP = "/android_connect/get_all_products.php";

    HttpAdapter httpAdapter;

    public GetAllProductsHttp() {
        httpAdapter = new HttpAdapter();
    }

    public List<Product> getAllProducts() throws Exception {

        String jsonString = httpAdapter.post(HTTP_DOMAIN + HTTP).replaceAll("\\s+", " ");

        JSONArray jasonArray = new JSONArray(jsonString);

        for (int i = 0, count = jasonArray.length(); i < count; i++) {
            JSONObject jsonObject = jasonArray.getJSONObject(i);

            /*Product product = new Product(jsonObject.getString("product_name"),
                    jsonObject.getString("product_price"),
                    jsonObject.getString("product_qty"),
                    jsonObject.getString("image_path"));*/

            Product product = new Product();
            product.product_name = jsonObject.getString("product_name");
            product.product_price = jsonObject.getString("product_price");
            product.product_qty= jsonObject.getString("product_qty") ;
            product.product_imgpath = jsonObject.getString("image_path");
            rowItems.add(product);
        }
        return rowItems;
    }

    public List<String> getAllProductsName() throws Exception {
        List<String> myList= new ArrayList<String>();

        String jsonString = httpAdapter.post(HTTP_DOMAIN + HTTP).replaceAll("\\s+", " ");

        JSONArray jasonArray = new JSONArray(jsonString);

        for (int i = 0, count = jasonArray.length(); i < count; i++) {
            JSONObject jsonObject = jasonArray.getJSONObject(i);

            /*Product product = new Product(jsonObject.getString("product_name"),
                    jsonObject.getString("product_price"),
                    jsonObject.getString("product_qty"),
                    jsonObject.getString("image_path"));*/

            myList.add(jsonObject.getString("product_name"));
        }
        return myList;
    }

}
