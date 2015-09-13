package com.example.c4q_ac35.espy.db;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by c4q-ac35 on 9/10/15.
 */
public class FavoritesData {

    @DatabaseField(generatedId = true)
    int id;
    @DatabaseField(index = true)
    String name;
    @DatabaseField
    double latitude;
    @DatabaseField
    double longitude;

    FavoritesData(){

    }

    public FavoritesData(String name,double latitude,double longitude){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id=").append(id);
        sb.append(", ").append("name=").append(name);
        sb.append(", ").append("lat=").append(latitude);
        sb.append(", ").append("lon=").append(longitude);
        return super.toString();
    }
}
