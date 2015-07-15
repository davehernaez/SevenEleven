package com.hernaez.seven_eleven.model.dataaccesslayer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hernaez.seven_eleven.domain.Order;
import com.hernaez.seven_eleven.domain.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaenelleisidro on 7/13/15.
 */
public class OrderDao {
    public static final String TABLE_ORDERS = "table_orders";
    DBHelper dbhelper;

    public OrderDao(DBHelper dbhelper) {
        this.dbhelper = dbhelper;
    }

    public Order checkOrder(Order order) {
        Product product = order.product;
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        String where = DBHelper.PRODUCT_NAME + "='"
                + product.product_name + "'";
        Cursor c = db.query(true, OrderDao.TABLE_ORDERS, DBHelper.ALL_FIELDS,
                where, null, null, null, null, null, null);
        if (c != null) {
            if (c.moveToFirst()) {
                String productName = c.getString(c.getColumnIndex(DBHelper.PRODUCT_NAME));
                String productPrice = c.getString(c.getColumnIndex(DBHelper.PRODUCT_PRICE));
                String productQty = c.getString(c.getColumnIndex(DBHelper.PRODUCT_QTY));
                String productSubtotal = c.getString(c.getColumnIndex(DBHelper.PRODUCT_SUBTOTAL));

                order.product = order.product;
                Product newProdct = new Product();
                order.orderedProduct = newProdct;
                newProdct.product_name = productName;
                newProdct.product_price = productPrice;
                newProdct.product_qty = productQty;
                newProdct.subtotal = productSubtotal;

                c.close();
                db.close();
                return order;
            }
        }
        return order;

    }


    public void update(ContentValues cv, Order order) {
        Product product = order.product;
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.update(OrderDao.TABLE_ORDERS, cv, DBHelper.PRODUCT_NAME + "='"
                + product.product_name + "'", null);
        db.close();

    }

    public void add(Order order) {
        Product product = order.product;
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.PRODUCT_ID, product.id);
        values.put(DBHelper.PRODUCT_NAME, product.product_name);
        values.put(DBHelper.PRODUCT_PRICE, product.product_price);
        values.put(DBHelper.PRODUCT_QTY, product.product_qty);
        values.put(DBHelper.PRODUCT_SUBTOTAL, order.total);
        values.put(DBHelper.PRODUCT_IMGPATH, product.product_imgpath);
        Log.e("inserting:", values + "");
        db.insert(TABLE_ORDERS, null, values);
        db.close();
    }

    public void update(Product product) {

    }

    public List<Product> getAllOrders() {
        List<Product> products = new ArrayList<Product>();
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        String where = null;
        Cursor c = db.query(true, TABLE_ORDERS, DBHelper.ALL_FIELDS,
                where, null, null, null, null, null);
        if (c != null) {
            for (int i = 0, count = c.getCount(); i < count; i++) {
                c.moveToNext();
                Product product = new Product();
                product.id = c.getString(c.getColumnIndex(DBHelper.PRODUCT_ID));
                product.product_name = c.getString(c.getColumnIndex(DBHelper.PRODUCT_NAME));
                product.product_price = c.getString(c.getColumnIndex(DBHelper.PRODUCT_PRICE));
                product.product_qty = c.getString(c.getColumnIndex(DBHelper.PRODUCT_QTY));
                product.product_imgpath = c.getString(c.getColumnIndex(DBHelper.PRODUCT_IMGPATH));
                product.subtotal = c.getString(c.getColumnIndex(DBHelper.PRODUCT_SUBTOTAL));
                products.add(product);

                Log.e("Orders:", product.product_name);

            }

        }
        c.close();
        db.close();
        return products;
    }

    public void deleteSpecific(String productName) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.delete(OrderDao.TABLE_ORDERS, DBHelper.PRODUCT_NAME + "='" + productName
                + "'", null);

        db.close();
    }

    public void getOrders() {
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        Cursor c = db.rawQuery("select * from '" + OrderDao.TABLE_ORDERS + "'",
                null);

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                //String prodname = c.getString(c.getColumnIndex("product_name"));
                String prodid_fromdb = c.getString(c.getColumnIndex("_id"));
                String prodqty_fromdb = c.getString(c.getColumnIndex("product_qty"));


                Log.e("from db", prodid_fromdb + prodqty_fromdb);
                c.moveToNext();
            }
        }
        if (c.isAfterLast()) {
            c.close();
            db.close();
        }
    }

    public void deleteAll() {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.delete(OrderDao.TABLE_ORDERS, null, null);
        db.close();
    }
}
