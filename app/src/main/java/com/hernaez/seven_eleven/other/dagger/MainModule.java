package com.hernaez.seven_eleven.other.dagger;

import android.content.Context;

import com.google.gson.Gson;
import com.hernaez.seven_eleven.model.businesslayer.GetAllProductName;
import com.hernaez.seven_eleven.model.businesslayer.GetReOrderProducts;
import com.hernaez.seven_eleven.model.businesslayer.GetSpecificProduct;
import com.hernaez.seven_eleven.model.businesslayer.Login;
import com.hernaez.seven_eleven.model.businesslayer.NewOrder;
import com.hernaez.seven_eleven.model.businesslayer.PlaceOrder;
import com.hernaez.seven_eleven.model.businesslayer.ProductList;
import com.hernaez.seven_eleven.model.businesslayer.ReOrder;
import com.hernaez.seven_eleven.model.dataaccesslayer.GetAllProductsHttp;
import com.hernaez.seven_eleven.model.dataaccesslayer.GetReOrderProductsHttp;
import com.hernaez.seven_eleven.model.dataaccesslayer.GetSpecificProductHttp;
import com.hernaez.seven_eleven.model.dataaccesslayer.HttpAdapter;
import com.hernaez.seven_eleven.model.dataaccesslayer.LoginHttpAdapter;
import com.hernaez.seven_eleven.model.dataaccesslayer.NewOrderHttp;
import com.hernaez.seven_eleven.model.dataaccesslayer.PlaceOrderHttp;
import com.hernaez.seven_eleven.model.dataaccesslayer.ReOrderHttp;
import com.hernaez.seven_eleven.model.dataaccesslayer.retrofit.UserHttpService;
import com.hernaez.seven_eleven.other.MainApplication;
import com.hernaez.seven_eleven.other.helper.AndroidUtils;
import com.hernaez.seven_eleven.other.retrofit.RestAdapterRequestInterceptor;
import com.hernaez.seven_eleven.other.retrofit.RestErrorHandler;
import com.hernaez.seven_eleven.viewcontroller.activity.AdminPageActivity;
import com.hernaez.seven_eleven.viewcontroller.activity.CustomerOrderActivity;
import com.hernaez.seven_eleven.viewcontroller.activity.LoginActivity;
import com.hernaez.seven_eleven.viewcontroller.activity.MainActivity;
import com.hernaez.seven_eleven.viewcontroller.activity.OrderSummaryActivity;
import com.hernaez.seven_eleven.viewcontroller.activity.ProductListActivity;
import com.hernaez.seven_eleven.viewcontroller.activity.ReOrderActivity;
import com.hernaez.seven_eleven.viewcontroller.fragment.CarouselFragment;
import com.hernaez.seven_eleven.viewcontroller.fragment.CustomerOrderFragment;
import com.hernaez.seven_eleven.viewcontroller.fragment.OrderSummaryFragment;
import com.squareup.okhttp.OkHttpClient;

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
                AdminPageActivity.class,
                CustomerOrderActivity.class,
                ProductListActivity.class,
                ReOrderActivity.class,
                OrderSummaryActivity.class,

                MainActivity.class,
                CarouselFragment.class,
                CustomerOrderFragment.class,
                OrderSummaryFragment.class

        }
)
public class MainModule {
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
    Login provideLogin(LoginHttpAdapter loginHttpAdapter) {
        return new Login(loginHttpAdapter);
    }

    @Singleton
    @Provides
    GetAllProductsHttp provideGetAllProductsHttp(HttpAdapter httpAdapter) {
        return new GetAllProductsHttp(httpAdapter);
    }

    @Singleton
    @Provides
    ProductList provideProductList(GetAllProductsHttp getAllProductsHttp) {
        return new ProductList(getAllProductsHttp);
    }

    @Singleton
    @Provides
    GetAllProductName provideGetAllProductName(GetAllProductsHttp getReOrderProductsHttp) {
        return new GetAllProductName(getReOrderProductsHttp);
    }

    @Singleton
    @Provides
    GetReOrderProductsHttp provideRetReOrderProductsHttp(HttpAdapter httpAdapter) {
        return new GetReOrderProductsHttp(httpAdapter);
    }

    @Singleton
    @Provides
    GetReOrderProducts provideRetReOrderProducts(GetReOrderProductsHttp getReOrderProductsHttp) {
        return new GetReOrderProducts(getReOrderProductsHttp);
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
    GetSpecificProductHttp provideGetSpecificProductHttp(HttpAdapter httpAdapter) {
        return new GetSpecificProductHttp(httpAdapter);
    }

    @Singleton
    @Provides
    GetSpecificProduct provideGetSpecificProduct(GetSpecificProductHttp getSpecificProductHttp) {
        return new GetSpecificProduct(getSpecificProductHttp);
    }

    //

    @Provides
    RestAdapter provideRestAdapter(Context context,RestErrorHandler restErrorHandler, RestAdapterRequestInterceptor restRequestInterceptor, Gson gson,OkHttpClient okHttpClient) {
        return new RestAdapter.Builder()
                .setClient(new OkClient(okHttpClient))
                .setEndpoint(UserHttpService.HTTP_DOMAIN)
                .setErrorHandler(restErrorHandler)
                .setRequestInterceptor(restRequestInterceptor)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(new GsonConverter(gson))
                .build();
    }

    @Singleton
    @Provides
    UserHttpService provideProductHttpService(RestAdapter restAdapter){
        return restAdapter.create(UserHttpService.class);
    }

}
