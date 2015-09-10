package com.example.c4q_ac35.espy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.c4q_ac35.espy.foursquare.Location;
import com.example.c4q_ac35.espy.foursquare.Venue;

import java.net.MalformedURLException;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by c4q-marbella on 8/22/15.
 */
public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.ViewHolder> {

    private static final String PRE_ENDPOINT = "https://maps.googleapis.com/maps/api/streetview?&size=800x400&location=";
    private static final String TAG = "VenueActivity";
    private Location mLocation;
    private Venue[] mVenues;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.item_name)
        TextView name;
        @Bind(R.id.item_address)
        TextView address;
        @Bind(R.id.item_phone)
        TextView phone;
        @Bind(R.id.venue_picture)
        ImageView mImageViewVenue;

        @Bind(R.id.faveBt) ImageView mFavoritesButton;
        @Bind(R.id.shareBt) ImageView mShareButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Venue venue = mVenues[position];
            //place intent to connect user with venue website
        }
    }

    public VenueAdapter(Context context, Venue[] venues) {
        this.mContext = context;
        this.mVenues = venues;
    }

    @Override
    public VenueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.venue_layout, parent, false);
        return new VenueAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VenueAdapter.ViewHolder holder, int position) {
        final Venue venue = mVenues[position];
        holder.name.setText(venue.getName());
        holder.address.setText(venue.getLocation().getCity());
        holder.phone.setText(venue.getContact().phone);

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
    public int getItemCount() {
        return mVenues.length;
    }
}