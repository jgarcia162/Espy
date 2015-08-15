package com.example.c4q_ac35.espy;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;import java.lang.Override;import java.lang.String;import eboves.c4q.nyc.practiceespyfinal.R;


public class PlaceInf extends ActionBarActivity {

    private TextView mName;
    private TextView mAddressCity;
    private TextView mPhoneNumber;
    private TextView mWebsite;

    private String title;
    private String address;
    private String phoneNumber;
    private String website;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_inf);

        mWebsite = (TextView) findViewById(R.id.url);
        mPhoneNumber = (TextView) findViewById(R.id.phone_number);
        mName = (TextView) findViewById(R.id.nameID);
        mAddressCity = (TextView) findViewById(R.id.location_city);

        mWebsite.setText(website);
        mPhoneNumber.setText(phoneNumber);
        mName.setText(title);
        mAddressCity.setText(address);
    }


}
