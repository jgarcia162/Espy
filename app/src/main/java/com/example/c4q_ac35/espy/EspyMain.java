package com.example.c4q_ac35.espy;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;


public class EspyMain extends ActionBarActivity {

    TextView mTextView,mTesting;
    ArrayList<String> mArrayList;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espy_main);

        mTextView = (TextView) findViewById(R.id.textView);
        mTesting = (TextView) findViewById(R.id.testing);
        mListView = (ListView) findViewById(R.id.list_view);
        mArrayList = new ArrayList<>();

        mArrayList.add("One");
        mArrayList.add("Two");
        mArrayList.add("Three");


        // TESTING FIREBASE CONNECTION
        Firebase.setAndroidContext(this);

        Firebase myFireBase = new Firebase("https://blinding-torch-1145.firebaseio.com/");

        final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,mArrayList);
        myFireBase.child("List View").setValue(mArrayList);

        myFireBase.child("List View").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mArrayList = (ArrayList<String>) dataSnapshot.getValue();
                mListView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mArrayList.add("Four");
        mArrayList.add("Five");
        mArrayList.remove(1);
        //update child by using setValue
        myFireBase.child("List View").setValue(mArrayList);

        myFireBase.child("message").child("second message").child("third message").setValue("wow another message");
        myFireBase.child("message").child("second message").child("fourth message").setValue("OMG ANOTHER ONE!");
        myFireBase.child("second child").setValue("this is a second child of Espy");
        // adding a child to an object replaces that object's value with that child
        myFireBase.child("second child").child("second child message").setValue("this is a child of the second child");

        // must follow tree to retrieve specific data
        myFireBase.child("message").child("second message").child("fourth message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mTesting.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_espy_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
