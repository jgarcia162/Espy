package com.example.c4q_ac35.espy;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by c4q-vanice on 9/9/15.
 */
public class EspyApplications extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//    Parse.initialize(this, "yOhhtZQhLjtWKhtacswgAMDkvvrjOS6gIF4VYnLa", "5Yg14PDRkGHKvDSerI8NLveKwUwpupUVzSD4lVfk");

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Roboto-ThinItalic.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .addCustomStyle(EspyFont.class, R.attr.textFieldStyle)
                        .build()
        );
    }
}
