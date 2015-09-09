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

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.example.c4q_ac35.espy.foursquare.FourSquareAPI;
import com.example.c4q_ac35.espy.foursquare.ResponseAPI;
import com.example.c4q_ac35.espy.foursquare.Venue;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;

/**
 * Created by c4q-marbella on 8/24/15.
 */
public class FavoriteActivity extends Fragment {

    public static final String BASE_API = "https://api.foursquare.com/v2";
    public static final String TAG = "Main Activity";
    FourSquareAPI servicesFourSquare = null;
    public Venue[] venuee = null;
    protected TextView name;
    protected TextView address;
    protected TextView phone;
    protected TextView favorite;
    private String title;
    private int page;
    RecyclerView mRecyclerView;
    RecyclerViewHeader mRecyclerViewHeader;
    private VenueAdapter adapter;
    public Venue[] fav = null;
    List favList= null;
    private boolean resultsFound = false;
    Context context;
    // newInstance constructor for creating fragment with arguments

    public static FavoriteActivity newInstance(int page, String title) {
        FavoriteActivity faveActivity = new FavoriteActivity();
        Bundle args = new Bundle();
        args.putInt("favePage", page);
        args.putString("Favorites", title);
        faveActivity.setArguments(args);
        return faveActivity;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("myListPage", 1);
        title = getArguments().getString("myList");

        RestAdapter mRestAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL).setLog(new AndroidLog(TAG))
                .setEndpoint(BASE_API).build();

        servicesFourSquare = mRestAdapter.create(FourSquareAPI.class);
        servicesFourSquare.getFeed("40.756296,-73.923944", new FourSquareCallback());

    }


    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_favorites, container, false);

            mRecyclerView = (RecyclerView) view.findViewById(R.id.favelist);
            mRecyclerViewHeader = (RecyclerViewHeader) view.findViewById(R.id.header);

            Log.d(TAG,"recycleviwerHeader");
      //  this.favorite = (EspyFont) view.findViewById(R.id.favorite_text);
//        android.graphics.Typeface font = android.graphics.Typeface.createFromAsset(getActivity().getAssets(), "fonts/poiret_one.ttf");
//        this.favorite.setTypeface(font);

//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                            .setDefaultFontPath("fonts/poiret_one.ttf")
//                            .setFontAttrId(R.attr.fontPath)
//                            .build()
//            );
//        this.name = (EspyFont) view.findViewById(R.id.item_name);
//        this.address = (EspyFont) view.findViewById(R.id.item_address);
//        this.phone = (EspyFont) view.findViewById(R.id.item_phone);
//        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/poiret_one.ttf");
//        this.name.setTypeface(font);
//        this.address.setTypeface(font);
//       this.phone.setTypeface(font);

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

    class FourSquareCallback implements Callback<ResponseAPI> {

        @Override
        public void success(final ResponseAPI responseAPI, Response response) {

            venuee = new Venue[responseAPI.getResponse().getVenues().size()];

//            for (int i = 0; i <responseAPI.getResponse().getVenues().size(); i++) {
//                Venue v = new Venue();
//                String name = responseAPI.getResponse().getVenues().get(i).getName();
//                String location = responseAPI.getResponse().getVenues().get(i).getLocation().getCity();
//
//                venuee[i] = v;
//                Log.d(TAG, name);
//            }

            resultsFound = true;
            if (adapter == null) {
                List<Venue> venueList = responseAPI.getResponse().getVenues();

                venuee = venueList.toArray(new Venue[venueList.size()]);

                adapter = new VenueAdapter(getActivity(), venuee);
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.setLayoutManager((new LinearLayoutManager(getActivity())));
                mRecyclerViewHeader.attachTo(mRecyclerView,true);

            }

            //adapter.setVenueses(venuee);
            //  adapter.notifyDataSetChanged();

            Log.d(TAG, "Success");
        }



        @Override
        public void failure(RetrofitError error) {
            Log.d(TAG, "Failure");

        }

    }

}
