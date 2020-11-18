package com.ccseevents.owl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class EventsDatabaseHelper extends SQLiteOpenHelper
{
    public static final String DATABASE_NAME = "myevents.db";
    public static final String TABLE_NAME = "all_events";
    public static final String TABLE_NAME1 = "my_events";
    public static final String TABLE_NAME2 = "hide_events";
    public static final String TABLE_NAME3 = "event_comments";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "EVENTDATE_START";
    public static final String COL_3 = "EVENTDATE_END";
    public static final String COL_4 = "TITLE";
    public static final String COL_5 = "DESCRIPTION";
    public static final String COL_6 = "HOST";
    public static final String COL_7 = "LOCATION";
    public static final String COL_8 = "RSVPLINK";
    public static final String COL_9 = "PHOTOURL";
    public static final String COL_10 = "ALLDAY";
    public static final String COMMENTCOL_2 = "COMMENTDATE";
    public static final String COMMENTCOL_3 = "COMMENTERNAME";
    public static final String COMMENTCOL_4 = "COMMENTCONTENT";
    public EventsDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" ("+COL_1+" INTEGER PRIMARY KEY, "+COL_2+"  TEXT, "+COL_3+" TEXT, "+COL_4+" TEXT, "+COL_5+" TEXT, "+COL_6+" TEXT, "+COL_7+" TEXT, "+COL_8+" TEXT, "+COL_9+" TEXT, "+COL_10+" BOOLEAN)");
        db.execSQL("create table "+TABLE_NAME1+" ("+COL_1+" INTEGER PRIMARY KEY)");
        db.execSQL("create table "+TABLE_NAME2+" ("+COL_1+" INTEGER PRIMARY KEY)");
        db.execSQL("create table "+TABLE_NAME3+" ("+COL_1+" INTEGER, "+COMMENTCOL_2+"  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, "+COMMENTCOL_3+" TEXT, "+COMMENTCOL_4+" TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    //All Data Table Functions
    public boolean insertEvents(Integer Id, String eventdate_start, String eventdate_end, String title, String description, String host, String location, String rsvplink, String photo, Boolean allday) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,Id);
        contentValues.put(COL_2, eventdate_start);
        contentValues.put(COL_3, eventdate_end);
        contentValues.put(COL_4,title);
        contentValues.put(COL_5,description);
        contentValues.put(COL_6,host);
        contentValues.put(COL_7,location);
        contentValues.put(COL_8,rsvplink);
        contentValues.put(COL_9,photo);
        contentValues.put(COL_10,allday);
        long result = db.insert(TABLE_NAME,null,contentValues);
        return result != -1;
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select ID,strftime('%w',eventDate_start),strftime('%d',eventDate_start),strftime('%m',eventDate_start),strftime('%Y',eventDate_start),strftime('%H:%M',eventDate_start),strftime('%H:%M',eventDate_end),title," +
                "CASE when instr(description,'http') > 0 then " +
                    "case when instr(substr(description,instr(description,'http'),length(description)),' ') = 0 then replace(description,substr(description,instr(description,'http'),length(description)),'<a href= '''||substr(description,instr(description,'http'),length(description))||'''>Click Here to Open Website</a>') " +
                    "else replace(description,substr(description,instr(description,'http'),instr(substr(description,instr(description,'http'),length(description)),' ')),'<a href= '''||substr(description,instr(description,'http'),instr(substr(description,instr(description,'http'),length(description)),' '))||'''>Click Here to Open Website </a>') end " +
                "else description end description," +
                "host,location,photourl" +
                " from "+ TABLE_NAME +" where (eventDate_start>date('now') or allday) and ID not in (select ID from "+TABLE_NAME2+") order by eventDate_start,title",null);
        return res;
    }
    public boolean deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,null,null);
        return result != -1;
    }
    public Cursor getEventDate(Integer eventID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select strftime('%d',eventDate_start),strftime('%m',eventDate_start),strftime('%Y',eventDate_start),strftime('%H',eventDate_start),strftime('%M',eventDate_start),strftime('%H',eventDate_end),strftime('%M',eventDate_end),location,allday" +
                "  from "+ TABLE_NAME+" where ID = "+eventID,null);
        return res;
    }
    //Featured Event
    public Cursor featuredEvent(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select ID,strftime('%w',eventDate_start),strftime('%d',eventDate_start),strftime('%m',eventDate_start),strftime('%Y',eventDate_start),strftime('%H:%M',eventDate_start),strftime('%H:%M',eventDate_end),title," +
                "CASE when instr(description,'http') > 0 then " +
                "case when instr(substr(description,instr(description,'http'),length(description)),' ') = 0 then replace(description,substr(description,instr(description,'http'),length(description)),'<a href= '''||substr(description,instr(description,'http'),length(description))||'''>Click Here to Open Website</a>') " +
                "else replace(description,substr(description,instr(description,'http'),instr(substr(description,instr(description,'http'),length(description)),' ')),'<a href= '''||substr(description,instr(description,'http'),instr(substr(description,instr(description,'http'),length(description)),' '))||'''>Click Here to Open Website </a>') end " +
                "else description end description," +
                "host,location,photourl,allday" +
                " from  "+ TABLE_NAME + " where (eventDate_start>date('now') or allday) order by eventDate_start,title LIMIT 1",null);
        return res;
    }

    //My Events Table Functions
    public Cursor getMyEvents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select a.ID,strftime('%w',eventDate_start),strftime('%d',eventDate_start),strftime('%m',eventDate_start),strftime('%Y',eventDate_start),strftime('%H:%M',eventDate_start),strftime('%H:%M',eventDate_end),title," +
                "CASE when instr(description,'http') > 0 then replace(description,substr(description,instr(description,'http'),length(description)),'<a href= '''||substr(description,instr(description,'http'),length(description))||'''>Click Here to Open Website</a>') else description end description," +
                "host,location,photourl" +
                " from "+ TABLE_NAME +" a INNER JOIN "+TABLE_NAME1+" b on a.ID=b.ID where (eventDate_start>date('now') or allday) order by eventDate_start,title",null);
        return res;
    }
    public boolean insertMyEvents(Integer eventID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,eventID);
        long result = db.insert(TABLE_NAME1,null,contentValues);
        return result != -1;
    }
    public boolean deleteMyEvents(Integer eventID) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME1,COL_1+"="+eventID,null);
        return result != -1;
    }
    public boolean existsMyEvents(Integer eventID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME1,null);
        while (res.moveToNext()){
            int val = res.getInt(0);
            if (eventID == val) {
                return true;
            }
        }
        return false;
    }
    //Hide Events
    public Cursor getHideEvents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select a.ID,strftime('%w',eventDate_start),strftime('%d',eventDate_start),strftime('%m',eventDate_start),strftime('%Y',eventDate_start),strftime('%H:%M',eventDate_start),strftime('%H:%M',eventDate_end),title," +
                "CASE when instr(description,'http') > 0 then " +
                "case when instr(substr(description,instr(description,'http'),length(description)),' ') = 0 then replace(description,substr(description,instr(description,'http'),length(description)),'<a href= '''||substr(description,instr(description,'http'),length(description))||'''>Click Here to Open Website</a>') " +
                "else replace(description,substr(description,instr(description,'http'),instr(substr(description,instr(description,'http'),length(description)),' ')),'<a href= '''||substr(description,instr(description,'http'),instr(substr(description,instr(description,'http'),length(description)),' '))||'''>Click Here to Open Website </a>') end " +
                "else description end description," +
                "host,location,photourl" +
                " from "+ TABLE_NAME +" a INNER JOIN "+TABLE_NAME2+" b on a.ID=b.ID where (eventDate_start>date('now') or allday)  order by eventDate_start,title",null);
        return res;
    }

    public boolean insertHideEvents(Integer eventID) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,eventID);
        long result = db.insert(TABLE_NAME2,null,contentValues);
        return result != -1;
    }
    public boolean deleteHideEvents(Integer eventID) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME2,COL_1+"="+eventID,null);
        return result != -1;
    }
    public boolean existsHideEvents() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select count() from "+ TABLE_NAME2,null);
        while (res.moveToNext()){
            int val = res.getInt(0);
            if (val > 0) {
                return true;
            }
        }
        return false;
    }

    public boolean eventisHidden(Integer eventID) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME2,null);
        while (res.moveToNext()){
            int val = res.getInt(0);
            if (eventID == val) {
                return true;
            }
        }
        return false;
    }

    public Cursor getEventsOnDate(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select ID,strftime('%w',eventDate_start),strftime('%d',eventDate_start),strftime('%m',eventDate_start),strftime('%Y',eventDate_start),strftime('%H:%M',eventDate_start),strftime('%H:%M',eventDate_end),title," +
                "CASE when instr(description,'http') > 0 then " +
                "case when instr(substr(description,instr(description,'http'),length(description)),' ') = 0 then replace(description,substr(description,instr(description,'http'),length(description)),'<a href= '''||substr(description,instr(description,'http'),length(description))||'''>Click Here to Open Website</a>') " +
                "else replace(description,substr(description,instr(description,'http'),instr(substr(description,instr(description,'http'),length(description)),' ')),'<a href= '''||substr(description,instr(description,'http'),instr(substr(description,instr(description,'http'),length(description)),' '))||'''>Click Here to Open Website </a>') end " +
                "else description end description," +
                "host,location,photourl" +
                " from "+ TABLE_NAME +" where (eventDate_start>date('now') or allday) and ID not in (select ID from "+TABLE_NAME2+") and strftime('%Y-%m-%d',eventDate_start) = '"+date+"' order by eventDate_start,title",null);
        return res;
    }

    public Cursor getEventsforMonth(String date){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select ID,strftime('%w',eventDate_start),strftime('%d',eventDate_start),strftime('%m',eventDate_start),strftime('%Y',eventDate_start),strftime('%H:%M',eventDate_start),strftime('%H:%M',eventDate_end),title," +
                "CASE when instr(description,'http') > 0 then " +
                "case when instr(substr(description,instr(description,'http'),length(description)),' ') = 0 then replace(description,substr(description,instr(description,'http'),length(description)),'<a href= '''||substr(description,instr(description,'http'),length(description))||'''>Click Here to Open Website</a>') " +
                "else replace(description,substr(description,instr(description,'http'),instr(substr(description,instr(description,'http'),length(description)),' ')),'<a href= '''||substr(description,instr(description,'http'),instr(substr(description,instr(description,'http'),length(description)),' '))||'''>Click Here to Open Website </a>') end " +
                "else description end description," +
                "host,location,photourl" +
                " from "+ TABLE_NAME +" where (eventDate_start>date('now') or allday)  and ID not in (select ID from "+TABLE_NAME2+") and strftime('%m',eventDate_start) = '"+date+"' order by eventDate_start,title",null);
        return res;
    }

    //Comment Table Functions
    public Cursor getEventComments(Integer eventID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME3 +" where ID = eventID order by commentdate",null);
        return res;
    }
    public boolean insertEventComment(Integer eventID, String user, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,eventID);
        contentValues.put(COMMENTCOL_3,user);
        contentValues.put(COMMENTCOL_4,comment);
        long result = db.insert(TABLE_NAME3,null,contentValues);
        return result != -1;
    }
    public boolean deleteEventComments(Integer eventID) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME3,COL_1+"="+eventID,null);
        return result != -1;
    }
}

