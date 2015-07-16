package com.hernaez.seven_eleven.viewcontroller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.model.businesslayer.ProductList;
import com.hernaez.seven_eleven.other.dagger.Injector;
import com.hernaez.seven_eleven.viewcontroller.adapter.CustomViewAdapter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by TAS on 7/7/2015.
 */
public class ProductListActivity extends Activity {
    ListView lv;
    String userid;
    @Inject
    ProductList productList;
    List list;

    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prdouct_list);
        Injector.inject(this);


        /*productList = new ProductList();*/

        lv = (ListView) findViewById(R.id.listView_productList);
        thread = new Thread() {
            public void run() {

                populate();

            }
        };

        thread.start();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                TextView name = (TextView) view
                        .findViewById(R.id.textViewProduct_name);

                Toast.makeText(getApplicationContext(),
                        "You clicked: " + name.getText().toString(),
                        Toast.LENGTH_SHORT).show();
                // TODO Auto-generated method stub

            }
        });
    }

    public void populate() {
        try {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    try {

                        CustomViewAdapter myadapter = null;

                        myadapter = new CustomViewAdapter(getApplicationContext(),
                                R.layout.list_item, productList.getAllProducts());
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

    public static void start(Activity me) {
        Intent intent = new Intent(me, ProductListActivity.class);
        me.startActivity(intent);
    }
}
