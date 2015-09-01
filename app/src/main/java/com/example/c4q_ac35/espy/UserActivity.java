package com.example.c4q_ac35.espy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UserActivity extends Fragment {

    public static UserActivity newInstance(int page, String title) {
       UserActivity userProfile= new UserActivity();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        userProfile.setArguments(args);
        return userProfile;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getIntent();
//        addPreferencesFromResource(R.xml.user_settings_layout);
//        PreferenceManager.setDefaultValues(UserActivity.this, R.xml.user_settings_layout, false);

        int page = getArguments().getInt("myListPage", 1);
       String title = getArguments().getString("myList");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.user_layout, container, false);

        return view;
    }
}
