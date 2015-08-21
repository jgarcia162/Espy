package com.example.c4q_ac35.espy;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserInitalSetActivity extends AppCompatActivity {

    @Bind(R.id.nameText) TextInputLayout nameTxt;
    @Bind(R.id.homeText) TextInputLayout homeTxt;
    @Bind(R.id.workText) TextInputLayout workTxt;
    @Bind(R.id.schoolText) TextInputLayout schoolTxt;
    @Bind(R.id.firstSubmit) Button submitBt;

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

    }

    private void getUserInput() {
       userName = nameTxt.getEditText().toString();
       hAddress = homeTxt.getEditText().toString();
       wAddress = workTxt.getEditText().toString();
       sAddress = schoolTxt.getEditText().toString();
    }







}
