package com.hernaez.seven_eleven;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AdminPage extends Activity implements OnClickListener {
	Button btn_all_products, btn_for_reordering;
	Intent i;
	String userid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admin_page);

		Bundle extras = getIntent().getExtras();

		if (extras != null) {
			userid = extras.getString("user_id");
			Log.e("userid", userid);
		}

		btn_all_products = (Button) findViewById(R.id.button_productList);
		btn_for_reordering = (Button) findViewById(R.id.button_for_reorder);

		btn_all_products.setOnClickListener(this);
		btn_for_reordering.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button_productList:
			i = new Intent(AdminPage.this, ProductList.class);
			i.putExtra("user_id", userid);
			startActivity(i);
			break;
		
		case R.id.button_for_reorder:
			i = new Intent(AdminPage.this, ReOrder.class);
			i.putExtra("user_id", userid);
			startActivity(i);
			break;

		}

	}

}
