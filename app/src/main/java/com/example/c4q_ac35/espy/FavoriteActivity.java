package com.example.c4q_ac35.espy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private VenueAdapter adapter;
    public static final String BASE_API = "https://api.foursquare.com/v2";
    public static final String TAG = "Main Activity";
    FourSquareAPI servicesFourSquare = null;
    public Venue[] venuee = null;
    private boolean resultsFound = false;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

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
        View view = inflater.inflate(R.layout.activity_my_venues, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.favelist);

        return view;
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
                // adapter = new CustomeAdapter(getApplicationContext(), R.layout.venue_layout, venuee);
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.setLayoutManager((new LinearLayoutManager(getActivity())));

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