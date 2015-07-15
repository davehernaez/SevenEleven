package com.hernaez.seven_eleven.viewcontroller.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.model.businesslayer.ProductList;
import com.hernaez.seven_eleven.model.businesslayer.ReOrder;
import com.hernaez.seven_eleven.viewcontroller.adapter.CustomViewAdapter;

/**
 * Created by TAS on 7/7/2015.
 */
public class ReOrderActivity extends Activity implements AdapterView.OnItemClickListener {
    ListView lv;
    EditText dialog_qty;
    TextView tv_prodname;
    ProductList productList;
    Thread thread;
    String userid;
    ReOrder reOrder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reorder);

        productList = new ProductList();
        reOrder = new ReOrder();
        Bundle extras = getIntent().getExtras();

        userid = extras.getString("user_id");
        Log.e("ReOrderActivity", userid);

        lv = (ListView) findViewById(R.id.listView_reorder);
        thread = new Thread() {
            public void run() {
                populate();
            }
        };
        thread.start();


        lv.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int id,
                            long position) {
        // TODO Auto-generated method stub

        tv_prodname = (TextView) view.findViewById(R.id.textViewProduct_name);

        dialog();

    }

    public void populate() {
        try {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    CustomViewAdapter myadapter = null;
                    try {
                        myadapter = new CustomViewAdapter(getApplicationContext(),
                                R.layout.list_item, productList.getReorderProducts());
                        lv.setAdapter(myadapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialog = inflater.inflate(R.layout.dialog_reorder, null);
        builder.setView(dialog);

        final AlertDialog ad = builder.create();
        ad.show();
        ad.setCancelable(false);

        Button btn_minus = (Button) dialog
                .findViewById(R.id.buttondialog_minus);
        Button btn_plus = (Button) dialog.findViewById(R.id.buttondialog_plus);
        Button btn_ok = (Button) dialog.findViewById(R.id.buttondialog_ok);
        Button btn_cancel = (Button) dialog.findViewById(R.id.buttondialog_cancel);

        TextView tv_title = (TextView) dialog.findViewById(R.id.textView_dialog_title);
        tv_title.setText("How many " + tv_prodname.getText().toString() + "(s) would you like to order from supplier?");

        dialog_qty = (EditText) dialog
                .findViewById(R.id.editText_dialog_qty);

        btn_minus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (Integer.parseInt(dialog_qty.getText().toString()) > 1) {
                    Integer qty = Integer.parseInt(dialog_qty.getText()
                            .toString());
                    qty--;
                    dialog_qty.setText(qty.toString());
                }

            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (Integer.parseInt(dialog_qty.getText().toString()) < 1000) {
                    Integer qty = Integer.parseInt(dialog_qty.getText()
                            .toString());
                    qty++;
                    dialog_qty.setText(qty.toString());
                }

            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                reorder(tv_prodname.getText().toString(), dialog_qty.getText().toString());
               populate();
                ad.dismiss();
                Toast.makeText(getApplicationContext(), "Order completed. Your product's quantity has been updated.", Toast.LENGTH_LONG).show();

            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
            }
        });


    }

    public void reorder(String product_name, String product_qty) {

        try {
            reOrder.reOrder(product_name, product_qty);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void start(Activity activity, String userid) {
        Intent i = new Intent(activity, ReOrderActivity.class);
        i.putExtra("user_id", userid);
        activity.startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
