package com.example.c4q_ac35.espy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    @Bind(R.id.email)
    TextInputLayout emailEditText;
    @Bind(R.id.passwordText)
    TextInputLayout passwordEditText;
    @Bind(R.id.login_button)
    Button login_button;
    @Bind(R.id.signup_button)
    Button signup_button;
    @Bind(R.id.googleLoginButton)
    Button googleButton;
    @Bind(R.id.twitterLoginButton)
    Button twitterButton;
    @Bind(R.id.facebookLoginButton)
    Button facebookButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(getApplicationContext(), UserSignUpActivity.class);
                startActivity(signUpIntent);
            }
        });

    }

    private Boolean login() {
        Boolean validLogin = true;
        String userEmail = emailEditText.getEditText().toString();
        String password = passwordEditText.getEditText().toString();

        if (userEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            validLogin = false;
            emailEditText.setErrorEnabled(true);
            emailEditText.setError("Please enter a valid email address. (e.g name@email.com)");

        } else {
            emailEditText.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 15) {
            validLogin = false;
            passwordEditText.setErrorEnabled(true);
            passwordEditText.setError("Password must contain at least 6 characters.");
        } else {
            passwordEditText.setError(null);
        }

        return validLogin;
    }


//    private void login() {
////        String username = usernameEditText.getText().toString().trim();
////        String password = passwordEditText.getText().toString().trim();
//
//
//        // Validate the log in data
//        boolean validationError = false;
//        StringBuilder validationErrorMessage = new StringBuilder(getString(R.string.error_intro));
//        if (username.length() == 0) {
//            validationError = true;
//            validationErrorMessage.append(getString(R.string.error_blank_username));
//        }
//        if (password.length() == 0) {
//            if (validationError) {
//                validationErrorMessage.append(getString(R.string.error_join));
//            }
//            validationError = true;
//            validationErrorMessage.append(getString(R.string.error_blank_password));
//        }
//        validationErrorMessage.append(getString(R.string.error_end));
//
//
//        // If there is a validation error, display the error
//        if (validationError) {
//            Toast.makeText(LoginActivity.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
//                    .show();
//            return;
//        }
//    }

    // Set up a progress dialog
//    final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
//    dialog.setMessage(getString(R.string.progress_login));
//    dialog.show();
//    // Call the Parse login method
//    ParseUser.logInInBackground(username, password, new LogInCallback() {
//        @Override
//        public void done(ParseUser user, ParseException e) {
//            dialog.dismiss();
//            if (e != null) {
//                // Show the error message
//                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//            } else {
//                // Start an intent for the dispatch activity
//                Intent intent = new Intent(LoginActivity.this, DispatchActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//            }
//        }
//    });
//}
}
