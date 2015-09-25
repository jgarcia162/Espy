package com.example.c4q_ac35.espy.db;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.c4q_ac35.espy.foursquare.Contact;
import com.example.c4q_ac35.espy.foursquare.Location;
import com.example.c4q_ac35.espy.foursquare.Venue;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Created by c4q-ac35 on 9/14/15.
 */
public class DaoFactory extends Application {
    private SharedPreferences sharedPreferences;
    private MyFavoritesHelper myFavoritesHelper = null;

    private Dao<Venue,Integer> venueDao=null;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        myFavoritesHelper = new MyFavoritesHelper(this);


    }

    public SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }

    public Dao<Venue, Integer> getVenueDao() throws SQLException {
        if (venueDao == null) {
            venueDao = myFavoritesHelper.getDao(Venue.class);
        }
        return venueDao;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (myFavoritesHelper != null) {
            OpenHelperManager.releaseHelper();
            myFavoritesHelper = null;
        }
    }


}
