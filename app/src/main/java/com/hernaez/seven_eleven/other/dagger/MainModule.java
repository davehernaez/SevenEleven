package com.hernaez.seven_eleven.other.dagger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hernaez.seven_eleven.model.businesslayer.Login;
import com.hernaez.seven_eleven.model.businesslayer.OrderDaoManager;
import com.hernaez.seven_eleven.model.businesslayer.ProductsRetrotfitManager;
import com.hernaez.seven_eleven.model.dataaccesslayer.greendao.DaoMaster;
import com.hernaez.seven_eleven.model.dataaccesslayer.greendao.DaoSession;
import com.hernaez.seven_eleven.model.dataaccesslayer.retrofit.HttpService;
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

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;


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
    Login provideLogin(HttpService httpService, AndroidUtils androidUtils) {
        return new Login(httpService, androidUtils);
    }

    @Singleton
    @Provides
    ProductsRetrotfitManager providesProductsRetrofitManager(HttpService httpService) {
        return new ProductsRetrotfitManager(httpService);
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
    RestAdapterRequestInterceptor provideRestAdapterRequestInterceptor(UserAgentProvider userAgentProvider, AndroidUtils androidUtils) {
        return new RestAdapterRequestInterceptor(userAgentProvider, androidUtils);
    }

    @Provides
    RestErrorHandler provideRestErrorHandler(Bus bus, RestAdapterRequestInterceptor restAdapterRequestInterceptor) {
        return new RestErrorHandler(bus, restAdapterRequestInterceptor);
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

    @Provides
    RestAdapter provideRestAdapter(Context context, RestErrorHandler restErrorHandler, RestAdapterRequestInterceptor restRequestInterceptor, Gson gson, OkHttpClient okHttpClient) {
        return new RestAdapter.Builder()
                .setClient(new OkClient(okHttpClient))
                .setEndpoint(HttpConstant.HTTP_DOMAIN)
                .setErrorHandler(restErrorHandler)
                .setRequestInterceptor(restRequestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .build();
    }

    @Singleton
    @Provides
    HttpService provideUsertHttpService(RestAdapter restAdapter) {
        return restAdapter.create(HttpService.class);
    }

    @Singleton
    @Provides
    DaoSession provideDaoSession(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "example-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }

    @Singleton
    @Provides
    OrderDaoManager providesOrderDaoManager(DaoSession daoSession) {
        return new OrderDaoManager(daoSession);
    }

}
