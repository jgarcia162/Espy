package com.example.c4q_ac35.espy;

/**
 * Created by c4q-vanice on 9/13/15.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class CustomBackground extends PagerAdapter {

    protected int [] backgroundImage;
    protected Context mContext;
    protected LayoutInflater layoutInflater;

    public CustomBackground(Context context, int [] backgroundImage){
        mContext = context;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.backgroundImage = backgroundImage;
    }

    @Override
    public int getCount() {
        return backgroundImage.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //   layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewImage = layoutInflater.inflate(R.layout.splash_layout, container, false);
        ImageView imageView = (ImageView) viewImage.findViewById(R.id.imagePage);
        imageView.setImageResource(backgroundImage[position]);
        container.addView(imageView);
        return viewImage;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}

