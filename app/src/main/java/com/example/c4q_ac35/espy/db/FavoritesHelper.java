package com.example.c4q_ac35.espy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by c4q-ac35 on 8/1/15.
 */
public class FavoritesHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "my-favorite-places.db";
    private static final int DB_VERSION = 1;

    private static FavoritesHelper INSTANCE;


    public static synchronized FavoritesHelper getInstance(Context context)
    {
        if(INSTANCE == null)
        {
            INSTANCE = new FavoritesHelper(context.getApplicationContext());
        }

        return INSTANCE;
    }

    public FavoritesHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void insertData(String tableName){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(tableName, null, null);
    }

    public static void insertRow(String venueName, String address, String phoneNumber, String hours, String tableName, double lat,double lon, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put(FavoritesEntry.COLUMN_NAME,venueName);
        values.put(FavoritesEntry.COLUMN_ADDRESS,address);
        values.put(FavoritesEntry.COLUMN_PHONE_NUMBER,phoneNumber);
        values.put(FavoritesEntry.COLUMN_HOURS,hours);
        values.put(FavoritesEntry.COLUMN_LAT,lat);
        values.put(FavoritesEntry.COLUMN_LON,lon);


        db.insertOrThrow(tableName,null,values);
    }

    public static abstract class FavoritesEntry implements BaseColumns{
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_ADDRESS = "Address";
        public static final String COLUMN_PHONE_NUMBER = "Phone Number";
        public static final String COLUMN_HOURS = "Hours";
        public static final String COLUMN_LAT = "Latitude";
        public static final String COLUMN_LON = "Longitude";
    }

    private static final String SQL_CREATE_TABLE_FAVORITES =
            "CREATE TABLE" + "IMMEDIATE SAFETY" +" (" +
                    FavoritesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FavoritesEntry.COLUMN_NAME+" TEXT," +
                    FavoritesEntry.COLUMN_ADDRESS+" TEXT," +
                    FavoritesEntry.COLUMN_LAT+" TEXT,"+
                    FavoritesEntry.COLUMN_LON+" TEXT,"+
                    FavoritesEntry.COLUMN_PHONE_NUMBER+" TEXT," +
                    FavoritesEntry.COLUMN_HOURS+" TEXT"+
                    " )";

    public String deleteTable(String tableName){
        String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + tableName.toUpperCase();
        return SQL_DELETE_ENTRIES;
    }
}