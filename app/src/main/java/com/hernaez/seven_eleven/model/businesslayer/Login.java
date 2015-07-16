package com.hernaez.seven_eleven.model.businesslayer;

import android.util.Log;

import com.hernaez.seven_eleven.domain.User;
import com.hernaez.seven_eleven.model.dataaccesslayer.LoginHttpAdapter;

/**
 * Created by jaenelleisidro on 7/8/15.
 */
public class Login {
    LoginHttpAdapter loginHttpAdapter;

    public Login(LoginHttpAdapter loginHttpAdapter){
        //loginHttpAdapter = new LoginHttpAdapter();
        this.loginHttpAdapter=loginHttpAdapter;
    }

    public User userLogin(String userName, String password) throws Exception {

        User user=loginHttpAdapter.getUser(userName,password);

        return user;

    }
}
