package com.hernaez.seven_eleven.model.dataaccesslayer;

import com.hernaez.seven_eleven.domain.Product;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TAS on 7/15/2015.
 */
public class PlaceOrderHttp {
    public static final String HTTP_DOMAIN = "http://seveneleven.net46.net";
    public static final String HTTP_END_DOMAIN = "/android_connect/place_order.php";
    HttpAdapter httpAdapter;

    public PlaceOrderHttp(HttpAdapter httpAdapter) { this.httpAdapter = httpAdapter; }

    public void placeOrder(Integer orderId, Product product) throws  Exception{
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("order_id", orderId.toString()));
        nameValuePairs.add(new BasicNameValuePair("product_id", product.id.toString()));
        nameValuePairs.add(new BasicNameValuePair("order_qty", product.product_qty.toString()));

        httpAdapter.post(HTTP_DOMAIN+ HTTP_END_DOMAIN, nameValuePairs);

    }
}
