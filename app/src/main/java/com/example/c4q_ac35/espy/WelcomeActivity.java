package com.example.c4q_ac35.espy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {

    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.signup_button)
    Button signupButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(loginIntent);
            }
        });

//        signupButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent signUpIntent = new Intent (getApplicationContext(),UserSignUpActivity.class);
//                startActivity(signUpIntent);
//            }
//        });


//Todo: add vanice's splash@@@

//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        boolean firstOpened = preferences.getBoolean("first_opened", true);
//        if(firstOpenened) {
//            //showWelcomeScreen();
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putBoolean("first_opened", false);
//            editor.apply();
//        }
//

    }

}
