package com.hernaez.seven_eleven.viewcontroller.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.domain.User;
import com.hernaez.seven_eleven.model.businesslayer.Login;

/**
 * Created by TAS on 7/7/2015.
 */
public class LoginActivity extends Activity {
    EditText etPassword;

    private static final String TAG_SUCCESS = "success";

    Login login=new Login();
    Thread loginThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        StrictMode.enableDefaults();


        final EditText etUsername = (EditText) findViewById(R.id.editText_username);
        etPassword = (EditText) findViewById(R.id.editText_password);

        final Button btnLogin = (Button) findViewById(R.id.button_login);


        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                YoYo.with(Techniques.Pulse).duration(400).playOn(btnLogin);
                if(TextUtils.isEmpty(username)){
                    YoYo.with(Techniques.Wave).duration(500).playOn(etUsername);
                    etUsername.setError("Username cannot be empty!");
                }
                if(TextUtils.isEmpty(password)){
                    YoYo.with(Techniques.Wave).duration(500).playOn(etPassword);
                    etPassword.setError("Password cannot be empty!");
                }
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    return;
                }
                loginThread = new Thread() {
                    public void run() {
                        login(username,password);
                    }
                };
                loginThread.start();

            }

        });
    }

    public void login(final String username,final String password) {
        try {
            final User user = login.userLogin(username, password);
            if(user.userType.equals(User.USERTYPE_ADMIN)){

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alert("Logging in as admin...");
                        AdminPageActivity.start(LoginActivity.this,user);
                    }
                });
            }else if(user.userType.equals(User.USERTYPE_USER)){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alert("Logging in...");
                        CustomerOrderActivity.start(LoginActivity.this,user);
                    }
                });
            }
        }catch(Exception e){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    alert("Username and password did not match.");
                }
            });
        }

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

    private void alert(String message){
        Toast.makeText(this, message,
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        etPassword.setText("");
        super.onResume();
    }
}
