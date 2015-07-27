package com.hernaez.seven_eleven.model.businesslayer;

import com.hernaez.seven_eleven.domain.Product;
import com.hernaez.seven_eleven.model.dataaccesslayer.ProductsHttp;

import java.util.List;

/**
 * Created by TAS on 7/27/2015.
 */
public class ProductManager {
    ProductsHttp productsHttp;

    public ProductManager(ProductsHttp productsHttp) {
        this.productsHttp = productsHttp;
    }

    public List<String> getAllProductsName() throws Exception {

        List<String> products = productsHttp.getAllProductsName();

        return products;
    }

    public Product getSpecificProduct(String product_name) throws Exception {
        Product specificproduct = productsHttp.getSpecificProduct(product_name);

        return specificproduct;
    }

    public List<Product> getAllProducts() throws Exception {

        List<Product> products = productsHttp.getAllProducts();

        return products;
    }

    public List<Product> getReorderProducts() throws Exception {

        List<Product> reOrderproducts = productsHttp.getReOrderProducts();

        return reOrderproducts;
    }
}
