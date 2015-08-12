package com.hernaez.seven_eleven.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TAS on 7/9/2015.
 */
public class Product implements Serializable {
    public Integer productId;
    public String productName;
    public Double productPrice;
    public Integer productQty;
    public String productImgpath;
}
