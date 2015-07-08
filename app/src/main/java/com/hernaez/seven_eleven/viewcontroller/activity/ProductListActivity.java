package com.hernaez.seven_eleven.viewcontroller.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.domain.RowItem;
import com.hernaez.seven_eleven.viewcontroller.adapter.CustomViewAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
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
public class ProductListActivity extends Activity {
    ListView lv;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prdouct_list);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            userid = extras.getString("user_id");
            Log.e("userid", userid);
        }

        lv = (ListView) findViewById(R.id.listView_productList);
        getAll();
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

    public void getAll() {

        String phpOutput = "";
        InputStream inputstream = null;
        List<RowItem> rowItems;
        rowItems = new ArrayList<RowItem>();
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppostURL = new HttpPost(
                "http://seveneleven.esy.es/android_connect/get_all_products.php");

        try {

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

                phpOutput = stringBuilder.toString();

                JSONArray new_array = new JSONArray(phpOutput);

                for (int i = 0, count = new_array.length(); i < count; i++) { // Loop

                    Log.e("value of i", i + "");

                    JSONObject jsonObject = new_array.getJSONObject(i);
                    String prodname = jsonObject.getString("product_name");// extract
                    // product
                    // name
                    String prodqty = jsonObject.getString("product_qty");// extract
                    // product
                    // qty
                    String prodprice = jsonObject.getString("product_price");// extract
                    // price
                    String prodimg = jsonObject.getString("image_path");// extract
                    // product
                    // image
                    // url

                    RowItem item = new RowItem(prodname, prodprice, prodqty,
                            null, prodimg);

                    rowItems.add(item);// add all strings to row item

                    CustomViewAdapter myadapter = new CustomViewAdapter(this,
                            R.layout.list_item, rowItems);
                    lv.setAdapter(myadapter);
                }

                Log.e("phpOutput", "Success!!!!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            Log.e("log_tag", "Error converting result" + exception.toString());
        }
    }
}
