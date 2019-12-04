package com.peekay.shixun.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BookKeepDB extends SQLiteOpenHelper {
    public static final String CREAT_TABLE = "CREATE TABLE JILU(ID INTEGER PRIMARY KEY AUTOINCREMENT,TYPE INTEGER,TITLE TEXT,REMARK TEXT,MONEY REAL)";

    public BookKeepDB(Context context) {
        super(context, "BOOKKEEP.DB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS JILU");
        onCreate(db);
    }

    //添加记录
    public Boolean add(String title, String remark, Float money, int type) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TITLE", title);
        values.put("REMARK", remark);
        values.put("MONEY", money);
        values.put("TYPE", type);
        long i = sqLiteDatabase.insert("JILU", null, values);
        if (i == -1) {
            return false;
        } else {
            return true;
        }
    }

    //查询全部记录
    public Cursor getAll() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("JILU", null, null, null, null, null, null);
        return cursor;
    }
}
