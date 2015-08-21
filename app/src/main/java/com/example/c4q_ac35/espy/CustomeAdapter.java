package com.example.c4q_ac35.espy;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.c4q_ac35.espy.foursquare.Venue;import java.lang.Override;

/**
 * Created by elvisboves on 8/15/15.
 */
public class CustomeAdapter extends ArrayAdapter<Venue> {

    Venue[] mVenueses;


    public CustomeAdapter(Context context, int resource, Venue[] venueses) {
        super(context, resource, venueses);
        this.mVenueses = venueses;
    }

    public void setVenueses(Venue[] venueses) {
        mVenueses = venueses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View view = inflater.inflate(R.layout.venue_layout, parent, false);
        TextView name = (TextView) view.findViewById(R.id.item_name);
        TextView address = (TextView) view.findViewById(R.id.item_address);
        TextView phone = (TextView) view.findViewById(R.id.item_phone);

       // name.setText(mVenueses[position].getName());

        Venue venue = mVenueses[position];

        name.setText(venue.getName());
        address.setText(venue.getLocation().getCity());
        phone.setText(venue.getContact().phone);

        Log.w("TAG", "Called");

        return view;
    }
}
