package com.example.c4q_ac35.espy;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

/**
 * Created by c4q-marbella on 9/14/15.
 */
public class MyPreferenceFragment extends PreferenceFragment {
    public static final String TAG = "NotificationSettingsFragment";
    private boolean mNotificationSettingsChanged;
    public static boolean IS_VIBRATE = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.user_settings_layout);
    }

    private final Preference.OnPreferenceChangeListener mMuteBlogChangeListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            if (preference instanceof CheckBoxPreference) {
                CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
                boolean isChecked = (Boolean) newValue;
                checkBoxPreference.setChecked(isChecked);
                mNotificationSettingsChanged = true;
            }
            return false;
        }
    };


}
