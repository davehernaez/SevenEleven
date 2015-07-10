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

    public GetReOrderProductsHttp() {
        httpAdapter = new HttpAdapter();
    }

    public List<Product> getReOrderProducts() throws Exception {

        String http_output = httpAdapter.httpPost(BASE_DOMAIN + END_DOMAIN).replaceAll("\\s+", " ");

        JSONArray jsonArray = new JSONArray(http_output);

        for (int i = 0, count = jsonArray.length(); i < count; i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            Product product = new Product(jsonObject.getString("product_name"),
                    null,
                    jsonObject.getString("product_qty"),
                    null,
                    jsonObject.getString("image_path"));


            if (jsonObject.getInt("product_qty") < 10) {
                products.add(product);
            }


        }


        return products;
    }
}
