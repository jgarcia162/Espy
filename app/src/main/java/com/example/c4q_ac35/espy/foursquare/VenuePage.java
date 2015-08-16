
package com.example.c4q_ac35.espy.foursquare;


import com.google.gson.annotations.Expose;


public class VenuePage {

    @Expose
    private String id;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

}
