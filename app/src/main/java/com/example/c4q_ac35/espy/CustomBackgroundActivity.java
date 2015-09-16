package com.example.c4q_ac35.espy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.andraskindler.parallaxviewpager.ParallaxViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by c4q-vanice on 9/13/15.
 */
public class CustomBackgroundActivity extends Activity {
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

            final ImageView pinDrop = (ImageView) findViewById(R.id.imagePage);
            final Animation drop = AnimationUtils.loadAnimation(getBaseContext(), R.anim.drop);
            pinDrop.setAnimation(drop);
            drop.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    finish();
                    Intent firstStart = new Intent(getApplicationContext(), EspyMain.class);
                    startActivity(firstStart);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });


        }


    }

