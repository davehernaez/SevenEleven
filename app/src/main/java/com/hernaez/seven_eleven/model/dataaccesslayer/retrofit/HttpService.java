package com.hernaez.seven_eleven.model.dataaccesslayer.retrofit;


import android.util.Log;

import com.hernaez.seven_eleven.domain.Product;
import com.hernaez.seven_eleven.domain.User;
import com.hernaez.seven_eleven.other.HttpConstant;

import java.util.List;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface HttpService {

    public static final String PARAM_LOGINUSERS_USERNAME = "username";
    public static final String PARAM_LOGINUSERS_PASSWORD = "password";

    @FormUrlEncoded
    @POST(HttpConstant.HTTP_LOGIN)
//    @GET(HttpConstant.HTTP_LOGIN)
    User userLogin(
//            @Query(PARAM_LOGINUSERS_USERNAME) String username, @Query(PARAM_LOGINUSERS_PASSWORD) String password);
            @Field(PARAM_LOGINUSERS_USERNAME) String username, @Field(PARAM_LOGINUSERS_PASSWORD) String password);


    @GET(HttpConstant.HTTP_GET_ALL_PRODUCTS)
    List<Product> getAllProducts();


    @GET(HttpConstant.HTTP_GET_ALL_REORDER)
    List<Product> getAllReorders();


}
