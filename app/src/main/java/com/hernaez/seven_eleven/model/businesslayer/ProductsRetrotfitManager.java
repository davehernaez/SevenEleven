package com.hernaez.seven_eleven.model.businesslayer;

import com.hernaez.seven_eleven.domain.Order;
import com.hernaez.seven_eleven.domain.Product;
import com.hernaez.seven_eleven.model.dataaccesslayer.retrofit.HttpService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dave Hernaez on 8/2/2015.
 */
public class ProductsRetrotfitManager {
    HttpService httpService;

    public ProductsRetrotfitManager(HttpService httpService) {
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

    public List<String> getAllNames() throws Exception {
        List<Product> products = httpService.getAllProducts();

        Integer count = products.toArray().length;
        Product product = new Product();
        List<String> productNames = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            products.get(i);
            product.productName = products.get(i).productName;
            productNames.add(product.productName);
        }
        return productNames;
    }

    public Product getSpecificProduct(String name) throws Exception {
        Product specificProduct = httpService.getSpecificProduct(name);
        return specificProduct;
    }

    public Order newOrder(Integer userid) throws Exception {
        Order order = httpService.newOrder(userid);
        return order;
    }

    public void placeOrder(Integer orderId, Product product) throws Exception {
        httpService.placeOrder(orderId, product.productId, product.productQty);


    }

    public void reOrder(String product_name, Integer product_qty) throws Exception {

        httpService.reOrder(product_name, product_qty);
    }

    public void addNewProduct(Product product, String imageString) throws Exception {
        //create new httpservice to add a new product to database here
        httpService.addNewProduct(product.productName,
                product.productQty,
                product.productPrice,
                product.productImgpath, imageString);
    }
}
