package com.example.c4q_ac35.espy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.example.c4q_ac35.espy.foursquare.Venue;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {
    public static List<Venue> historyList = new ArrayList<Venue>();
    private RecyclerView hRecyclerView;
    private RecyclerViewHeader hRecyclerViewHeader;
    private VenueAdapter adapter;



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
        hRecyclerView = (RecyclerView) view.findViewById(R.id.historylist);
        hRecyclerViewHeader = (RecyclerViewHeader) view.findViewById(R.id.header);
        hRecyclerView.setLayoutManager((new LinearLayoutManager(getActivity())));
        hRecyclerViewHeader.attachTo(hRecyclerView, true);
        if (historyList != null) {
            adapter = new VenueAdapter(getActivity(), historyList);
            hRecyclerView.setAdapter(adapter);
        }
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(hRecyclerView);
        return view;
    }

    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            int position = viewHolder.getAdapterPosition();
            historyList.remove(position);
            hRecyclerView.getAdapter().notifyItemRemoved(position);
        }
    };
}
