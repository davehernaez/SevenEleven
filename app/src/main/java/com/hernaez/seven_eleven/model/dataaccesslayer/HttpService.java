package com.hernaez.seven_eleven.model.dataaccesslayer;


import com.hernaez.seven_eleven.domain.ListProducts;
import com.hernaez.seven_eleven.domain.Product;

import java.util.List;

import javax.security.auth.callback.Callback;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by TAS on 7/22/2015.
 */
public interface HttpService {
    public static final String HTTP_DOMAIN = "http://seveneleven.esy.es";
    public static final String HTTP = "/android_connect/get_all_products.php";


    //@FormUrlEncoded
    //@POST("android_connect")

    @GET("/android_connect/get_all_products.php")
    public List<Product> getProducts();

}
