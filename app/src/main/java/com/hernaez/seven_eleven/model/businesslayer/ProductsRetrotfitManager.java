package com.hernaez.seven_eleven.model.businesslayer;

import com.hernaez.seven_eleven.domain.Product;
import com.hernaez.seven_eleven.model.dataaccesslayer.retrofit.HttpService;

import java.util.List;

/**
 * Created by Dave Hernaez on 8/2/2015.
 */
public class ProductsRetrotfitManager {
    HttpService httpService;

    public ProductsRetrotfitManager(HttpService httpService){
        this.httpService = httpService;
    }

    public List<Product> getAllProducts() throws Exception {

        List<Product> products = httpService.getAllProducts();

        return products;
    }

    public List<Product> getAllReorders() throws Exception {

        List<Product> products = httpService.getAllReorders();

        return products;
    }
}
