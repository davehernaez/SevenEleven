package com.hernaez.seven_eleven.model.businesslayer;

import android.util.Log;

import com.hernaez.seven_eleven.domain.User;
import com.hernaez.seven_eleven.model.dataaccesslayer.LoginHttpAdapter;
import com.hernaez.seven_eleven.model.dataaccesslayer.retrofit.UserHttpService;
import com.hernaez.seven_eleven.other.helper.AndroidUtils;

/**
 * Created by jaenelleisidro on 7/8/15.
 */
public class Login {
    //LoginHttpAdapter loginHttpAdapter;
    UserHttpService userHttpService;
    AndroidUtils androidUtils;

    public Login(/*LoginHttpAdapter loginHttpAdapter*/UserHttpService userHttpService, AndroidUtils androidUtils){
        //this.loginHttpAdapter=loginHttpAdapter;
        this.userHttpService= userHttpService;
        this.androidUtils = androidUtils;
    }

    public User userLogin(String username, String password) throws Exception {
        Log.e("UserLogin","UserLogin...");
        //User user=loginHttpAdapter.getUser(username,password);
        User user = userHttpService.searchUser(username, password);

        return user;

    }
}
