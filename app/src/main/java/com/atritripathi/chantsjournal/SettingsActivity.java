package com.atritripathi.chantsjournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = "SettingsActivity";

    private static final int RC_SIGN_IN = 123;
    private TextView profileName;
    private ImageView profileImage;
    private FirebaseAuth auth;

    private List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.GoogleBuilder().build(),
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.PhoneBuilder().build());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        overridePendingTransition(R.anim.fade_in_layout,R.anim.fade_out_layout);

        profileName = findViewById(R.id.tv_user_name);
        profileImage = findViewById(R.id.iv_user_profile);
        auth = FirebaseAuth.getInstance();

        updateProfileData();

        TextView loginButton = findViewById(R.id.button_signin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isUserLoggedIn()) {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(providers)
                                    .setTheme(R.style.AuthTheme)
                                    .setLogo(R.drawable.buddha_transparent)
                                    .setTosAndPrivacyPolicyUrls(
                                            "https://github.com/AtriTripathi/Privacy-Policies-" +
                                                    "Terms-and-Conditions/blob/master/Chants%20Journal" +
                                                    "/Terms%20%26%20Conditions",
                                            "https://github.com/AtriTripathi/Privacy-Policies-" +
                                                    "Terms-and-Conditions/blob/master/Chants%20Journal/Privacy%20Policy")
                                    .build(),
                            RC_SIGN_IN
                    );
                } else {
                    displayMessage("Already Signed In");
                }
            }
        });


        TextView logoutButton = findViewById(R.id.button_signout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUserLoggedIn()) {
                    AuthUI.getInstance()
                            .signOut(SettingsActivity.this)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        signOut();
                                        displayMessage("Signed Out");
                                    } else {
                                        displayMessage("Sign Out Error!");
                                    }
                                }
                            });
                } else {
                    displayMessage("No account added");
                }
            }
        });


        TextView deleteUserButton = findViewById(R.id.button_remove_account);
        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (auth.getCurrentUser() != null) {
                    auth.getCurrentUser()
                            .delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        signOut();
                                        displayMessage("Account removed successfully");
                                    } else {
                                        displayMessage("Account removal error, try again.");
                                    }
                                }
                            });
                } else {
                    displayMessage("No account added");
                }
            }
        });

        TextView exitApp = findViewById(R.id.exit_app);
        exitApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }


    private void updateProfileData() {
        if (auth.getCurrentUser() != null) {
            Picasso.get()
                    .load(auth.getCurrentUser().getPhotoUrl())
                    .placeholder(R.drawable.monk_user_icon)
                    .error(R.drawable.monk_user_icon)
                    .into(profileImage);
            profileName.setText(auth.getCurrentUser().getDisplayName());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                updateProfileData();
                displayMessage("Signed in");
            } else if (resultCode == RESULT_CANCELED) {
                if (response != null) {
                    response.getError().getErrorCode();
                }
                displayMessage("Sign in canceled");
            } else {
                displayMessage("Unknown Response");
            }
        }
    }

    private boolean isUserLoggedIn() {
        if (auth.getCurrentUser() != null) {
            return true;
        }
        return false;
    }

    private void signOut() {
        Intent signOutIntent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(signOutIntent);
        finish();
    }

    private void displayMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
