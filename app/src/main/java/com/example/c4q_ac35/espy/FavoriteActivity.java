package com.example.c4q_ac35.espy;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.c4q_ac35.espy.foursquare.Venue;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by c4q-marbella on 8/24/15.
 */
public class FavoriteActivity extends Fragment {

    protected TextView name;
    protected TextView address;
    protected TextView phone;
    private String title;
    private int page;
    RecyclerView mRecyclerView;
    private VenueAdapter adapter;
    public Venue[] fav = null;
    List favList= null;
    // newInstance constructor for creating fragment with arguments

    public static FavoriteActivity newInstance(int page, String title) {
        FavoriteActivity fragmentFirst = new FavoriteActivity();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("myListPage", 1);
        title = getArguments().getString("myList");


       // adapter = new VenueAdapter(getActivity(), favList);
      //  mRecyclerView.setAdapter(adapter);
       // mRecyclerView.setLayoutManager((new LinearLayoutManager(getActivity())));

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.venue_layout, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.favelist);
        this.name = (TextView) view.findViewById(R.id.item_name);
        this.address = (TextView) view.findViewById(R.id.item_address);
        this.phone = (TextView) view.findViewById(R.id.item_phone);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/poiret_one.ttf");
        this.name.setTypeface(font);
        this.address.setTypeface(font);
        this.phone.setTypeface(font);

        return view;
    }

    public static final void setAppFont(ViewGroup mContainer, Typeface mFont) {
        if (mContainer == null || mFont == null) return;

        final int mCount = mContainer.getChildCount();
        for (int i = 0; i < mCount; ++i)
        {
            final View mChild = mContainer.getChildAt(i);
            if (mChild instanceof TextView)
            {
                ((TextView) mChild).setTypeface(mFont);
            }
            else if (mChild instanceof ViewGroup)
            {
                setAppFont((ViewGroup) mChild, mFont);
            }
        }
    }

}
