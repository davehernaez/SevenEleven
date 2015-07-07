package com.hernaez.seven_eleven;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Splash extends Activity {
    ImageView imgv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        imgv1 = (ImageView) findViewById(R.id.imageview_splash_image);


        YoYo.with(Techniques.ZoomIn).delay(1000).playOn(imgv1);


        t.start();

    }

    Thread t = new Thread() {
        public void run() {

            try {
                sleep(2500);

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
