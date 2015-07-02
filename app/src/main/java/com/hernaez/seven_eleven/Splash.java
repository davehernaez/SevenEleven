package com.hernaez.seven_eleven;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class Splash extends Activity {
    ImageView imgv1, imgv2;
    Animation anim1, fade_out;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        imgv1 = (ImageView) findViewById(R.id.imageview_splash_image);
        imgv2 = (ImageView) findViewById(R.id.imageView_splash_bottom_banner);

        YoYo.with(Techniques.ZoomIn).delay(1000).playOn(imgv1);
        YoYo.with(Techniques.BounceInUp).delay(1000).playOn(imgv2);

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
