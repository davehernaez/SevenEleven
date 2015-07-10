package com.hernaez.seven_eleven.model.businesslayer;

import com.hernaez.seven_eleven.domain.Product;
import com.hernaez.seven_eleven.model.dataaccesslayer.GetSpecificProductHttp;

/**
 * Created by TAS on 7/10/2015.
 */
public class GetSpecificProduct {
    GetSpecificProductHttp getSpecificProductHttp;

    public GetSpecificProduct(){
        getSpecificProductHttp = new GetSpecificProductHttp();
    }

    public Product getSpecificProduct(String product_name) throws Exception {
        Product specificproduct = getSpecificProductHttp.getSpecificProduct(product_name);

        return specificproduct;
    }

}
