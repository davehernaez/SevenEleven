package com.hernaez.seven_eleven.viewcontroller.activity;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.hernaez.seven_eleven.domain.RowItem;
import com.hernaez.seven_eleven.viewcontroller.adapter.CustomViewAdapter;

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
public class ReOrderActivity extends Activity implements AdapterView.OnItemClickListener {
    ListView lv;
    EditText dialog_qty;
    TextView tv_prodname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reorder);

        lv = (ListView) findViewById(R.id.listView_reorder);
        //getAll();

        lv.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int id,
                            long position) {
        // TODO Auto-generated method stub

        tv_prodname = (TextView) view.findViewById(R.id.textViewProduct_name);

        dialog();

    }

    /*@SuppressWarnings("deprecation")
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

                    RowItem item = new RowItem(prodname, null, prodqty, null,
                            prodimg);
                    if (Integer.parseInt(prodqty) <= 10) {
                        rowItems.add(item);// add all strings to row item
                    }

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
    }*/

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
        tv_title.setText("How many "+ tv_prodname.getText().toString()+"(s) would you like to order from supplier?");

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
                reOrder(tv_prodname.getText().toString(),dialog_qty.getText().toString());
                ad.dismiss();
                Toast.makeText(getApplicationContext(), "Order completed. Your product's quantity has been updated.", Toast.LENGTH_LONG).show();
                //getAll();
            }
        });



        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ad.dismiss();
            }
        });




    }
    @SuppressWarnings("deprecation")
    public void reOrder(String name, String qty) {
        String phpOutput = "";
        InputStream inputstream = null;

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppostURL = new HttpPost(
                "http://seveneleven.esy.es/android_connect/reorder.php");

        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(0);
            nameValuePairs.add(new BasicNameValuePair("product_name", name));
            nameValuePairs.add(new BasicNameValuePair("product_qty", qty));
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

    }
}
