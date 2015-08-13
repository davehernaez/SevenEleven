package com.hernaez.seven_eleven.viewcontroller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.hernaez.seven_eleven.R;

/**
 * Created by TAS on 7/7/2015.
 */
public class SplashActivity extends Activity {
    ImageView imgv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        imgv1 = (ImageView) findViewById(R.id.imageview_splash_image);
        YoYo.with(Techniques.FadeOut).delay(900).duration(1700).playOn(imgv1);

        t.start();

    }

    Thread t = new Thread() {
        public void run() {

            try {
                sleep(2400);

                Intent i = new Intent(
                        "com.hernaez.seven_eleven.LOGIN");
                startActivity(i);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                finish();
            }

        }
    };

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}
