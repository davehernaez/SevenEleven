package com.hernaez.seven_eleven.model.businesslayer;

import android.util.Log;

import com.hernaez.seven_eleven.domain.User;
import com.hernaez.seven_eleven.model.dataaccesslayer.retrofit.HttpService;
import com.hernaez.seven_eleven.other.helper.AndroidUtils;

/**
 * Created by jaenelleisidro on 7/8/15.
 */
public class Login {
    //LoginHttpAdapter loginHttpAdapter;
    HttpService httpService;
    AndroidUtils androidUtils;

    public Login(/*LoginHttpAdapter loginHttpAdapter*/HttpService httpService, AndroidUtils androidUtils) {
        this.httpService = httpService;
        this.androidUtils = androidUtils;
    }

    public User userLogin(String username, String password) throws Exception {
        Log.e("UserLogin", "UserLogin...");
        User user = httpService.userLogin(username, password);

        return user;

    }
}
