package com.hernaez.seven_eleven.model.businesslayer;

import android.content.ContentValues;

import com.hernaez.seven_eleven.domain.Order;
import com.hernaez.seven_eleven.domain.Product;
import com.hernaez.seven_eleven.model.dataaccesslayer.DBHelper;
import com.hernaez.seven_eleven.model.dataaccesslayer.NewOrderHttp;
import com.hernaez.seven_eleven.model.dataaccesslayer.OrderDao;
import com.hernaez.seven_eleven.model.dataaccesslayer.PlaceOrderHttp;

import java.util.List;

/**
 * Created by TAS on 7/13/2015.
 */
public class OrderManager {
    OrderDao orderDao;

    public OrderManager(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public List<Product> getAllOrders() {
        List<Product> products = orderDao.getAllOrders();
        return products;
    }

    public void add(Order order) {
        orderDao.add(order);
    }

    public void update(ContentValues contentValues, Order order) {
        orderDao.update(contentValues, order);

    }

    public boolean checkOrders(Order order) {

        ContentValues contentValues = new ContentValues();
        Order newOrder = orderDao.checkOrder(order);
        Product product = order.product;
        Product newProduct = newOrder.orderedProduct;
        if (newProduct != null) {
            //correct new values for updating product in database
            Integer newQty = product.product_qty + newProduct.product_qty;
            Double newTotal = product.subtotal + newProduct.subtotal;
            contentValues.put(DBHelper.PRODUCT_QTY, newQty);
            contentValues.put(DBHelper.PRODUCT_SUBTOTAL, newTotal);

            update(contentValues, order);
            return false;
        } else {
            add(order);
            return true;
        }
    }

    public void deleteSpecific(String productName) {
        orderDao.deleteSpecific(productName);
    }


    public void deleteAll() {
        orderDao.deleteAll();
    }

}
