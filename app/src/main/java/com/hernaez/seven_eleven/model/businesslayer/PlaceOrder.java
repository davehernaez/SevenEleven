package com.hernaez.seven_eleven.model.businesslayer;

import com.hernaez.seven_eleven.domain.Product;
import com.hernaez.seven_eleven.model.dataaccesslayer.PlaceOrderHttp;

/**
 * Created by TAS on 7/20/2015.
 */
public class PlaceOrder {
    PlaceOrderHttp placeOrderHttp;
    public PlaceOrder(PlaceOrderHttp placeOrderHttp){
        this.placeOrderHttp = placeOrderHttp;
    }

    public void placeOrder(Integer orderId, Product product) throws Exception {
        placeOrderHttp.placeOrder(orderId, product);


    }
}
