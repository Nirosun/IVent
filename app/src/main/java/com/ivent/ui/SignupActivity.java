package com.ivent.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivent.R;
import com.ivent.entities.adapter.BuildEntities;
import com.ivent.entities.adapter.CreateEntities;

import java.io.FileNotFoundException;


/**
 * A login screen that offers login via email/password.
 */
public class SignupActivity extends ActionBarActivity {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private static final String TAG = "debug";

    private UserSigninTask mAuthTask = null;
    String uriStr;
    // UI references.
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private EditText nameEditText;
    private Button cameraButton;
    private Button uploadImageButton;
    private Button mEmailSignInButton;
    private ImageView uploadImageView;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // Set up the login form.
        nameEditText = (EditText) findViewById(R.id.name_EditText);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        nameEditText = (EditText) findViewById(R.id.name_EditText);
        mPasswordView = (EditText) findViewById(R.id.password);
        cameraButton = (Button) findViewById(R.id.cameraButton);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        uploadImageView = (ImageView) findViewById(R.id.uploadImageView);
        uploadImageButton = (Button) findViewById(R.id.uploadImageButton);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptSignup();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignup();
            }
        });

        cameraButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });

        uploadImageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (data != null) {
                Uri uri = data.getData();
                uriStr = uri.toString();
                ContentResolver cr = this.getContentResolver();
                try {
                    System.out.println(uri);
                    bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                    if (bitmap == null) {
                        System.out.println("bitmap is null");
                        return;
                    }
                    uploadImageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException fe) {
                    fe.printStackTrace();
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * Attempts to sign up the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSignup() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        nameEditText.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String name = nameEditText.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(name)) {
            nameEditText.setError(getString(R.string.error_field_required));
            focusView = nameEditText;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserSigninTask(name, password);
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserSigninTask extends AsyncTask<Void, Void, Boolean> {

        private final String name;
        private final String mPassword;

        UserSigninTask(String name, String password) {
            this.name = name;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            CreateEntities creator = new BuildEntities(getApplicationContext());
            creator.createUser(name, mPassword, uriStr);
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("password", mPassword);
                bundle.putString("photo", uriStr);

                Intent intent = new Intent(SignupActivity.this, CategoryListActivity.class);
                intent.putExtra("user", name);
                intent.putExtra("bundle", bundle);
                startActivity(intent);

                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }


}

