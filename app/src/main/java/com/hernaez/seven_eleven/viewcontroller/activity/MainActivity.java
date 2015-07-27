package com.hernaez.seven_eleven.viewcontroller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.hernaez.seven_eleven.R;
import com.hernaez.seven_eleven.domain.User;
import com.hernaez.seven_eleven.other.helper.AndroidUtils;
import com.hernaez.seven_eleven.viewcontroller.fragment.CarouselFragment;

import javax.inject.Inject;

/**
 * Created by TAS on 7/21/2015.
 */

public class MainActivity extends BaseActivity {
    public static final String EXTRA_USERID = "userId";
    Integer userid;
    @Inject
    AndroidUtils androidUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userid = extras.getInt(EXTRA_USERID);
            Log.e("userid", userid + "");
        }
        androidUtils.loadFragment(this, R.id.container, CarouselFragment.newInstance());
    }

    public static void start(Activity me, User user) {
        Intent intent = new Intent(me, MainActivity.class);
        intent.putExtra(EXTRA_USERID, user.userId);
        me.startActivity(intent);
    }

}
