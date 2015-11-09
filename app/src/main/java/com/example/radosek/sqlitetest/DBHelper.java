package com.example.radosek.sqlitetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by radosek on 8/12/2015.
 */
public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "MyDBName03.db";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_TYPE = "type";
    public static final String CONTACTS_COLUMN_COST = "cost";

    public static ArrayList<Entry> arrayList = new ArrayList<Entry>();

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("create table contacts " + "(id integer primary key, name text)");
        db.execSQL("CREATE TABLE contacts " + "(id INTEGER PRIMARY KEY, name TEXT, type INTEGER, cost INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertContact(String name, int cost, int type)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACTS_COLUMN_NAME, name);
        contentValues.put(CONTACTS_COLUMN_COST, cost);
        contentValues.put(CONTACTS_COLUMN_TYPE, type);
        db.insert("contacts", null, contentValues);
        return true;
    }

    //Cursor representuje vracena data
    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from contacts where id=" + id + "", null);
        //Cursor res =  db.rawQuery( "select * from contacts LIMIT 1 OFFSET "+id+"", null );
        return res;
    }

    public boolean updateContact(Integer id, String name, int cost, int type)
    {
        //TODO update zaznam
        SQLiteDatabase db = this.getReadableDatabase();
        String strFilter = "id=" + id;
        ContentValues args = new ContentValues();
        args.put(CONTACTS_COLUMN_NAME, name);
        args.put(CONTACTS_COLUMN_COST, cost);
        args.put(CONTACTS_COLUMN_TYPE, type);
        db.update("contacts", args, strFilter, null);
        return true;
    }

    public void setAllContacs()
    {
        arrayList.clear();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            arrayList.add(new Entry(res.getInt(res.getColumnIndex(CONTACTS_COLUMN_ID)), res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME))));
            res.moveToNext();
        }
    }

    public ArrayList<Entry> getAllContacsName()
    {
        return arrayList;
    }

    public void removeAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CONTACTS_TABLE_NAME, "1", null);
    }

    public void remove(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CONTACTS_TABLE_NAME, "id=" + id, null);
    }


}
