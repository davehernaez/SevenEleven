package com.hernaez.seven_eleven.viewcontroller.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.domain.User;
import com.hernaez.seven_eleven.model.businesslayer.OrderDaoManager;
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
    @Inject
    OrderDaoManager orderDaoManager;
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
        dialog().show();
        /*new AlertDialog.Builder(this).setTitle("Exit").setMessage("Would you like to exit? Unfinished orders will be deleted.").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                orderDaoManager.deleteAll(MainActivity.this);
                final Intent i = new Intent(
                        "com.hernaez.seven_eleven.LOGIN");
                startActivity(i);
                finish();
            }
        }).setNegativeButton("No", null).show();*/
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

    public AlertDialog.Builder dialog() {
        AlertDialog.Builder alertDialog;
        alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Exit");
        alertDialog.setNegativeButton("No", null).setCancelable(false);
        if (userid == 1) {
            alertDialog.setMessage("Are you sure you want to exit? Unsaved changes will be lost.");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final Intent i = new Intent(
                            "com.hernaez.seven_eleven.LOGIN");
                    startActivity(i);
                    finish();
                }
            });
        } else if (userid == 2) {
            alertDialog.setMessage("Are you sure you want to exit? Unfinished orders will be deleted.");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    orderDaoManager.deleteAll(MainActivity.this);
                    final Intent i = new Intent(
                            "com.hernaez.seven_eleven.LOGIN");
                    startActivity(i);
                    finish();
                }
            });
        }


        return alertDialog;
    }

}
