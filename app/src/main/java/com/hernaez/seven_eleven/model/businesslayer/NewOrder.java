package com.hernaez.seven_eleven.model.businesslayer;

import com.hernaez.seven_eleven.domain.Order;
import com.hernaez.seven_eleven.model.dataaccesslayer.NewOrderHttp;

/**
 * Created by TAS on 7/20/2015.
 */
public class NewOrder {
    NewOrderHttp newOrderHttp;

    public NewOrder(NewOrderHttp newOrderHttp) {
        this.newOrderHttp = newOrderHttp;

    }

    public Order newOrder(Integer userid) throws Exception {
        Order order = newOrderHttp.newOrder(userid);
        return order;
    }
}
