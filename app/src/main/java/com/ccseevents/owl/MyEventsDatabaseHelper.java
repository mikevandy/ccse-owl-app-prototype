package com.ccseevents.owl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.util.Date;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class MyEventsDatabaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "myevents.db";
    public static final String TABLE_NAME = "my_events";
    public static final String TABLE_NAME1 = "all_events";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "EVENTDATE";
    public static final String COL_3 = "TITLE";
    public static final String COL_4 = "DESCRIPTION";
    public static final String COL_5 = "HOST";
    public static final String COL_6 = "LOCATION";
    public static final String COL_7 = "RSVPLINK";

    public MyEventsDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("create table "+TABLE_NAME+" ("+COL_1+" INTEGER PRIMARY KEY)");
       db.execSQL("create table "+TABLE_NAME1+" (ID INTEGER PRIMARY KEY, EVENTDATE TEXT, TITLE TEXT, DESCRIPTION TEXT, HOST TEXT, LOCATION TEXT, RSVPLINK TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public boolean insertEvents(Integer Id, String eventdate, String title, String description, String host, String location, String rsvplink) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,Id);
        contentValues.put(COL_2, eventdate);
        contentValues.put(COL_3,title);
        contentValues.put(COL_4,description);
        contentValues.put(COL_5,host);
        contentValues.put(COL_6,location);
        contentValues.put(COL_7,rsvplink);
        long result = db.insert(TABLE_NAME1,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select ID,strftime('%w',eventDate),strftime('%d',eventDate),strftime('%m',eventDate),strftime('%Y',eventDate),strftime('%H:%M',eventDate),'7:00',title,description,host from "+ TABLE_NAME1 +" where eventdate>date('now') order by eventdate",null);
        return res;
    }

    //My Events Table Functions
    public Cursor getMyEvents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select a.ID,strftime('%w',eventDate),strftime('%d',eventDate),strftime('%m',eventDate),strftime('%Y',eventDate),strftime('%H:%M',eventDate),'7:00',title,description,host from "+ TABLE_NAME1 +" a INNER JOIN "+TABLE_NAME+" b on a.ID=b.ID where eventdate>date('now') order by eventdate",null);
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

    public Cursor featuredEvent(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from  "+ TABLE_NAME1 + " where eventdate>date('now') order by eventdate LIMIT 1",null);
        return res;
    }
}

