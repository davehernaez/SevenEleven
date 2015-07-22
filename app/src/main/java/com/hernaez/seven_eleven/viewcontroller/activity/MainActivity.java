package com.hernaez.seven_eleven.viewcontroller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.domain.User;
import com.hernaez.seven_eleven.other.helper.AndroidUtils;
import com.hernaez.seven_eleven.viewcontroller.fragment.CarouselFragment;
import com.hernaez.seven_eleven.viewcontroller.fragment.CustomerOrderFragment;

import javax.inject.Inject;

/**
 * Created by TAS on 7/21/2015.
 */

public class MainActivity extends BaseActivity {
    Integer userid;
    @Inject
    AndroidUtils androidUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userid = extras.getInt("user_id");
        }

        androidUtils.loadFragment(this, R.id.container, CarouselFragment.newInstance());

    }

    public static void start(Activity me, User user) {
        Intent intent = new Intent(me, MainActivity.class);
        intent.putExtra("user_id", user.userId);
        me.startActivity(intent);
    }
}
