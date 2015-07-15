package com.hernaez.seven_eleven.viewcontroller.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.domain.Order;
import com.hernaez.seven_eleven.domain.Product;
import com.hernaez.seven_eleven.model.businesslayer.OrderManager;
import com.hernaez.seven_eleven.model.dataaccesslayer.DBHelper;
import com.hernaez.seven_eleven.model.dataaccesslayer.OrderDao;
import com.hernaez.seven_eleven.viewcontroller.adapter.CustomViewAdapter2;

import java.util.List;

/**
 * Created by TAS on 7/7/2015.
 */
public class OrderSummaryActivity extends Activity implements AdapterView.OnItemLongClickListener,
        AdapterView.OnItemClickListener, View.OnClickListener {
    DBHelper dbhelper;
    ListView lv;
    String[] fromDB;
    Cursor c;
    int[] toView;
    Bitmap bmp;
    TextView tv_total, tv_grantotal;
    Double total;
    String prodsubtotal, userid, orderId;
    Intent i;
    Button btn_confirm;
    OrderManager orderManager;
    OrderDao orderDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_summary);

        Bundle extras = getIntent().getExtras();

        orderId = "1";

        if (extras != null) {
            userid = extras.getString("user_id");
            Log.e("userid", userid);
        }

        tv_total = (TextView) findViewById(R.id.textView_summary_grandtotal);
        tv_grantotal = (TextView) findViewById(R.id.textView_summary_grandtotal);

        lv = (ListView) findViewById(R.id.listView_order_summary);

        dbhelper = new DBHelper(this);
        orderDao = new OrderDao(dbhelper);
        orderManager = new OrderManager(orderDao);


        thread.start();

        total = 0.0;

        lv.setOnItemLongClickListener(this);

        lv.setOnItemClickListener(this);

        btn_confirm = (Button) findViewById(R.id.button_clear_summary);

        btn_confirm.setOnClickListener(this);

    }

    Thread thread = new Thread() {
        public void run() {
            populate();
        }

        ;
    };

    public void populate() {
        List<Product> product = orderManager.getAllOrders();

        CustomViewAdapter2 myadapter = new CustomViewAdapter2(getApplicationContext(), R.layout.order_summary_holder, product);
        lv.setAdapter(myadapter);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View view,
                                   int position, long id) {
        // TODO Auto-generated method stub
        TextView tv_name = (TextView) view
                .findViewById(R.id.textView_order_summary_productname);
        TextView tv_qty = (TextView) view
                .findViewById(R.id.textView_order_summary_qty);
        String qty = tv_qty.getText().toString();
        final String name = tv_name.getText().toString();
        new AlertDialog.Builder(this)
                // Alert dialog for confirmation
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Confirm")
                .setMessage(
                        "Are you sure you want to remove " + qty + " (" + name
                                + "(s)) from your orders?")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                delete(name);
                                populate();
                                Toast.makeText(getApplicationContext(),
                                        name + " is deleted from your orders.",
                                        Toast.LENGTH_LONG).show();
                            }

                        }).setNegativeButton("No", null).show();

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int position,
                            long id) {
        // TODO Auto-generated method stub

        TextView tv_name = (TextView) view
                .findViewById(R.id.textView_order_summary_productname);
        String name = tv_name.getText().toString();
        Toast.makeText(getApplicationContext(), "You clicked: " + name,
                Toast.LENGTH_LONG).show();
    }

    public void delete(String name) {
        orderManager.deleteSpecific(name);
        populate();
        /*SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.delete(OrderDao.TABLE_ORDERS, DBHelper.PRODUCT_NAME + "='" + name
                + "'", null);
        populate();
        db.close();*/

    }

    @Override
    protected void onDestroy() {

        // TODO Auto-generated method stub
        super.onDestroy();

        dbhelper.close();
    }

    @Override
    public void onClick(View v) {

        // TODO Auto-generated method stub
        switch (v.getId()) {

            case R.id.button_clear_summary:
                YoYo.with(Techniques.Pulse).duration(500).playOn(btn_confirm);
                SQLiteDatabase db = dbhelper.getReadableDatabase();

                Cursor c = db.rawQuery("select * from '" + OrderDao.TABLE_ORDERS
                        + "'", null);
                if (c.moveToFirst()) {
                    c.close();
                    db.close();
                    new AlertDialog.Builder(this)
                            // Alert dialog for confirmation
                            .setIcon(android.R.drawable.ic_input_add)
                            .setTitle("Confirm")
                            .setMessage("You ordered a total of " + tv_grantotal.getText().toString() + " pesos." +
                                    "Are you sure you want to place this orders?")
                            .setPositiveButton("Yes",

                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    try {
                                                        newOrder(userid);
                                                        getOrder();
                                                        deleteAll();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            });


                                        }

                                    }).setNegativeButton("No", null).show();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "You have no orders yet.", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

    public void deleteAll() {
        orderManager.deleteAll();
        populate();
        finish();
    }

    public void getOrder() throws Exception {

        List<Product> products = orderManager.getAllOrders();
        Integer count = products.toArray().length;

        for (int i = 0; i < count; i++) {
            products.get(i);
            Product product = new Product();
            product.id = products.get(i).id;

            product.product_qty = products.get(i).product_qty;

            placeOrder(orderId, product);
        }

    }

    @SuppressWarnings("deprecation")
    public void newOrder(String userid) throws Exception {
        Order order = orderManager.newOrder(userid);
        orderId = order.id;

    }

    @SuppressWarnings("deprecation")
    public void placeOrder(String orderid, Product product) throws Exception {
        orderManager.placeOrder(orderid, product);

    }
}