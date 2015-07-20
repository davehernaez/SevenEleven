package com.hernaez.seven_eleven.viewcontroller.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import com.hernaez.seven_eleven.model.businesslayer.NewOrder;
import com.hernaez.seven_eleven.model.businesslayer.OrderManager;
import com.hernaez.seven_eleven.model.businesslayer.PlaceOrder;
import com.hernaez.seven_eleven.model.dataaccesslayer.DBHelper;
import com.hernaez.seven_eleven.model.dataaccesslayer.OrderDao;
import com.hernaez.seven_eleven.viewcontroller.adapter.CustomViewAdapter2;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by TAS on 7/7/2015.
 */
public class OrderSummaryActivity extends BaseActivity implements AdapterView.OnItemLongClickListener,
        AdapterView.OnItemClickListener, View.OnClickListener {

    ListView lv;
    String[] fromDB;
    Cursor c;
    int[] toView;
    Bitmap bmp;
    TextView tv_total, tv_grantotal;
    Double total;
    String prodsubtotal;
    Integer orderId, userid;
    Intent i;
    Button btn_confirm;
    DBHelper dbhelper;
    OrderDao orderDao;
    OrderManager orderManager;

    @Inject
    NewOrder newOrder;
    @Inject
    PlaceOrder placeOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_summary);

        dbhelper = new DBHelper(this);
        orderDao = new OrderDao(dbhelper);
        orderManager = new OrderManager(orderDao);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            userid = extras.getInt("user_id");
        }

        tv_total = (TextView) findViewById(R.id.textView_summary_grandtotal);
        tv_grantotal = (TextView) findViewById(R.id.textView_summary_grandtotal);

        lv = (ListView) findViewById(R.id.listView_order_summary);

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
        final List<Product> products = orderManager.getAllOrders();

        CustomViewAdapter2 myadapter = new CustomViewAdapter2(getApplicationContext(), R.layout.order_summary_holder, products);
        lv.setAdapter(myadapter);
        Integer count = products.toArray().length;
        Double grandTotal = 0.00;
        Product product = new Product();
        for (int i = 0; i < count; i++) {
            products.get(i);

            product.id = products.get(i).id;
            grandTotal += products.get(i).subtotal;

            product.product_qty = products.get(i).product_qty;

        }
        tv_grantotal.setText(grandTotal.toString());


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
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        delete(name);
                                        populate();
                                        Toast.makeText(getApplicationContext(),
                                                name + " is deleted from your orders.",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });

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
                try {
                    if (orderManager.getAllOrders().toArray().length > 0) {
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
                                                            finishOrder();
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
                } catch (Exception e) {
                    e.printStackTrace();
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

    public void finishOrder() throws Exception {

        List<Product> products = orderManager.getAllOrders();
        Integer count = products.toArray().length;

        for (int i = 0; i < count; i++) {

            products.get(i);
            Product product = new Product();
            product.id = products.get(i).id;
            product.product_qty = products.get(i).product_qty;

            placeOrder(orderId, product);
            Log.e("Placing Orders", "" + product.id);

        }


    }

    @SuppressWarnings("deprecation")
    public void newOrder(Integer userid) throws Exception {
        Order order = newOrder.newOrder(userid);
        orderId = order.id;
    }

    @SuppressWarnings("deprecation")
    public void placeOrder(Integer orderid, Product product) throws Exception {
        placeOrder.placeOrder(orderid, product);

    }
}