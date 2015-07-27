package com.hernaez.seven_eleven.viewcontroller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.domain.User;

import butterknife.InjectView;


/**
 * Created by TAS on 7/7/2015.
 */
public class AdminPageActivity extends BaseActivity implements View.OnClickListener {
    Integer userid;

    @InjectView(R.id.button_for_reorder)
    Button btn_for_reordering;

    @InjectView(R.id.button_productList)
    Button btn_all_products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_page);


        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            userid = extras.getInt("user_id");
            Log.e("userid", userid + "");
        }

        btn_all_products.setOnClickListener(this);
        btn_for_reordering.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        switch (v.getId()) {
            case R.id.button_productList:
                YoYo.with(Techniques.Pulse).duration(1000).playOn(btn_all_products);
                ProductListActivity.start(AdminPageActivity.this);

                break;

            case R.id.button_for_reorder:
                YoYo.with(Techniques.Pulse).duration(1000).playOn(btn_for_reordering);
                ReOrderActivity.start(AdminPageActivity.this, userid);
                break;

        }

    }

    public static void start(Activity me, User user) {
        Intent intent = new Intent(me, AdminPageActivity.class);
        intent.putExtra("user_id", user.userId);
        me.startActivity(intent);

    }

}
