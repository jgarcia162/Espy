package com.example.c4q_ac35.espy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.lang.Override;
import java.lang.String;



public class MainActivity extends ActionBarActivity {

    private Button mButtonSearch;
    private EditText mEditTextZipCode;
    String zipCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonSearch = (Button) findViewById(R.id.search_button_final);
        mEditTextZipCode = (EditText) findViewById(R.id.zipcode_final);

        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                zipCode = mEditTextZipCode.getText().toString();

                SharedPreferences info;
                info = MainActivity.this.getSharedPreferences("PREFS_NAME", 0);
                SharedPreferences.Editor editor = info.edit();

                editor.putString("zipcode", zipCode);

                Intent intent = new Intent(MainActivity.this, SearchResultsActivity.class);
                    intent.putExtra("zipcode", zipCode);
                    startActivity(intent);

            }
        });



    }



}
