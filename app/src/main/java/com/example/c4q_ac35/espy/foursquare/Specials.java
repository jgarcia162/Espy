
package com.example.c4q_ac35.espy.foursquare;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;


public class Specials {

    @Expose
    private long count;
    @Expose
    private List<Object> items = new ArrayList<Object>();

    /**
     * 
     * @return
     *     The count
     */
    public long getCount() {
        return count;
    }

    /**
     * 
     * @param count
     *     The count
     */
    public void setCount(long count) {
        this.count = count;
    }

    /**
     * 
     * @return
     *     The items
     */
    public List<Object> getItems() {
        return items;
    }

    /**
     * 
     * @param items
     *     The items
     */
    public void setItems(List<Object> items) {
        this.items = items;
    }

}
