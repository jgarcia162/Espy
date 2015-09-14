package com.example.c4q_ac35.espy;

import java.util.Date;

/**
 * Created by c4q-ac35 on 9/8/15.
 */
public class GeofenceNotificationsHistory {
    private Date notifiedAt;
    private String geofenceTransitionDetails;
    public Date getNotifiedAt() {
        return notifiedAt;
    }
    public void setNotifiedAt(Date notifiedAt) {
        this.notifiedAt = notifiedAt;
    }
    public String getGeofenceTransitionDetails() {
        return geofenceTransitionDetails;
    }
    public void setGeofenceTransitionDetails(String geofenceTransitionDetails) {
        this.geofenceTransitionDetails = geofenceTransitionDetails;
    }
}