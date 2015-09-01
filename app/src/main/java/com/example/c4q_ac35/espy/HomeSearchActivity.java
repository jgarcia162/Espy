package com.example.c4q_ac35.espy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class HomeSearchActivity extends Fragment {
    private String title;
    private int page;
    private VenueAdapter adapter;
    public static final String BASE_API = "https://api.foursquare.com/v2";
    public static final String TAG = "Main Activity";
    FourSquareAPI servicesFourSquare = null;
    public Venue[] venuee = null;
    private boolean resultsFound = false;
    private RecyclerView mRecyclerView;
    private static final String LOG_TAG = "HomeSearchActivity";


    public static HomeSearchActivity newInstance(int page, String title) {
        HomeSearchActivity homeSearchActivity = new HomeSearchActivity();
        Bundle args = new Bundle();
        args.putInt("homePage", page);
        args.putString("home", title);
        homeSearchActivity.setArguments(args);
        return homeSearchActivity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        page = getArguments().getInt("homePage", 0);
        title = getArguments().getString("home");

        RestAdapter mRestAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL).setLog(new AndroidLog(TAG))
                .setEndpoint(BASE_API).build();

        servicesFourSquare = mRestAdapter.create(FourSquareAPI.class);
        servicesFourSquare.getFeed("40.7463956,-73.9852992", new FourSquareCallback());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_favorite_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.listView);

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
//
//
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