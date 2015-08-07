package com.hernaez.seven_eleven.viewcontroller.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.domain.User;
import com.hernaez.seven_eleven.other.helper.AndroidUtils;
import com.hernaez.seven_eleven.viewcontroller.fragment.AdminPageFragmentHolder;
import com.hernaez.seven_eleven.viewcontroller.fragment.CarouselFragment;
import com.hernaez.seven_eleven.viewcontroller.fragment.CustomerOrderFragment;
import com.hernaez.seven_eleven.viewcontroller.fragment.OrderSummaryFragment;

import javax.inject.Inject;

/**
 * Created by TAS on 7/21/2015.
 */

public class MainActivity extends BaseActivity {
    public static final String EXTRA_USERID = "userId";
    public static Integer userid;
    @Inject
    AndroidUtils androidUtils;
    Fragment customerOrderFragment, orderSummaryFragment;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        if (savedInstanceState != null) {
            Log.e("Bundle", "Bundle is not null. " + savedInstanceState.getInt("userId"));

        }

        customerOrderFragment = new CustomerOrderFragment();
        orderSummaryFragment = new OrderSummaryFragment();

        if (extras != null) {
            userid = extras.getInt(EXTRA_USERID);
            Log.e("userid", userid + "");
        }

        thread = new Thread() {
            @Override
            public void run() {
                if (userid == 2) {
                    androidUtils.loadFragment(MainActivity.this, R.id.container, CarouselFragment.newInstance());
                } else if (userid == 1) {
                    androidUtils.loadFragment(MainActivity.this, R.id.container, AdminPageFragmentHolder.newInstance());

                }
            }
        };

        thread.start();


    }

    public static void start(Activity me, User user) {
        Intent intent = new Intent(me, MainActivity.class);
        intent.putExtra(EXTRA_USERID, user.userId);
        me.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("Exit").setMessage("Would you like to exit?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton("No", null).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("userId", userid);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        userid = savedInstanceState.getInt("userId");
        super.onRestoreInstanceState(savedInstanceState);
    }
}
