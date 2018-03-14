package com.example.c4q_ac35.espy;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.c4q_ac35.espy.foursquare.Location;
import com.example.c4q_ac35.espy.foursquare.Venue;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by c4q-marbella on 8/22/15.
 */
public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.ViewHolder> implements StickyRecyclerHeadersAdapter {
    private static final String PRE_ENDPOINT = "https://maps.googleapis.com/maps/api/streetview?&size=800x400&location=";
    private static final String TAG = "VenueActivity";
    private Location mLocation;
    public List<Venue> mVenues;
    private Context mContext;

    //MyFavoritesHelper myFavoritesHelper = new MyFavoritesHelper();


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_name)
        TextView name;
        @BindView(R.id.item_address)
        TextView address;
        @BindView(R.id.item_phone)
        TextView phone;
        @BindView(R.id.venue_picture)
        ImageView mImageViewVenue;
        @BindView(R.id.menu)
        ImageButton menuBt;
        @BindView(R.id.favorite_button)
        ImageButton favButton;
        @BindView(R.id.share_button)
        ImageView mShareButton;
        @BindView(R.id.done)
        ImageButton doneButton;
        @BindView(R.id.distance_marker)
        ImageButton distanceMaker;
        @BindView(R.id.item_distance)
        TextView distance;
        @BindView(R.id.menu_txt)
        TextView menu;

        public ViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public VenueAdapter(Context context, List<Venue> venues) {
        this.mContext = context;
        this.mVenues = venues;
    }

    @Override
    public VenueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.venue_layout, parent, false);
        return new VenueAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VenueAdapter.ViewHolder holder, int position) {
        final Venue venue = mVenues.get(position);

        holder.name.setText(venue.getName());
        String address = venue.getLocation().getFormattedAddress().toString();
        holder.address.setText(address.substring(1, address.length() - 1));
        holder.phone.setText(venue.getContact().phone);
//        holder.ratingBar.setText("" + venue.getStats().getUsersCount());
        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FavoritesFragment.venueList != null) {
                    FavoritesFragment.venueList.add(venue);
                } else {
                    FavoritesFragment.venueList = new ArrayList<Venue>();
                    FavoritesFragment.venueList.add(venue);
                    Toast.makeText(view.getContext(), FavoritesFragment.venueList.size() + " Favorites ", Toast.LENGTH_SHORT).show();
                    holder.favButton.setVisibility(View.INVISIBLE);
                }
            }
        });
        DecimalFormat df2 = new DecimalFormat("###.##");
        holder.distance.setText(df2.format(venue.getLocation().getDistance() * (0.000621371)) + " mi");
        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FavoritesFragment.venueList != null
                        && !FavoritesFragment.venueList.contains(venue)) {
                    FavoritesFragment.venueList.add(venue);
//                    EspyMain.startLocationUpdates();

                } else {
                    FavoritesFragment.venueList = new ArrayList<Venue>();
                    FavoritesFragment.venueList.add(venue);
                    Toast.makeText(view.getContext(), "Added " + venue.getName() + " to Favorites ", Toast.LENGTH_SHORT).show();
                    holder.favButton.setVisibility(View.INVISIBLE);
                    //holder.favButton.setEnabled(false);
//                    EspyMain.startLocationUpdates();
                }

                SharedPreferences pref = mContext.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                float radius = pref.getFloat(Constants.PREFERENCE_DISTANCE, Constants.GEOFENCE_RADIUS_IN_METERS);
                EspyApplication.populateGeofenceList(radius);
                ((EspyApplication) mContext.getApplicationContext()).addGeofences();
            }
        });

        holder.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FavoritesFragment.venueList != null
                        && !FavoritesFragment.venueList.isEmpty()
                        && !UserFragment.historyList.contains(venue)) {
                    FavoritesFragment.venueList.remove(venue);
                    Toast.makeText(v.getContext(), "NICE!", Toast.LENGTH_SHORT).show();
                    UserFragment.historyList.add(venue);
                    holder.doneButton.setVisibility(View.INVISIBLE);
                } else {
                }
            }
        });


        if (venue.getMenu() != null) {
            holder.menu.setText(venue.getMenu().getMobileUrl());
            holder.menu.setVisibility(View.INVISIBLE);
        }

        holder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String venuePhone = venue.getContact().getPhone();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + venuePhone));
                if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mContext.startActivity(callIntent);
            }
        });

        holder.mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendInvite = new Intent();
                sendInvite.setAction(Intent.ACTION_SEND);
                sendInvite.putExtra(Intent.EXTRA_TEXT, venue.getName() + venue.getLocation().getFormattedAddress());
                sendInvite.setType("text/plain");
                mContext.startActivity(sendInvite);

                Log.d(TAG, "share button:" + venue.getName() + venue.getLocation().getFormattedAddress());
            }
        });

        holder.menuBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(mContext);
                alert.setTitle("Menu");

                WebView wv = new WebView(mContext);
                wv.loadUrl(holder.menu.getText().toString());
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);

                        return true;
                    }
                });

                alert.setView(wv);
                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                alert.show();

            }
        });

        mLocation = venue.getLocation();

        String urlString = PRE_ENDPOINT + mLocation.getLat() + "," + mLocation.getLng();
        URL url = null;
        try {
            url = new URL(urlString);
            Log.d(TAG, "Latitud:  " + mLocation.getLat());
            Log.d(TAG, "Longitud:  " + mLocation.getLng());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        holder.mImageViewVenue.setImageBitmap(null);
        Glide.with(mContext).load(url).centerCrop().into(holder.mImageViewVenue);
        Log.w("TAG", "Called");
    }

    @Override
    public long getHeaderId(int i) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mVenues.size();

    }

    public void setVenues(List<Venue> venues){
        this.mVenues = venues;
        notifyDataSetChanged();
    }
}