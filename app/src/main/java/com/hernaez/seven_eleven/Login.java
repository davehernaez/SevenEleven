package com.hernaez.seven_eleven;

import java.io.BufferedReader;
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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {

	EditText edtx_username, edtx_password;
	Button btn_login;
	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	String username, password;

	private static final String login = "http://localhost/android_connect/login.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		StrictMode.enableDefaults();

		edtx_username = (EditText) findViewById(R.id.editText_username);
		edtx_password = (EditText) findViewById(R.id.editText_password);

		btn_login = (Button) findViewById(R.id.button_login);

		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				username = edtx_username.getText().toString();
				password = edtx_password.getText().toString();
				// TODO Auto-generated method stub	
				loginThread=new Thread(){
					public void run() {
						login();
					};
				};
				loginThread.start();
			}

		});

	}
	
	Thread loginThread;

	public void login() {

		
		if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {

			userLogin(username, password);// Login method

		} else {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(Login.this, "Please fill up empty field(s)",
							Toast.LENGTH_LONG).show();
				}
			});
			
		}
	}

	public void userLogin(String user, String pass) {

		String phpOutput = "";
		InputStream inputstream = null;

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppostURL = new HttpPost(
				"http://192.168.254.16///android_connect/login.php");

		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(0);
			nameValuePairs.add(new BasicNameValuePair("username", user));
			nameValuePairs.add(new BasicNameValuePair("password", pass));
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
				String output = "hi";
				String admin = "admin";
				String user_user = "user";
				Log.e("output value before", output);
				output = jsonObject.getString("user_name");
				Log.e("output value after", output);

				// Login as admin
				if (output.equals(username)
						&& (admin.equals(jsonObject.get("user_type")))) {

					
					final String userid = jsonObject.getString("user_id");
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(getApplicationContext(),
									"Logging in as admin...", Toast.LENGTH_SHORT)
									.show();
							Intent intent = new Intent(Login.this, AdminPage.class);
							intent.putExtra("user_id", userid);
							startActivity(intent);
						}
					});
					
				}
				// Login as normal user
				else if (output.equals(username)
						&& (user_user.equals(jsonObject.get("user_type")))) {
					Toast.makeText(getApplicationContext(), "Logging in...",
							Toast.LENGTH_SHORT).show();
					final String userid = jsonObject.getString("user_id");
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Intent intent = new Intent(Login.this, UserPage.class);
							intent.putExtra("user_id", userid);
							startActivity(intent);
						}
					});
				}
			} catch (Exception ex) {
			}
		} catch (final Exception exception) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(Login.this, "Username and password did not match.",
							Toast.LENGTH_LONG).show();
					Log.e("log_tag", "Error Parsing Data" + exception.toString());
				}
			});
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		edtx_username.setText("");
		edtx_password.setText("");

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle("Exit")
				.setMessage("Are you sure you want exit?")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}

						}).setNegativeButton("No", null).show();
	}

}
