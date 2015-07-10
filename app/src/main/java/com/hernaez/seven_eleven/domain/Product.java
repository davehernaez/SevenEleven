package com.hernaez.seven_eleven.domain;

/**
 * Created by TAS on 7/9/2015.
 */
public class Product {

    public String product_name;
    public String product_price;
    public String product_qty;
    public String product_imgpath;
    public String product_subtotal;

    public Product(String product_name, String product_price, String product_qty,
                   String subtotal, String product_imgpath) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.product_qty = product_qty;
        this.product_imgpath = product_imgpath;
    }

    public String getProdName() {
        return product_name;
    }

    public String getImageURL() {
        return product_imgpath;
    }

    public String getProdQty() {
        return product_qty;
    }

    public String getProdPrice() {
        return product_price;
    }

}
