package com.hernaez.seven_eleven.model.dataaccesslayer.greendao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.hernaez.seven_eleven.model.dataaccesslayer.greendao.OrderTable;

import com.hernaez.seven_eleven.model.dataaccesslayer.greendao.OrderTableDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig orderTableDaoConfig;

    private final OrderTableDao orderTableDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        orderTableDaoConfig = daoConfigMap.get(OrderTableDao.class).clone();
        orderTableDaoConfig.initIdentityScope(type);

        orderTableDao = new OrderTableDao(orderTableDaoConfig, this);

        registerDao(OrderTable.class, orderTableDao);
    }
    
    public void clear() {
        orderTableDaoConfig.getIdentityScope().clear();
    }

    public OrderTableDao getOrderTableDao() {
        return orderTableDao;
    }

}
