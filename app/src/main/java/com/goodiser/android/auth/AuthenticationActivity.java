package com.goodiser.android.auth;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.goodiser.android.app.FeedActivity;
import com.goodiser.android.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth = null;

    // UI Elements
    private EditText mEmailView = null;
    private EditText mPasswordView = null;
    private Button mSignInButton = null;
    private ProgressBar mSignInProgress = null;
    private ImageView mNotificationView = null;

    // User Data
    protected String EMAIL = null;
    protected String PASSWORD = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        mAuth = FirebaseAuth.getInstance();

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mSignInProgress = (ProgressBar) findViewById(R.id.signin_progress);
        mNotificationView = (ImageView) findViewById(R.id.notification_view);

    }

    @Override
    public void onStart() {
        super.onStart();


    }



    private boolean isEmailValid(String email) {
        return !email.isEmpty() && email.contains("@");
    }
    private boolean isPasswordValid(String password) {
        return !password.isEmpty() && password.length() > 8;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void signIn(View view) {


        EMAIL = mEmailView.getText().toString();
        PASSWORD = mPasswordView.getText().toString();

        mSignInProgress.setVisibility(View.VISIBLE);
        mSignInButton.setVisibility(View.GONE);


        if (this.isEmailValid(EMAIL) && this.isPasswordValid(PASSWORD)) {

            hideSoftKeyboard(this);

            mAuth.signInWithEmailAndPassword(EMAIL, PASSWORD).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        FirebaseUser user = mAuth.getCurrentUser();

                        Intent intent = new Intent(AuthenticationActivity.this, FeedActivity.class);
                        startActivity(intent);

                    } else {

                        mNotificationView.setVisibility(View.VISIBLE);

                        Toast.makeText(AuthenticationActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        mSignInProgress.setVisibility(View.GONE);
                        mSignInButton.setVisibility(View.VISIBLE);

                    }

                }
            });

        } else {

            //Toast.makeText(AuthenticationActivity.this, "Please enter your email and password.", Toast.LENGTH_SHORT).show();

            mNotificationView.setVisibility(View.VISIBLE);

            mSignInProgress.setVisibility(View.GONE);
            mSignInButton.setVisibility(View.VISIBLE);

        }

    }










}
