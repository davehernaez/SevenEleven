package com.hernaez.seven_eleven.other.dagger;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hernaez.seven_eleven.domain.User;
import com.hernaez.seven_eleven.model.businesslayer.Login;
import com.hernaez.seven_eleven.model.businesslayer.NewOrder;
import com.hernaez.seven_eleven.model.businesslayer.OrderManager;
import com.hernaez.seven_eleven.model.businesslayer.PlaceOrder;
import com.hernaez.seven_eleven.model.businesslayer.ProductManager;
import com.hernaez.seven_eleven.model.businesslayer.ReOrder;
import com.hernaez.seven_eleven.model.dataaccesslayer.DBHelper;
import com.hernaez.seven_eleven.model.dataaccesslayer.HttpAdapter;
import com.hernaez.seven_eleven.model.dataaccesslayer.LoginHttpAdapter;
import com.hernaez.seven_eleven.model.dataaccesslayer.NewOrderHttp;
import com.hernaez.seven_eleven.model.dataaccesslayer.OrderDao;
import com.hernaez.seven_eleven.model.dataaccesslayer.PlaceOrderHttp;
import com.hernaez.seven_eleven.model.dataaccesslayer.ProductsHttp;
import com.hernaez.seven_eleven.model.dataaccesslayer.ReOrderHttp;
import com.hernaez.seven_eleven.model.dataaccesslayer.retrofit.UserHttpService;
import com.hernaez.seven_eleven.other.HttpConstant;
import com.hernaez.seven_eleven.other.MainApplication;
import com.hernaez.seven_eleven.other.helper.AndroidUtils;
import com.hernaez.seven_eleven.other.helper.PostFromAnyThreadBus;
import com.hernaez.seven_eleven.other.retrofit.RestAdapterRequestInterceptor;
import com.hernaez.seven_eleven.other.retrofit.RestErrorHandler;
import com.hernaez.seven_eleven.other.retrofit.UserAgentProvider;
import com.hernaez.seven_eleven.viewcontroller.activity.LoginActivity;
import com.hernaez.seven_eleven.viewcontroller.activity.MainActivity;
import com.hernaez.seven_eleven.viewcontroller.fragment.AdminPageFragment;
import com.hernaez.seven_eleven.viewcontroller.fragment.AdminPageFragmentHolder;
import com.hernaez.seven_eleven.viewcontroller.fragment.CarouselFragment;
import com.hernaez.seven_eleven.viewcontroller.fragment.CustomerOrderFragment;
import com.hernaez.seven_eleven.viewcontroller.fragment.OrderSummaryFragment;
import com.hernaez.seven_eleven.viewcontroller.fragment.ProductListFragment;
import com.hernaez.seven_eleven.viewcontroller.fragment.ReOrderFragment;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import retrofit.http.Query;


/**
 * Dagger module for setting up provides statements.
 * Register all of your entry points below.
 */
@Module(
        complete = false,

        injects = {

                MainApplication.class,
                LoginActivity.class,

                MainActivity.class,
                CarouselFragment.class,
                CustomerOrderFragment.class,
                OrderSummaryFragment.class,
                AdminPageFragmentHolder.class,
                AdminPageFragment.class,
                ReOrderFragment.class,
                ProductListFragment.class
        }
)
public class MainModule {

    @Singleton
    @Provides
    Bus provideOttoBus() {
        return new PostFromAnyThreadBus();
    }

    @Provides
    @Singleton
    AndroidUtils provideAndroidUtils(Context context) {
        return new AndroidUtils(context);
    }

    @Singleton
    @Provides
    HttpAdapter provideLoginHttpAdapter() {
        return new HttpAdapter();
    }

    @Singleton
    @Provides
    LoginHttpAdapter provideLoginHttpAdapter(HttpAdapter httpAdapter) {
        return new LoginHttpAdapter(httpAdapter);
    }

    @Singleton
    @Provides
    Login provideLogin(/*LoginHttpAdapter loginHttpAdapter*/ UserHttpService userHttpService, AndroidUtils androidUtils) {
        return new Login(/*loginHttpAdapter*/ userHttpService, androidUtils);
    }

    @Singleton
    @Provides
    ReOrderHttp provideReOrderHttp(HttpAdapter httpAdapter) {
        return new ReOrderHttp(httpAdapter);
    }

    @Singleton
    @Provides
    ReOrder provideReOrder(ReOrderHttp reOrderHttp) {
        return new ReOrder(reOrderHttp);
    }

    @Singleton
    @Provides
    NewOrderHttp provideNewOrderHttp(HttpAdapter httpAdapter) {
        return new NewOrderHttp(httpAdapter);
    }
    @Provides
    Gson provideGson() {
        /**
         * GSON instance to use for all request  with date format set up for proper parsing.
         * <p/>
         * You can also configure GSON with different naming policies for your API.
         * Maybe your API is Rails API and all json values are lower case with an underscore,
         * like this "first_name" instead of "firstName".
         * You can configure GSON as such below.
         * <p/>
         *
         * public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd")
         *         .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES).create();
         */
        return new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
    }


    @Provides
    RestAdapterRequestInterceptor provideRestAdapterRequestInterceptor(UserAgentProvider userAgentProvider,AndroidUtils androidUtils) {
        return new RestAdapterRequestInterceptor(userAgentProvider,androidUtils);
    }
    @Provides
    RestErrorHandler provideRestErrorHandler(Bus bus,RestAdapterRequestInterceptor restAdapterRequestInterceptor) {
        return new RestErrorHandler(bus,restAdapterRequestInterceptor);
    }

    @Provides
    OkHttpClient provideRestAdapterRequestInterceptor(Context context) {
        OkHttpClient okHttpClient = new OkHttpClient();
        int cacheSize = 50 * 1024 * 1024; // 10 MiB
        File cacheDirectory = new File(context.getCacheDir().getAbsolutePath(), "HttpCache");
        Cache cache = new Cache(cacheDirectory, cacheSize);
        okHttpClient.setCache(cache);
        return okHttpClient;
    }
    @Singleton
    @Provides
    NewOrder provideNewOrder(NewOrderHttp newOrderHttp) {
        return new NewOrder(newOrderHttp);
    }

    @Singleton
    @Provides
    PlaceOrderHttp providePlaceOrderHttp(HttpAdapter httpAdapter) {
        return new PlaceOrderHttp(httpAdapter);
    }

    @Singleton
    @Provides
    PlaceOrder providePlaceOrder(PlaceOrderHttp placeOrderHttp) {
        return new PlaceOrder(placeOrderHttp);
    }

    @Singleton
    @Provides
    ProductsHttp providesProductHttp(HttpAdapter httpAdapter) {
        return new ProductsHttp(httpAdapter);
    }

    @Singleton
    @Provides
    ProductManager providesProductManager(ProductsHttp productsHttp) {
        return new ProductManager(productsHttp);
    }

    //

    @Provides
    RestAdapter provideRestAdapter(Context context, RestErrorHandler restErrorHandler, RestAdapterRequestInterceptor restRequestInterceptor, Gson gson, OkHttpClient okHttpClient) {
        return new RestAdapter.Builder()
                .setClient(new OkClient(okHttpClient))
                .setEndpoint(HttpConstant.HTTP_DOMAIN)
                .setErrorHandler(restErrorHandler)
//                .setRequestInterceptor(restRequestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .build();
    }

    @Singleton
    @Provides
    UserHttpService provideUsertHttpService(RestAdapter restAdapter) {
        return restAdapter.create(UserHttpService.class);
    }

    @Singleton
    @Provides
    DBHelper providesDbHelper(Context context) {
        return new DBHelper(context);
    }

    @Singleton
    @Provides
    OrderDao providesorderDao(DBHelper dbHelper) {
        return new OrderDao(dbHelper);
    }

    @Singleton
    @Provides
    OrderManager providesorderManager(OrderDao orderDao) {
        return new OrderManager(orderDao);
    }

}
