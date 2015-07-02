package com.hernaez.seven_eleven;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class UserPage extends Activity implements OnClickListener {
    Button button_order;
    Intent i;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_page);
        button_order = (Button) findViewById(R.id.buttonOrder);
        button_order.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            userid = extras.getString("user_id");
            Log.e("userid", userid);
        }
    }

    @Override
    public void onClick(View v) {

        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.buttonOrder:
                i = new Intent(this, CustomerOrder.class);
                YoYo.with(Techniques.Pulse).duration(300).playOn(button_order);
                i.putExtra("user_id", userid);
                startActivity(i);

                break;
        }

    }
}