
package com.example.c4q_ac35.espy.foursquare;


import com.google.gson.annotations.Expose;


public class Stats {

    @Expose
    private long checkinsCount;
    @Expose
    private long usersCount;
    @Expose
    private long tipCount;

    /**
     * 
     * @return
     *     The checkinsCount
     */
    public long getCheckinsCount() {
        return checkinsCount;
    }

    /**
     * 
     * @param checkinsCount
     *     The checkinsCount
     */
    public void setCheckinsCount(long checkinsCount) {
        this.checkinsCount = checkinsCount;
    }

    /**
     * 
     * @return
     *     The usersCount
     */
    public long getUsersCount() {
        return usersCount;
    }

    /**
     * 
     * @param usersCount
     *     The usersCount
     */
    public void setUsersCount(long usersCount) {
        this.usersCount = usersCount;
    }

    /**
     * 
     * @return
     *     The tipCount
     */
    public long getTipCount() {
        return tipCount;
    }

    /**
     * 
     * @param tipCount
     *     The tipCount
     */
    public void setTipCount(long tipCount) {
        this.tipCount = tipCount;
    }

}
