package com.hernaez.seven_eleven.model.dataaccesslayer.retrofit;


import com.hernaez.seven_eleven.domain.Order;
import com.hernaez.seven_eleven.domain.Product;
import com.hernaez.seven_eleven.domain.User;
import com.hernaez.seven_eleven.other.HttpConstant;

import java.util.List;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

public interface HttpService {

    public static final String PARAM_LOGINUSERS_USERNAME = "username";
    public static final String PARAM_LOGINUSERS_PASSWORD = "password";
    public static final String PARAM_PRODUCT_SPECIFIC = "productName";
    public static final String PARAM_USER_ID = "userId";
    public static final String PARAM_ORDER_ID = "orderId";
    public static final String PARAM_PRODUCT_ID = "productId";
    public static final String PARAM_ORDER_QTY = "orderQty";
    public static final String PARAM_PRODUCT_NAME = "productName";
    public static final String PARAM_REORDER_QTY = "productQty";


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

    @FormUrlEncoded
    @POST(HttpConstant.HTTP_GET_SPECIFIC_PRODUCT)
    Product getSpecificProduct(
            @Field(PARAM_PRODUCT_SPECIFIC) String name);

    @FormUrlEncoded
    @POST(HttpConstant.HTTP_NEW_ORDER)
    Order newOrder(
            @Field(PARAM_USER_ID) Integer userId);

    @FormUrlEncoded
    @POST(HttpConstant.HTTP_PLACE_ORDER)
    Order placeOrder(
            @Field(PARAM_ORDER_ID) Integer orderId,
            @Field(PARAM_PRODUCT_ID) Integer productId,
            @Field(PARAM_ORDER_QTY) Integer orderQty);

    @FormUrlEncoded
    @POST(HttpConstant.HTTP_REORDER)
    Order reOrder(
            @Field(PARAM_PRODUCT_NAME) String productName,
            @Field(PARAM_REORDER_QTY) Integer productQty);
}
