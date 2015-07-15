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
import com.hernaez.seven_eleven.domain.Product;
import com.hernaez.seven_eleven.model.businesslayer.OrderManager;
import com.hernaez.seven_eleven.model.dataaccesslayer.DBHelper;
import com.hernaez.seven_eleven.model.dataaccesslayer.OrderDao;
import com.hernaez.seven_eleven.viewcontroller.adapter.CustomViewAdapter2;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
                                                    order(userid);
                                                    try {
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
    public void order(String userid) {
        String phpOutput = "";
        InputStream inputstream = null;

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppostURL = new HttpPost(
                "http://seveneleven.esy.es/android_connect/order.php");

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("user_id", userid));
            httppostURL.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpclient.execute(httppostURL);
            HttpEntity entity = response.getEntity();
            inputstream = entity.getContent();

        } catch (Exception exception) {
            Log.e("log_tag", "Error in http connection " + exception.toString());
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputstream, "iso-8859-1"), 8);
            StringBuilder stringBuilder = new StringBuilder();
            String singleLine = null;

            while ((singleLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(singleLine + "\n");
            }

            inputstream.close();
            phpOutput = stringBuilder.toString();

        } catch (Exception exception) {
            Log.e("log_tag", "Error converting result" + exception.toString());
        }
        try {
            phpOutput = phpOutput.replaceAll("\\s+", "");
            Log.e("phpOutput: ", phpOutput + "");

            JSONArray jasonArray = new JSONArray(phpOutput);
            JSONObject jsonObject = jasonArray.getJSONObject(0);

            try {
                // get method status if successful

                orderId = jsonObject.getString("order_id");
                Log.e("Order id: ", orderId);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @SuppressWarnings("deprecation")
    public void placeOrder(String orderid, Product product) throws Exception {
        orderManager.placeOrder(orderid, product);

    }
}