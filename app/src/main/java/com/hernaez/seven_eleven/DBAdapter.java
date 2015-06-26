package com.hernaez.seven_eleven;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {

	public static final String PRODUCT_ID = "product_id";
	public static final String PRODUCT_NAME = "product_name";
	public static final String PRODUCT_PRICE = "product_price";
	public static final String PRODUCT_QTY = "product_qty";

	public static final String[] ALL_FIELDS = new String[] { PRODUCT_ID,
			PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_QTY };

	public static final int COL_PRODUCT_ID = 0;
	public static final int COL_PRODUCT_NAME = 1;
	public static final int COL_PRODUCT_PRICE = 2;
	public static final int COL_PRODUCT_QTY = 3;

	public static final String DB_NAME = "db_order_summary";
	public static final String DB_TABLE = "table_orders";
	public static final int DB_VERSION = 1;

	private static final String DATABASE_CREATE = "CREATE TABLE " + DB_TABLE
			+ "(" + PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ PRODUCT_NAME + " TEXT NOT NULL," + PRODUCT_PRICE + " TEXT,"
			+ PRODUCT_QTY + " TEXT," + ");";

	private final Context context;
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	public DBAdapter(Context ctx) {
		this.context = ctx;
		myDBHelper = new DatabaseHelper(context);
	}

	public DBAdapter open() {
		db = myDBHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		myDBHelper.close();
	}

	public void addOrder(String prodname, String prodprice, String prodqty) {

		ContentValues cv = new ContentValues();
		cv.put(PRODUCT_NAME, prodname);
		cv.put(PRODUCT_PRICE, prodprice);
		cv.put(PRODUCT_QTY, prodqty);

		db.insert(DB_TABLE, null, cv);
		Log.e("added", prodname + " " + prodprice + " " + prodqty);

	}

	public Cursor getAllRow() {
		String where = null;
		Cursor c = db.query(true, DB_TABLE, ALL_FIELDS, where, null, null,
				null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}
public void deleteDB(){
	context.deleteDatabase(DB_NAME);

}
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			// TODO Auto-generated method stub
			_db.execSQL(DATABASE_CREATE);
			Log.e("db created", _db.getVersion() + "");
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			_db.execSQL("DROP TABLE IF EXISTS" + DB_TABLE);

			onCreate(_db);
		}

	}

}
