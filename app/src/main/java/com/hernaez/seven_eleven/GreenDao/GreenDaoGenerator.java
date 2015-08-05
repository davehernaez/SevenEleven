package com.hernaez.seven_eleven.GreenDao;



import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by TAS on 8/5/2015.
 */
public class GreenDaoGenerator {
    public  static  void main(String[] args) throws  Exception{
    Schema schema = new Schema(2,"com.hernaez.seven_eleven.model.dataaccesslayer.greendao");
        Entity entity = schema.addEntity("OrderTable");
        entity.addIdProperty();
        entity.addStringProperty("productName");
        entity.addIntProperty("productQty");
        entity.addDoubleProperty("productPrice");
        entity.addDoubleProperty("productSubtotal");
        entity.addStringProperty("productImgPath");
        String outputDirectory = "app/src/main/java";
        new DaoGenerator().generateAll(schema, outputDirectory);
    }
}
