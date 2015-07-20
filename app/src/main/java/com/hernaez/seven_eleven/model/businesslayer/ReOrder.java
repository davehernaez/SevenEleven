package com.hernaez.seven_eleven.model.businesslayer;

import com.hernaez.seven_eleven.model.dataaccesslayer.ReOrderHttp;

/**
 * Created by TAS on 7/10/2015.
 */
public class ReOrder {
    ReOrderHttp reOrderHttp;


    public ReOrder(ReOrderHttp reOrderHttp){
       this.reOrderHttp = reOrderHttp;

    }

    public void reOrder(String product_name, String product_qty) throws Exception{

        reOrderHttp.reOrder(product_name, product_qty);
    }
}
