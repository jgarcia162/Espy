package com.example.c4q_ac35.espy.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

/**
 * Created by c4q-ac35 on 9/10/15.
 */
public class MyFavoritesHelper extends OrmLiteSqliteOpenHelper {

    private static final String MYDB = "mydb.db";
    private static final int VERSION = 1;

    private static MyFavoritesHelper helper;

    public static MyFavoritesHelper getInstance(Context context){
        if(helper == null){
            helper = new MyFavoritesHelper(context.getApplicationContext());
        }
        return helper;
    }


    private MyFavoritesHelper(Context context) {
        super(context, MYDB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
    //TODO FINISH DB
        //getDao().create()

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }
}
