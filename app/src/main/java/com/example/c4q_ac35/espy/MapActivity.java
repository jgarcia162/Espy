package com.example.c4q_ac35.espy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MapActivity extends Fragment {

    public static TextView venueName,venueAddress,venuePhone;

    public static MapActivity newInstance (int page, String title){
        MapActivity mapActivity = new MapActivity();
        Bundle args = new Bundle();
        args.putInt("mapPage", page);
        args.putString("map", title);
        mapActivity.setArguments(args);
        return mapActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int mapPage = getArguments().getInt("mapPage", 2);
       String mapActivity = getArguments().getString("map");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.activity_map,container,false);
        createMap();
        return view;
    }

    private void createMap(){
        EspyMapFragment espyMapFragment = new EspyMapFragment();
        FragmentManager fm = this.getFragmentManager();

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.map_box,espyMapFragment).commit();
    }
}