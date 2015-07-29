package com.hernaez.seven_eleven.model.dataaccesslayer.retrofit;


import android.util.Log;

import com.hernaez.seven_eleven.domain.User;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Query;

public interface UserHttpService {
    public static final String HTTP_DOMAIN = "http://seveneleven.esy.es";
    public static final String HTTP_LOGIN = "/android_connect/login.php";

    public static final String PARAM_LOGINUSERS_USERNAME = "username";
    public static final String PARAM_LOGINUSERS_PASSWORD = "password";

    //@FormUrlEncoded
    @POST(HTTP_LOGIN)
    User searchUser(
            @Query(PARAM_LOGINUSERS_USERNAME) String username, @Query(PARAM_LOGINUSERS_PASSWORD) String password);

}
