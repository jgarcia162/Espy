package com.example.c4q_ac35.espy;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;

public class UserSignUpActivity extends AppCompatActivity {
    @BindView(R.id.userName)
    TextInputLayout userName;

    @BindView(R.id.passwordText)
    TextInputLayout passwordText;

    @BindView(R.id.confirmpasswordText)
    TextInputLayout confirmPasswordText;

    @BindView(R.id.firstSubmit)
    Button submitbt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_layout);
//
//        confirmPasswordText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == R.id.edittext_action_signup ||
//                        actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
//                    signup();
//                    return true;
//                }
//                return false;
//            }
//        });


        // Set up the submit button click handler
        submitbt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                signup();
            }
        });
    }


    private void signup() {
        String username = userName.getEditText().toString().trim();
        String password = passwordText.getEditText().toString().trim();
        String passwordAgain = confirmPasswordText.getEditText().toString().trim();

    }

//    // Validate the sign up data
//    boolean validationError = false;
//    StringBuilder validationErrorMessage = new StringBuilder(getString(R.string.error_intro));
//    if (username.length() == 0) {
//        validationError = true;
//        validationErrorMessage.append(getString(R.string.error_blank_username));
//    }
//    if (password.length() == 0) {
//        if (validationError) {
//            validationErrorMessage.append(getString(R.string.error_join));
//        }
//        validationError = true;
//        validationErrorMessage.append(getString(R.string.error_blank_password));
//    }
//    if (!password.equals(passwordAgain)) {
//        if (validationError) {
//            validationErrorMessage.append(getString(R.string.error_join));
//        }
//        validationError = true;
//        validationErrorMessage.append(getString(R.string.error_mismatched_passwords));
//    }
//    validationErrorMessage.append(getString(R.string.error_end));
//
//
//    // If there is a validation error, display the error
//    if (validationError) {
//        Toast.makeText(SignUpActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
//                .show();
//        return;
//    }
//
//
//    // Set up a progress dialog
//    final ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);
//    dialog.setMessage(getString(R.string.progress_signup));
//    dialog.show();
//
//
//    // Set up a new Parse user
//    ParseUser user = new ParseUser();
//    user.setUsername(username);
//    user.setPassword(password);
//
//
//    // Call the Parse signup method
//    user.signUpInBackground(new SignUpCallback() {
//        @Override
//        public void done(ParseException e) {
//            dialog.dismiss();
//            if (e != null) {
//                // Show the error message
//                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//            } else {
//                // Start an intent for the dispatch activity
//                Intent intent = new Intent(SignUpActivity.this, DispatchActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//        }
//    });


}
