package com.example.c4q_ac35.espy;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.example.c4q_ac35.espy.foursquare.FourSquareAPI;
import com.example.c4q_ac35.espy.foursquare.ResponseAPI;
import com.example.c4q_ac35.espy.foursquare.Venue;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.android.AndroidLog;
import retrofit.client.Response;


public class SearchResultsActivity extends ActionBarActivity {

    EditText searchBar;
    private ListView mListViewSearchResult;
    private CustomeAdapter adapter;

    public static final String BASE_API = "https://api.foursquare.com/v2";
    public static final String TAG = "Main Activity";

    FourSquareAPI servicesFourSquare = null;
    private Venue[] venuee = null;

    private boolean resultsFound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        searchBar = (EditText) findViewById(R.id.search_view);

        mListViewSearchResult = (ListView) findViewById(R.id.listView);
//
//        mListViewSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if (! resultsFound) {
//                    return;
//                }
////                String name = responseAPI.getResponse().getVenues().get(i).getName();
////                String phone = responseAPI.getResponse().getVenues().get(i).getContact().phone;
////                String website = responseAPI.getResponse().getVenues().get(i).getUrl();
////                String address = responseAPI.getResponse().getVenues().get(i).getLocation().getCity() + "\n"
////                        + responseAPI.getResponse().getVenues().get(i).getLocation().getState();
////
////                Intent intent = new Intent(SearchResultsActivity.this, PlaceInf.class);
////
////                intent.putExtra("name", name);
////                intent.putExtra("phone", phone);
////                intent.putExtra("address", address);
////                intent.putExtra("website", website);
////                startActivity(intent);
//
//            }
//        });


        RestAdapter mRestAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL).setLog(new AndroidLog(TAG))
                .setEndpoint(BASE_API).build();

        servicesFourSquare = mRestAdapter.create(FourSquareAPI.class);
        servicesFourSquare.getFeed("40.7463956,-73.9852992", new FourSquareCallback());

    }

    public class FourSquareCallback implements Callback<ResponseAPI> {

        String zipCode = getIntent().getStringExtra("zipcode");;

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

                adapter = new CustomeAdapter(getApplicationContext(), R.layout.venue_layout, venuee);
                mListViewSearchResult.setAdapter(adapter);
            }
            adapter.setVenueses(venuee);
            adapter.notifyDataSetChanged();


            Log.d(TAG, "Success");
        }

        @Override
        public void failure(RetrofitError error) {
            Log.d(TAG, "Failure");

        }
    }






}
