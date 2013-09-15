package com.example.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class MySQLiteHelper extends SQLiteOpenHelper {
	
	public static final String TABLE_NAME = "entry";    
    public static final String KEY_ROWID = BaseColumns._ID;
    public static final String KEY_GUID = "guid";
	public static final String KEY_READ = "read";

	
	
	public static final String DATABASE_NAME = "Articles.db";
	public static final int DATABASE_VERSION = 1;
	private static final String SQL_CREATE_ENTRIES = 
				"CREATE TABLE " + TABLE_NAME + "(" + 
				KEY_ROWID + " integer primary key autoincrement, " + 
				KEY_GUID + " text not null, " + 
	    		KEY_READ + " boolean not null" +
	    		");";
	private static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + TABLE_NAME;

	
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
	}
}
