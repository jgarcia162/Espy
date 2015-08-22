package com.example.c4q_ac35.espy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.c4q_ac35.espy.foursquare.Venue;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by c4q-marbella on 8/22/15.
 */
public class VenueAdapter extends RecyclerView.Adapter<VenueAdapter.ViewHolder> {

    private Venue [] mVenues;
    private Context mContext;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.item_name) TextView name;
        @Bind(R.id.item_address) TextView address;
        @Bind(R.id.item_phone) TextView phone;


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

        View itemView = LayoutInflater.from(mContext).inflate(R.layout.venue_layout,parent,false);
        return new VenueAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VenueAdapter.ViewHolder holder, int position) {
        Venue venue = mVenues[position];
        holder.name.setText(venue.getName());
        holder.address.setText(venue.getLocation().getCity());
        holder.phone.setText(venue.getContact().phone);

        Log.w("TAG", "Called");


    }

    @Override
    public int getItemCount() {
        return mVenues.length;
    }


}
