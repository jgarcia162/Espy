package com.example.c4q_ac35.espy.foursquare;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by c4q-vanice on 9/16/15.
 */
public interface StickyRecyclerHeadersAdapter<VH extends RecyclerView.ViewHolder> {
    public long getHeaderId(int position);

    public VH onCreateHeaderViewHolder(ViewGroup parent);

    public void onBindHeaderViewHolder(VH holder, int position);

    public int getItemCount();
}
