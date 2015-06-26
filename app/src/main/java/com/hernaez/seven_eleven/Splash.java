package com.hernaez.seven_eleven;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class Splash extends Activity {
	ImageView imgv1, imgv2;
	Animation anim1, fade_out;
	View v;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		imgv1 = (ImageView) findViewById(R.id.imageViewProduct_image);
		imgv2 = (ImageView) findViewById(R.id.imageView2);
		
		anim1 = AnimationUtils.loadAnimation(this, R.anim.scale);
		fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
		imgv2.startAnimation(fade_out);
		imgv1.startAnimation(anim1);

		Thread t = new Thread() {
			public void run() {
				try {
					sleep(2000);

					Intent i = new Intent(
							"com.hernaez.seven_eleven.LOGIN");
					startActivity(i);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				finally {
					finish();
				}

			}
		};
		t.start();

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

}
