package com.example.c4q_ac35.espy;

import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

/**
 * Created by c4q-marbella on 9/14/15.
 */
public class MyPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static final String TAG = "NotifSettings";
    private boolean mNotificationSettingsChanged;
    public static boolean IS_VIBRATE = false;
    private AudioManager myAudioManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.user_settings_layout);

       Preference nearbyEnabled = findPreference("pref_enablednearby");
////        Preference weeklyEnabled = findPreference("pref_enabledweekly");
////        Preference biWeeklyEnabled = findPreference("pref_enabledbiweekly");
////        Preference notifSound = findPreference("pref_notification_sound");
//        Preference notifVibrate = findPreference("pref_notification_vibrate");
////        Preference notiflight = findPreference("pref_notification_light");





//        boolean notifEnabledbt = sharedPreferences.getBoolean("pref_enablednotif", false);
//        boolean nearbyEnabled = sharedPreferences.getBoolean("pref_enablednearby", false);
//
//        if (nearbyEnabled) {
//            Toast.makeText(getActivity(), "Nearby Alerts off", Toast.LENGTH_LONG).show();
//        } else {
//            NotificationsService notificationsService = new NotificationsService();
//            notificationsService.sendWeeklyNotification("Nearby Alert");
//            Toast.makeText(getActivity(), "Nearby Alerts off", Toast.LENGTH_LONG).show();
//            Log.d(TAG, "enabled notif pref");
//        }

//        notifEnabled.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//            @Override
//            public boolean onPreferenceChange(Preference preference, Object newValue) {
//                mNotificationSettingsChanged=true;
//                if()
//
//
//                return false;
//            }
//        });

//        nearbyEnabled.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//            @Override
//            public boolean onPreferenceChange(Preference preference, Object newValue) {
//                nearbyEnabled = findPreference("pref_enablednearby");
//                NotificationsService notificationsService = new NotificationsService();
//                notificationsService.sendWeeklyNotification("Nearby Alert");
//                Toast.makeText(getActivity(), "Nearby Alerts on", Toast.LENGTH_LONG).show();
//                Log.d(TAG, "enabled notif pref");
//                return false;
//
//            }
//        });

//        notifVibrate.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//            @Override
//            public boolean onPreferenceChange(Preference preference, Object newValue) {
//
//              myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
//                Toast.makeText(getActivity(), "Silent Mode off", Toast.LENGTH_LONG).show();
//                return false;
//            }
//        });

    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

//        public void update


    }
}
