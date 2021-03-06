package com.hernaez.seven_eleven.viewcontroller.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.domain.User;
import com.hernaez.seven_eleven.model.businesslayer.Login;
import com.hernaez.seven_eleven.other.helper.AndroidUtils;

import java.util.HashMap;


import javax.inject.Inject;

import butterknife.InjectView;


/**
 * Created by TAS on 7/7/2015.
 */
public class LoginActivity extends BaseActivity implements ViewPagerEx.OnPageChangeListener {
    @Inject
    Login login;
    @Inject
    AndroidUtils androidUtils;

    @InjectView(R.id.button_login)
    Button btnLogin;
    @InjectView(R.id.editText_password)
    EditText etPassword;
    @InjectView(R.id.editText_username)
    EditText etUsername;

    @InjectView(R.id.mySlider)
    SliderLayout mySlider;

    private static final String TAG_SUCCESS = "success";


    Thread loginThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        StrictMode.enableDefaults();

        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("slider_bg_1", R.drawable.bg_slider_a);
        file_maps.put("slider_bg2", R.drawable.bg_slider_b);
        file_maps.put("slider_bg3", R.drawable.bg_slider_c);

        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView.image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);

            mySlider.addSlider(textSliderView);
        }
        mySlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        mySlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mySlider.setDuration(2999);
        mySlider.addOnPageChangeListener(this);


        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();

                YoYo.with(Techniques.Pulse).duration(400).playOn(btnLogin);
                if (TextUtils.isEmpty(username)) {
                    YoYo.with(Techniques.Wave).duration(500).playOn(etUsername);
                    etUsername.setError("Username cannot be empty!");
                }
                if (TextUtils.isEmpty(password)) {
                    YoYo.with(Techniques.Wave).duration(500).playOn(etPassword);
                    etPassword.setError("Password cannot be empty!");
                }
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    return;
                }
                loginThread = new Thread() {
                    public void run() {
                        login(username, password);
                    }
                };
                loginThread.start();
                btnLogin.setEnabled(false);
                etUsername.setEnabled(false);
                etPassword.setEnabled(false);

            }

        });
    }

    public void login(final String username, final String password) {
        Log.e("Login", "Logging in...");
        try {
            final User user = login.userLogin(username, password);

            if (user.userType.equals(User.USERTYPE_ADMIN)) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        androidUtils.alert("Logging in as admin...");
                        MainActivity.start(LoginActivity.this, user);
                        finish();
                    }
                });
            } else if (user.userType.equals(User.USERTYPE_USER)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        androidUtils.alert("Logging in...");
                        MainActivity.start(LoginActivity.this, user);
                        finish();
                    }
                });
            }
        } catch (Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    androidUtils.alert("Username and password did not match.");
                    btnLogin.setEnabled(true);
                    etUsername.setEnabled(true);
                    etPassword.setEnabled(true);
                }
            });
        }

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        androidUtils.alert("Press \"Back\" again to exit");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        btnLogin.setEnabled(true);
        etUsername.setEnabled(true);
        etPassword.setEnabled(true);
        etUsername.requestFocus();
        super.onResume();
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mySlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("password", etPassword
                .getText().toString());
    }

}
