package com.example.c4q_ac35.espy;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class SettingActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getIntent();
        addPreferencesFromResource(R.xml.user_settings_layout);
        PreferenceManager.setDefaultValues(SettingActivity.this, R.xml.user_settings_layout, false);

    }


}
