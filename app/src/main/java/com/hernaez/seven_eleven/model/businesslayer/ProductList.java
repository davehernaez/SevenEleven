package com.hernaez.seven_eleven.model.businesslayer;

import com.hernaez.seven_eleven.domain.Product;
import com.hernaez.seven_eleven.model.dataaccesslayer.GetAllProductsHttp;
import com.hernaez.seven_eleven.model.dataaccesslayer.GetReOrderProductsHttp;

import java.util.List;

/**
 * Created by TAS on 7/9/2015.
 */
public class ProductList {

    GetAllProductsHttp getAllProductsHttp;


    public ProductList(GetAllProductsHttp getAllProductsHttp) {
        this.getAllProductsHttp = getAllProductsHttp;
    }

    public List<Product> getAllProducts() throws Exception {

        List<Product> products = getAllProductsHttp.getAllProducts();

        return products;
    }
}
