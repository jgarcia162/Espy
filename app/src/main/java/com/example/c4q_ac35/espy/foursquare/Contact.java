
package com.example.c4q_ac35.espy.foursquare;


import com.j256.ormlite.field.DatabaseField;

public class Contact {

    @DatabaseField(canBeNull = true, foreign = true)
    public String phone;
    public String twitter;
    public String facebook;
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
