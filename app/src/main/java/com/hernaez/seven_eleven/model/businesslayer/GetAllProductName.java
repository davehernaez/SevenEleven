package com.hernaez.seven_eleven.model.businesslayer;

import com.hernaez.seven_eleven.model.dataaccesslayer.GetAllProductsHttp;

import java.util.List;

/**
 * Created by TAS on 7/20/2015.
 */
public class GetAllProductName {
    GetAllProductsHttp getAllProductsHttp;
    public GetAllProductName(GetAllProductsHttp getAllProductsHttp){
        this.getAllProductsHttp = getAllProductsHttp;
    }

    public List<String> getAllProductsName() throws Exception {

        List<String> products = getAllProductsHttp.getAllProductsName();

        return products;
    }
}
