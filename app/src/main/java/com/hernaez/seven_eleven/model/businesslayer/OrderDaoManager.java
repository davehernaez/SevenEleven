package com.hernaez.seven_eleven.model.businesslayer;

import android.content.Context;
import android.util.Log;

import com.hernaez.seven_eleven.model.dataaccesslayer.greendao.DaoSession;
import com.hernaez.seven_eleven.model.dataaccesslayer.greendao.OrderTable;
import com.hernaez.seven_eleven.model.dataaccesslayer.greendao.OrderTableDao;

import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

/**
 * Created by TAS on 8/5/2015.
 */
public class OrderDaoManager {
    DaoSession daoSession;

    public OrderDaoManager(DaoSession daoSession) {
        this.daoSession = daoSession;
    }

    public OrderTableDao getOrderTableDao(Context context) {
        return daoSession.getOrderTableDao();
    }

    public void deleteSpecificByName(Context context, String productName){
        Query query = getOrderTableDao(context).queryBuilder().where(
                new WhereCondition.StringCondition("T.\"PRODUCT_NAME\" = '"+ productName+"'")).build();
        List<OrderTable> result = query.list();
        if(!result.isEmpty()){
            for (int i = 0;i<result.size();i++) {
                Log.e("id", result.get(i).getId()+"");
                deleteOrder(context, result.get(i).getId());
            }
        }
    }

    public boolean getOrderProductName(Context context, OrderTable orderTable) throws Exception{
        QueryBuilder qb = getOrderTableDao(context).queryBuilder().where(OrderTableDao.Properties.ProductName.eq(orderTable.getProductName()));
        List<OrderTable> result = qb.list();
            if (result.isEmpty()) {
                addOrder(context, orderTable);
                return false;
            } else {
                OrderTable orderTable1 = getOrderId(context, orderTable.getId());
                orderTable1.setProductQty(orderTable1.getProductQty() + orderTable.getProductQty());
                orderTable1.setProductSubtotal(orderTable1.getProductSubtotal() + orderTable.getProductSubtotal());

                updateOrder(context, orderTable1);
                return true;
            }


    }

    public void updateOrder(Context context, OrderTable orderTable) {
        getOrderTableDao(context).update(orderTable);
    }

    //addOrder
    public void addOrder(Context context, OrderTable orderTable) {
        getOrderTableDao(context).insert(orderTable);
    }

    //deleteAll
    public void deleteAll(Context context) {
        getOrderTableDao(context).deleteAll();
    }

    //deleteSpecific
    public void deleteOrder(Context context, long id) {
        getOrderTableDao(context).delete(getOrderId(context, id));

    }

    public OrderTable getOrderId(Context context, long id) {
        return getOrderTableDao(context).load(id);
    }

    public List<OrderTable> getAllOrders(Context context) {
        return getOrderTableDao(context).loadAll();

    }
}
