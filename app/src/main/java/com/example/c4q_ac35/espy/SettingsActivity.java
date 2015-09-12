package com.example.c4q_ac35.espy;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.layout_user_setup);
        getIntent();
        addPreferencesFromResource(R.xml.user_settings_layout);
        PreferenceManager.setDefaultValues(this, R.xml.user_settings_layout, false);

    }


}
