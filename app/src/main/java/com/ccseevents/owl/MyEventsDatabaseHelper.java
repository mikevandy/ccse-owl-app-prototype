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
    public static final String TABLE_NAME1 = "all_events";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "DAYOFWEEK";
    public static final String COL_3 = "DAY";
    public static final String COL_4 = "MONTH";
    public static final String COL_5 = "YEAR";
    public static final String COL_6 = "FROMTIME";
    public static final String COL_7 = "TOTIME";
    public static final String COL_8 = "TITLE";
    public static final String COL_9 = "DESCRIPTION";
    public static final String COL_10 = "HOST";
    public static final String COL_11 = "LOCATION";
    public static final String COL_12 = "RSVPLINK";

    public MyEventsDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("create table "+TABLE_NAME+" ("+COL_1+" INTEGER PRIMARY KEY)");
       db.execSQL("create table "+TABLE_NAME1+" (ID INTEGER PRIMARY KEY, DAYOFWEEK TEXT, DAY TEXT, MONTH TEXT, YEAR TEXT, FROMTIME TEXT, TOTIME TEXT, TITLE TEXT, DESCRIPTION TEXT, HOST TEXT, LOCATION TEXT, RSVPLINK TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public boolean insertEvents(Integer Id, String dayOfWeek,String day,String month,String year,String fromTime,String toTime,String title,String description,String host,String location,String rsvplink) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,Id);
        contentValues.put(COL_2,dayOfWeek);
        contentValues.put(COL_3,day);
        contentValues.put(COL_4,month);
        contentValues.put(COL_5,year);
        contentValues.put(COL_6,fromTime);
        contentValues.put(COL_7,toTime);
        contentValues.put(COL_8,title);
        contentValues.put(COL_9,description);
        contentValues.put(COL_10,host);
        contentValues.put(COL_11,location);
        contentValues.put(COL_12,rsvplink);
        long result = db.insert(TABLE_NAME1,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME1,null);
        return res;
    }

    //My Events Table Functions
    public Cursor getMyEvents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME1 +" a INNER JOIN "+TABLE_NAME+" b on a.ID=b.ID",null);
        return res;
    }
    public boolean insertMyEvents(Integer Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,Id);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public boolean deleteMyEvents(Integer Id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,Id);
        long result = db.delete(TABLE_NAME,COL_1+"="+Id,null);
        if (result == -1)
            return false;
        else
            return true;
    }
    public boolean existsMyEvents(Integer eventID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME,null);
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            int val = res.getInt(0);
            if (eventID == val) {
                return true;
            }
        }
        return false;
    }
}
