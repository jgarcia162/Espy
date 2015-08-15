
package com.example.c4q_ac35.espy.foursquare;


import com.google.gson.annotations.Expose;


public class Menu {

    @Expose
    private String type;
    @Expose
    private String label;
    @Expose
    private String anchor;
    @Expose
    private String url;
    @Expose
    private String mobileUrl;

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The label
     */
    public String getLabel() {
        return label;
    }

    /**
     * 
     * @param label
     *     The label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * 
     * @return
     *     The anchor
     */
    public String getAnchor() {
        return anchor;
    }

    /**
     * 
     * @param anchor
     *     The anchor
     */
    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The mobileUrl
     */
    public String getMobileUrl() {
        return mobileUrl;
    }

    /**
     * 
     * @param mobileUrl
     *     The mobileUrl
     */
    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }

}
