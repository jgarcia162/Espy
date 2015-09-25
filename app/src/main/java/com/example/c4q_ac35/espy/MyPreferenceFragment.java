package com.example.c4q_ac35.espy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;


public class MyPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.user_settings_layout);


    }



    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {


    }
}
