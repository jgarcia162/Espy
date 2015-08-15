
package com.example.c4q_ac35.espy.foursquare;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

import com.example.c4q_ac35.espy.foursquare.Venue;


public class Response {

    @Expose
    private List<Venue> venues = new ArrayList<Venue>();
    @Expose
    private boolean confident;

    /**
     * 
     * @return
     *     The venues
     */
    public List<Venue> getVenues() {
        return venues;
    }

    /**
     * 
     * @param venues
     *     The venues
     */
    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }

    /**
     * 
     * @return
     *     The confident
     */
    public boolean isConfident() {
        return confident;
    }

    /**
     * 
     * @param confident
     *     The confident
     */
    public void setConfident(boolean confident) {
        this.confident = confident;
    }

}
