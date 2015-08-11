package com.hernaez.seven_eleven.viewcontroller.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.domain.User;
import com.hernaez.seven_eleven.other.helper.AndroidUtils;
import com.hernaez.seven_eleven.viewcontroller.adapter.AdminPageAdapter;
import com.hernaez.seven_eleven.viewcontroller.adapter.CustomerPageAdapter;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by TAS on 7/21/2015.
 */

public class MainActivity extends BaseActivity {
    public static final String EXTRA_USERID = "userId";
    public static Integer userid;
    @Inject
    AndroidUtils androidUtils;
    Thread thread;

    @InjectView(R.id.tpi_header2)
    protected com.rey.material.widget.TabPageIndicator indicator;

    @InjectView(R.id.vp_pages2)
    protected ViewPager pager;
    int currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        if (savedInstanceState != null) {
            Log.e("Bundle", "Bundle is not null. " + savedInstanceState.getInt("userId"));
            currentItem = savedInstanceState.getInt("currentItem");
        } else {
            currentItem = 0;
        }

        if (extras != null) {
            userid = extras.getInt(EXTRA_USERID);
            Log.e("userid", userid + "");
        }
        Log.e("fragmentTAG",getFragmentTag(pager.getCurrentItem()));

        thread = new Thread() {
            @Override
            public void run() {
                if (userid == 2) {

                    pager.setAdapter(new CustomerPageAdapter(getResources(), getSupportFragmentManager()));

                } else if (userid == 1) {
                    pager.setAdapter(new AdminPageAdapter(getResources(), getSupportFragmentManager()));
                }
                indicator.setViewPager(pager);
                pager.setCurrentItem(currentItem);

            }
        };

        thread.start();


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
        outState.putInt("currentItem", currentItem);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        userid = savedInstanceState.getInt("userId");
        currentItem = savedInstanceState.getInt("currentItem");
        super.onRestoreInstanceState(savedInstanceState);
    }


    public static void start(Activity me, User user) {
        Intent intent = new Intent(me, MainActivity.class);
        intent.putExtra(EXTRA_USERID, user.userId);
        me.startActivity(intent);
    }

    public String getFragmentTag(int position) {
        return "android:switcher:" + R.id.vp_pages2 + ":" + position;
    }

}
