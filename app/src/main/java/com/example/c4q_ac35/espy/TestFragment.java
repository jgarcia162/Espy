package com.example.c4q_ac35.espy;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by c4q-ac35 on 8/13/15.
 *
 * FOR TESTING PURPOSES
 */
public class TestFragment extends android.support.v4.app.Fragment{
    ListView listView;
    ArrayList<String> mArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mArrayList = new ArrayList<>();

        mArrayList.add("One");
        mArrayList.add("Two");
        mArrayList.add("Three");

        final View fragmentView= inflater.inflate(R.layout.list_layout_for_test, container, false);

        //rowView=getActivity().getLayoutInflater().inflate(R.layout.custom_location_list_view_item, null);
        listView=(ListView) fragmentView.findViewById(R.id.list_view_test);
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,mArrayList);

        listView.setAdapter(adapter);

        return fragmentView;
    }

}
