package com.example.c4q_ac35.espy;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.andraskindler.parallaxviewpager.ParallaxViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by c4q-vanice on 9/13/15.
 */
public class CustomBackgroundActivity extends FragmentActivity {
        protected ViewPager backgroundImagePager = null;
        protected List<Integer> imageSlides;
        protected ImageViewAdapter imageViewerAdapter;
        protected int currentBackgroundIndex = 0;
        protected Handler handler;
        Timer swipeTimer;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.splash_layout);



//            backgroundImagePager = (ViewPager) findViewById(R.id.background_image_pager);
//            imageViewerAdapter = new ImageViewAdapter(this);
//            backgroundImagePager.setAdapter(imageViewerAdapter);
//
//            imageSlides = new ArrayList<>();
//            imageSlides.add(R.drawable.milk_and_rose);
//            imageSlides.add(R.drawable.devicion);
//            imageSlides.add(R.drawable.barolo);
//
//            imageViewerAdapter.setImageResources(imageSlides);
//
//            handler = new Handler();
//        }
//
//        @Override
//        public void onResume() {
//            super.onResume();
//            startSlidingBackgroundImages();
//        }
//
//        @Override
//        public void onPause() {
//            super.onPause();
//            if (swipeTimer != null) {
//                swipeTimer.cancel();
//            }
//        }
//
//        protected void startSlidingBackgroundImages() {
//            if (imageSlides.size() > 0) {
//
//                final Runnable slideBackgroundImage = new Runnable() {
//                    public void run() {
//                        if (currentBackgroundIndex == imageSlides.size() - 1) {
//                            currentBackgroundIndex = 0;
//                        }
//                        backgroundImagePager.setCurrentItem(currentBackgroundIndex++, true);
//                    }
//                };
//
//                swipeTimer = new Timer();
//                swipeTimer.schedule(new TimerTask() {
//
//                    @Override
//                    public void run() {
//                        handler.post(slideBackgroundImage);
//                    }
//                }, 1000, 3000);
//            }
//
      }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
           // getMenuInflater().inflate(R.menu.menu_custom_background, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    }

