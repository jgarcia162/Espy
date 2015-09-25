package com.example.c4q_ac35.espy;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.example.c4q_ac35.espy.db.MyFavoritesHelper;
import com.example.c4q_ac35.espy.foursquare.FourSquareAPI;
import com.example.c4q_ac35.espy.foursquare.Venue;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by c4q-marbella on 8/24/15.
 */
public class FavoritesFragment extends Fragment {

    public static final String BASE_API = "https://api.foursquare.com/v2";
    public static final String TAG = "Main Activity";
    FourSquareAPI servicesFourSquare = null;
    public static List<Venue> venueList = null;
    private String title;
    private int page;
    RecyclerView mRecyclerView;
    RecyclerViewHeader mRecyclerViewHeader;
    private VenueAdapter adapter;
    public Venue[] fav = null;
    List favList = null;
    private boolean resultsFound = false;
    Context context;
    protected TextView favorite;
    MyFavoritesHelper myFavoritesHelper;
    // newInstance constructor for creating fragment with arguments

    public static FavoritesFragment newInstance(int page, String title) {
        FavoritesFragment favFragment = new FavoritesFragment();
        Bundle args = new Bundle();
        args.putInt("favePage", page);
        args.putString("Favorites", title);
        favFragment.setArguments(args);
        return favFragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO TEST THIS
        try {
            venueList = myFavoritesHelper.getVenueDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        myFavoritesHelper.close();

//
//        page = getArguments().getInt("myListPage", 1);
//        title = getArguments().getString("myList");


        //RestAdapter mRestAdapter = new RestAdapter.Builder()
//                .setLogLevel(RestAdapter.LogLevel.FULL).setLog(new AndroidLog(TAG))
//                .setEndpoint(BASE_API).build();
//        servicesFourSquare = mRestAdapter.create(FourSquareAPI.class);
        //servicesFourSquare.getFeed("40.756296,-73.923944", new FourSquareCallback());
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        //myFavoritesHelper = new MyFavoritesHelper(view.getContext());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.favelist);
        mRecyclerViewHeader = (RecyclerViewHeader) view.findViewById(R.id.header);
//            this.favorite = (TextView) view.findViewById(R.id.favorite_text);
//            android.graphics.Typeface font = android.graphics.Typeface.createFromAsset(getActivity().getAssets(), "fonts/poiret_one.ttf");
//            this.favorite.setTypeface(font);
//        try {
//            if(myFavoritesHelper.getVenueDao() !=null){
//                venueList = myFavoritesHelper.getVenueDao().queryForAll();
//            } else{
//                venueList = null;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        mRecyclerView.setLayoutManager((new LinearLayoutManager(getActivity())));
        mRecyclerViewHeader.attachTo(mRecyclerView, true);

        if (venueList != null) {
            adapter = new VenueAdapter(getActivity(), venueList);
            mRecyclerView.setAdapter(adapter);
        }
        return view;
    }

    public static final void setAppFont(ViewGroup mContainer, Typeface mFont) {
        if (mContainer == null || mFont == null) return;

        final int mCount = mContainer.getChildCount();
        for (int i = 0; i < mCount; ++i) {
            final View mChild = mContainer.getChildAt(i);
            if (mChild instanceof TextView) {
                ((TextView) mChild).setTypeface(mFont);
            } else if (mChild instanceof ViewGroup) {
                setAppFont((ViewGroup) mChild, mFont);
            }
        }
    }

//    class FourSquareCallback implements Callback<ResponseAPI> {
//
//        @Override
//        public void success(final ResponseAPI responseAPI, Response response) {
//
//            venueList = responseAPI.getResponse().getVenues();
//
//            resultsFound = true;
//            if (adapter == null) {
//                resultsFound = true;
//                List<Venue> venueList = responseAPI.getResponse().getVenues();
//
//                adapter = new VenueAdapter(getActivity(), venueList);
//                mRecyclerView.setAdapter(adapter);
//                mRecyclerView.setLayoutManager((new LinearLayoutManager(getActivity())));
//                mRecyclerViewHeader.attachTo(mRecyclerView, true);
//
//                Log.d(TAG, "Success");
//            }
//
//        }
//
//        @Override
//        public void failure(RetrofitError error) {
//
//        }
//
//    }
}

