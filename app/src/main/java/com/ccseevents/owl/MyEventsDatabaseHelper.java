package com.ccseevents.owl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyEventsDatabaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "myevents.db";
    public static final String TABLE_NAME = "my_events";
    public static final String COL_1 = "ID";

    public MyEventsDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("create table "+TABLE_NAME+" ("+COL_1+" INTEGER PRIMARY KEY)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(Integer Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,Id);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean deleteData(Integer Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,Id);
        long result = db.delete(TABLE_NAME,COL_1+"="+Id,null);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME,null);
        return res;
    }

    public boolean existsData(Integer eventID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME,null);
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            Integer val = Integer.parseInt(res.getString(0));
            if (eventID == val)
                return true;
            else
                return false;
        }
        return false;
    }
}
