package com.hernaez.seven_eleven.model.dataaccesslayer;

import android.util.Log;

import com.hernaez.seven_eleven.domain.Order;
import com.hernaez.seven_eleven.other.HttpConstant;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TAS on 7/15/2015.
 */
public class NewOrderHttp {
    //public static final String HTTP_DOMAIN = "http://seveneleven.net46.net";
    //public static final String HTTP_DOMAIN = "http://seveneleven.esy.es";
    //public static final String HTTP_END_DOMAIN = "/android_connect/order.php";
    HttpAdapter httpAdapter;

    public NewOrderHttp(HttpAdapter httpAdapter) {
        this.httpAdapter = httpAdapter;
    }

    public Order newOrder(Integer userid) throws Exception {
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("user_id", userid.toString()));
        String jsonString = httpAdapter.post(HttpConstant.HTTP_NEW_ORDER, nameValuePairs).replaceAll("\\s+", " ");
        JSONArray jsonArray = new JSONArray(jsonString);
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        Order order = new Order();
        order.id = jsonObject.getInt("order_id");
        return order;
    }
}
