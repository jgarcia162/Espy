package com.example.c4q_ac35.espy.db;

import android.content.Context;

import com.firebase.client.Firebase;
import com.firebase.geofire.GeoFire;

import java.util.List;

/**
 * Created by c4q-ac35 on 8/13/15.
 */
public class FirebaseDatabase extends Firebase{

   public Firebase myFireBase = new Firebase("https://espy.firebaseio.com/");

    public FirebaseDatabase(String url) {
        super(url);
    }

    public void addUser(String username){
        myFireBase.child(username);
    }

    public void saveToDoList(List<String> toDoList){
        myFireBase.child("username").child("username's to do list").setValue(toDoList);
    }

    public void saveDoneList(List<String> doneList){
        myFireBase.child("username").child("username's done list").setValue(doneList);
    }


}
