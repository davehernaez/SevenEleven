package com.hernaez.seven_eleven.model.dataaccesslayer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    Context context;
    public static final String PRODUCT_ID = "_id";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_PRICE = "product_price";
    public static final String PRODUCT_QTY = "product_qty";
    public static final String PRODUCT_SUBTOTAL = "product_subtotal";
    public static final String PRODUCT_IMGPATH = "product_imgpath";

    private static final String DATABASE_NAME = "db_orders";
    private static final int DATABASE_VERSION = 8;

    public static final String[] ALL_FIELDS = new String[]{PRODUCT_ID,
            PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_QTY, PRODUCT_SUBTOTAL,
            PRODUCT_IMGPATH};

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + OrderDao.TABLE_ORDERS + "(" + PRODUCT_ID + " int, " + PRODUCT_NAME
            + " text not null," + PRODUCT_PRICE + " real," + PRODUCT_QTY
            + " int," + PRODUCT_SUBTOTAL + " real," + PRODUCT_IMGPATH
            + " text);";

    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        Log.e("db created", "new db created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + OrderDao.TABLE_ORDERS);
        onCreate(db);
    }


/*	public Cursor getAllRow() {
        SQLiteDatabase db = this.getWritableDatabase();

		String where = null;
		Cursor c = db.query(true, OrderDao.TABLE_ORDERS, ALL_FIELDS, where, null, null,
				null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}*/
}
