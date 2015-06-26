package com.hernaez.seven_eleven;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
import org.json.JSONStringer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderSummary extends Activity implements OnItemLongClickListener,
		OnItemClickListener, OnClickListener {
	DBHelper dbhelper;
	ListView lv;
	String[] fromDB;
	Cursor c;
	int[] toView;
	Bitmap bmp;
	TextView tv_total, tv_grantotal;
	Double total;
	String prodsubtotal, userid, orderid;
	Intent i;
	Button btn_confirm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_summary);

		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			userid = extras.getString("user_id");
			Log.e("userid", userid);
		}

		tv_total = (TextView) findViewById(R.id.textView_summary_grandtotal);
		tv_grantotal = (TextView) findViewById(R.id.textView_summary_grandtotal);

		lv = (ListView) findViewById(R.id.listView_order_summary);

		dbhelper = new DBHelper(this);

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
		};
	};

	public void populate() {
		total = 0.0;
		List<RowItem> rowItems;
		rowItems = new ArrayList<RowItem>();
		List<Double> totals = new ArrayList<Double>();
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		String where = null;
		Cursor c = db.query(true, DBHelper.TABLE_ORDERS, DBHelper.ALL_FIELDS,
				where, null, null, null, null, null);
		if (c != null) {
			for (int i = 0, count = c.getCount(); i < count; i++) {
				c.moveToNext();
				String prodname = c.getString(c
						.getColumnIndex(DBHelper.PRODUCT_NAME));
				String prodprice = c.getString(c
						.getColumnIndex(DBHelper.PRODUCT_PRICE));
				String prodqty = c.getString(c
						.getColumnIndex(DBHelper.PRODUCT_QTY));
				prodsubtotal = c.getString(c
						.getColumnIndex(DBHelper.PRODUCT_SUBTOTAL));
				String prodimgpath = c.getString(c
						.getColumnIndex(DBHelper.PRODUCT_IMGPATH));
				Log.e("cursor 1", prodname);
				RowItem item = new RowItem(prodname, prodprice, prodqty,
						prodsubtotal, prodimgpath);
				totals.add(Double.parseDouble(prodsubtotal));
				rowItems.add(item);
				Double subt = Double.parseDouble(prodsubtotal);
				total += subt; // totals.get(i);
				Log.e("final grand total", total + "");

				tv_total.setText(String.format("%.2f", total));

				CustomViewAdapter2 myadapter = new CustomViewAdapter2(this,
						R.layout.order_summary_holder, rowItems);
				lv.setAdapter(myadapter);

			}

			c.close();
		}

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
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		db.delete(DBHelper.TABLE_ORDERS, DBHelper.PRODUCT_NAME + "='" + name
				+ "'", null);
		populate();
		db.close();

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
			SQLiteDatabase db = dbhelper.getReadableDatabase();

			Cursor c = db.rawQuery("select * from '" + DBHelper.TABLE_ORDERS
					+ "'", null);
			if (c.moveToFirst()) {
				c.close();
				db.close();
				new AlertDialog.Builder(this)
						// Alert dialog for confirmation
						.setIcon(android.R.drawable.ic_input_add)
						.setTitle("Confirm")
						.setMessage("You ordered a total of " + tv_grantotal.getText().toString() +" pesos."+
								"Are you sure you want to place this orders?")
						.setPositiveButton("Yes",

						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								order(userid);
								getOrder();
								deleteAll();

							}

						}).setNegativeButton("No", null).show();

			}

			else {
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
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		db.delete(DBHelper.TABLE_ORDERS, null, null);
		Toast.makeText(getApplicationContext(), "Orders placed. Thank you.",
				Toast.LENGTH_LONG).show();
		populate();
		finish();
	}

	public void getOrder() {

		SQLiteDatabase db = dbhelper.getReadableDatabase();

		Cursor c = db.rawQuery("select * from '" + DBHelper.TABLE_ORDERS + "'",
				null);

		if (c.moveToFirst()) {
			while (!c.isAfterLast()) {
				//String prodname = c.getString(c.getColumnIndex("product_name"));
				String prodid = c.getString(c.getColumnIndex("_id"));
				String prodqty = c.getString(c.getColumnIndex("product_qty"));
				placeOrder(orderid, prodid, prodqty);
				c.moveToNext();
			}
		}
		if (c.isAfterLast()) {
			c.close();
			db.close();
		}

	}

	@SuppressWarnings("deprecation")
	public void order(String userid) {
		String phpOutput = "";
		InputStream inputstream = null;

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppostURL = new HttpPost(
				"http://192.168.254.16/android_connect/order.php");

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

				orderid = jsonObject.getString("order_id");
				Log.e("Order id: ", orderid);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("deprecation")
	public void placeOrder(String orderid, String prodid, String qty) {
		Log.e("order id in placeorder", orderid);
		String phpOutput = "";
		InputStream inputstream = null;

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppostURL = new HttpPost(
				"http://192.168.254.16/android_connect/place_order.php");

		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
			nameValuePairs.add(new BasicNameValuePair("order_id", orderid));
			nameValuePairs.add(new BasicNameValuePair("product_id", prodid));
			nameValuePairs.add(new BasicNameValuePair("order_qty", qty));
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