
package com.example.c4q_ac35.espy.foursquare;


import com.google.gson.annotations.Expose;


public class Meta {

    @Expose
    private long code;

    /**
     * 
     * @return
     *     The code
     */
    public long getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    public void setCode(long code) {
        this.code = code;
    }

}
