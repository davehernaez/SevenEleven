package com.hernaez.seven_eleven.model.dataaccesslayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.hernaez.seven_eleven.domain.Order;
import com.hernaez.seven_eleven.domain.Product;

/**
 * Created by jaenelleisidro on 7/13/15.
 */
public class OrderDao {
    public static final String TABLE_ORDERS = "table_orders";
    DBHelper dbhelper;
    public OrderDao(DBHelper dbhelper){
        this.dbhelper=dbhelper;
    }

    public void add(Order order){
        Product product=order.product;
        SQLiteDatabase db = dbhelper.getWritableDatabase();
            db = dbhelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBHelper.PRODUCT_ID, product.id);
            values.put(DBHelper.PRODUCT_NAME, product.product_name);
            values.put(DBHelper.PRODUCT_PRICE, product.product_price);
            values.put(DBHelper.PRODUCT_QTY, product.product_qty);
            values.put(DBHelper.PRODUCT_SUBTOTAL, order.total);
            values.put(DBHelper.PRODUCT_IMGPATH, product.product_imgpath);
            db.insert(TABLE_ORDERS, null, values);
            db.close();
    }

    public void update(Product product){

    }
}
