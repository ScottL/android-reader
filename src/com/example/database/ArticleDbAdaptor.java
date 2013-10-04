package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import com.example.myreader.data.Article;

public class ArticleDbAdaptor {
	
	public static final String TABLE_NAME = "entry";
    public static final String KEY_ROWID = BaseColumns._ID;
    public static final String KEY_GUID = "guid";
	public static final String KEY_READ = "read";
	public static final String KEY_HIDDEN = "hidden";
	
	private MySQLiteHelper sqLiteHelper;
	private SQLiteDatabase sqLiteDatabase;
	private Context context;
	
	public ArticleDbAdaptor(Context c){
		context = c;
	}

	public ArticleDbAdaptor openToRead() throws android.database.SQLException {
		sqLiteHelper = new MySQLiteHelper(context);
		sqLiteDatabase = sqLiteHelper.getReadableDatabase();
		return this; 
	}

	public ArticleDbAdaptor openToWrite() throws android.database.SQLException {
		sqLiteHelper = new MySQLiteHelper(context);
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this; 
	}

	public void close(){
		sqLiteHelper.close();
	}

	public long insertBlogListing(String guid) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_GUID, guid);
        initialValues.put(KEY_READ, false);
        initialValues.put(KEY_HIDDEN, false);
        return sqLiteDatabase.insert(TABLE_NAME, null, initialValues);
    }
    
    public Article getBlogListing(String guid) throws SQLException {
        Cursor mCursor =
        		sqLiteDatabase.query(true, TABLE_NAME, new String[] {
                		KEY_ROWID,
                		KEY_GUID, 
                		KEY_READ,
                		KEY_HIDDEN,
                		}, 
                		KEY_GUID + "= '" + guid + "'", 
                		null,
                		null, 
                		null, 
                		null, 
                		null);
        if (mCursor != null && mCursor.getCount() > 0) {
        	mCursor.moveToFirst();
        	Article a = new Article();
   			a.setGuid(mCursor.getString(mCursor.getColumnIndex(KEY_GUID)));
   			a.setRead(mCursor.getInt(mCursor.getColumnIndex(KEY_READ)) > 0);
   			a.setHidden(mCursor.getInt(mCursor.getColumnIndex(KEY_HIDDEN)) > 0);
   			a.setDbId(mCursor.getLong(mCursor.getColumnIndex(KEY_ROWID)));
   			return a;
        }
        return null;
    }
    
    public boolean markAsRead(String guid) {
        ContentValues args = new ContentValues();
        args.put(KEY_READ, true);
        return sqLiteDatabase.update(TABLE_NAME, args, KEY_GUID + "='" + guid+"'", null) > 0;
    }
    
    public boolean markAsUnread(String guid) {
        ContentValues args = new ContentValues();
        args.put(KEY_READ, false);
        return sqLiteDatabase.update(TABLE_NAME, args, KEY_GUID + "='" + guid+"'", null) > 0;
    }
}
