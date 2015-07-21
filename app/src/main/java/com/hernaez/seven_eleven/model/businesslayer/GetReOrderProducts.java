package com.hernaez.seven_eleven.model.businesslayer;

import com.hernaez.seven_eleven.domain.Product;
import com.hernaez.seven_eleven.model.dataaccesslayer.GetReOrderProductsHttp;

import java.util.List;

/**
 * Created by TAS on 7/20/2015.
 */
public class GetReOrderProducts {
    GetReOrderProductsHttp getReOrderProductsHttp;

    public GetReOrderProducts(GetReOrderProductsHttp getReOrderProductsHttp)
    {
        this.getReOrderProductsHttp = getReOrderProductsHttp;
    }


    public List<Product> getReorderProducts() throws Exception {

        List<Product> reOrderproducts = getReOrderProductsHttp.getReOrderProducts();

        return reOrderproducts;
    }
}
