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
    GetReOrderProductsHttp getReOrderProductsHttp;

    public ProductList() {
        getAllProductsHttp = new GetAllProductsHttp();
        getReOrderProductsHttp = new GetReOrderProductsHttp();
    }

    public List<Product> getAllProducts() throws Exception {

        List<Product> products = getAllProductsHttp.getAllProducts();

        return products;
    }

    public List<Product> getReorderProducts() throws Exception {

        List<Product> products = getReOrderProductsHttp.getReOrderProducts();

        return products;
    }

    public List<String> getAllProductsName() throws Exception {

        List<String> products = getAllProductsHttp.getAllProductsName();

        return products;
    }
}
