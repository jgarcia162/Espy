package com.example.c4q_ac35.espy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class UserActivity extends Fragment {
    ImageView userPic;


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
        int page = getArguments().getInt("myListPage", 1);
       String title = getArguments().getString("myList");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_user_setup, container, false);
        //userPic = (ImageView) view.findViewById(R.id.user_pic);
        return view;
    }
}
