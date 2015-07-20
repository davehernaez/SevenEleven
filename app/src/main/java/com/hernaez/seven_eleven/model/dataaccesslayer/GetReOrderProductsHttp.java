package com.hernaez.seven_eleven.model.dataaccesslayer;

import com.hernaez.seven_eleven.domain.Product;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TAS on 7/10/2015.
 */

public class GetReOrderProductsHttp {
    HttpAdapter httpAdapter;
    List<Product> products = new ArrayList<Product>();

    public static final String BASE_DOMAIN = "http://seveneleven.esy.es";
    public static final String END_DOMAIN = "/android_connect/get_all_products.php";

    public GetReOrderProductsHttp(HttpAdapter httpAdapter) {
        this.httpAdapter = httpAdapter;
    }

    public List<Product> getReOrderProducts() throws Exception {

        String jsonString = httpAdapter.post(BASE_DOMAIN + END_DOMAIN).replaceAll("\\s+", " ");

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
