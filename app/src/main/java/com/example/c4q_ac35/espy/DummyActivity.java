package com.example.c4q_ac35.espy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by c4q-ac35 on 9/12/15.
 */
public class DummyActivity extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Now finish, which will drop the user in to the activity that was at the top
            //  of the task stack
            finish();
        }
}

