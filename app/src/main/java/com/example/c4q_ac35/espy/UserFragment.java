package com.example.c4q_ac35.espy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class UserFragment extends Fragment {
    ImageView userPic;


    public static UserFragment newInstance(int page, String title) {
       UserFragment userProfile= new UserFragment();
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
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        //userPic = (ImageView) view.findViewById(R.id.user_pic);
        return view;
    }
}
