package com.atritripathi.chantsjournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private TextView profileName;
    private ImageView profileImage;
    private FirebaseAuth auth = null;
    private FirebaseUser user = null;

    private List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.GoogleBuilder().build(),
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.PhoneBuilder().build());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileName = findViewById(R.id.tv_user_name);
        profileImage = findViewById(R.id.iv_user_profile);

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String name = user.getDisplayName();
        Toast.makeText(SettingsActivity.this,user.getDisplayName(),Toast.LENGTH_SHORT).show();
        if (auth.getCurrentUser() != null) {
            profileName.setText(name);
//            profileImage.setImageURI(user.getPhotoUrl());
//            signOut();
        }
//        if (!isUserLogin()) {
//            signOut();
//        }
        setContentView(R.layout.activity_settings);


        if (auth != null) {
//            profileName.setText(auth.getCurrentUser().getDisplayName());
        }


        TextView loginButton = findViewById(R.id.button_signin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),
                        RC_SIGN_IN
                );
            }
        });

        TextView logoutButton = findViewById(R.id.button_signout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance()
                        .signOut(SettingsActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    signOut();
                                } else {
                                    displayMessage("Sign Out Error!");
                                }
                            }
                        });
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
                                        displayMessage("Account removed");
                                    } else {
                                        displayMessage("Account removal error");
                                    }
                                }
                            });
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String name = user.getDisplayName();
                Toast.makeText(this,name,Toast.LENGTH_SHORT).show();
                // ...
            } else if (resultCode == RESULT_CANCELED){
                Toast.makeText(this,"Signin failed",Toast.LENGTH_SHORT).show();

//                    // Sign in failed. If response is null the user canceled the
//                    // sign-in flow using the back button. Otherwise check
//                    // response.getError().getErrorCode() and handle the error.
//                    // ...
//                if (response != null) {
//                    response.getError().getErrorCode();
//                }
            } else {
                Toast.makeText(this,"Unknown Response",Toast.LENGTH_SHORT).show();
            }
        }
    }

    //    private boolean isUserLogin(){
//        if(auth.getCurrentUser() != null){
//            return true;
//        }
//        return false;
//    }
    private void signOut() {
        Intent signOutIntent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(signOutIntent);
        finish();
    }

    private void displayMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void displayLoginUserProfileName() {
        FirebaseUser mUser = auth.getCurrentUser();
        if (mUser != null) {
            profileName.setText(TextUtils.isEmpty(mUser.getDisplayName()) ? "No name found" : mUser.getDisplayName());
        }
    }

//    public void createSignInIntent() {
//        // [START auth_fui_create_intent]
//        // Choose authentication providers
////        List<AuthUI.IdpConfig> providers = Arrays.asList(
////                new AuthUI.IdpConfig.PhoneBuilder().build(),
////                new AuthUI.IdpConfig.GoogleBuilder().build());
//
//        // Create and launch sign-in intent
//        startActivityForResult(
//                AuthUI.getInstance()
//                        .createSignInIntentBuilder()
//                        .setAvailableProviders(providers)
//                        .build(),
//                RC_SIGN_IN);
//        // [END auth_fui_create_intent]
//    }
}
