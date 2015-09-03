package com.example.c4q_ac35.espy;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import butterknife.ButterKnife;

public class UserInitalSetActivity extends PreferenceActivity {

//    @Bind(R.id.nameText) TextInputLayout nameTxt;
//    @Bind(R.id.firstSubmit) Button submitBt;

    String userName;
    String hAddress;
    String wAddress;
    String sAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_setup);
        ButterKnife.bind(this);
        getUserInput();
        getIntent();
      //  PreferenceA(R.xml.user_settings_layout);
        PreferenceManager.setDefaultValues(this, R.xml.user_settings_layout, false);


    }

    private void getUserInput() {
    //   userName = nameTxt.getEditText().toString();

    }


}
