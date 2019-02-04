package com.atritripathi.chantsjournal;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int MANTRA_DETAILS_ACTIVITY_REQUEST_CODE = 0;
    private static final int RC_SIGN_IN = 123;

    private MantraViewModel mMantraViewModel;
    private MantraAdapter mMantraAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To show the translucent Buddha in the background, appropriately.
        View initBuddhaView = findViewById(R.id.init_buddha_view);
        ImageView buddhaTranslucent = findViewById(R.id.buddha_always_bg);

        RecyclerView mRecyclerView = findViewById(R.id.rv_mantras);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mMantraAdapter = new MantraAdapter(this);

        // Checks whether list is empty and then displays the initial buddha image
        mMantraAdapter.registerAdapterDataObserver(new MantraListEmptyObserver(mRecyclerView, initBuddhaView, buddhaTranslucent));
        mRecyclerView.setAdapter(mMantraAdapter);

        // Get a new or existing ViewModel from the ViewModelProvider.
        mMantraViewModel = ViewModelProviders.of(this).get(MantraViewModel.class);

        // Add an observer on the LiveData. The onChanged() method fires when the observed data
        // changes and the activity is in the foreground.
        mMantraViewModel.getAllMantras().observe(this, new Observer<List<Mantra>>() {
            @Override
            public void onChanged(@Nullable List<Mantra> mantras) {
                mMantraAdapter.setMantras(mantras);
            }
        });

        final Button addMantraButton = findViewById(R.id.add_mantra_button);
        addMantraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewMantraActivity.class);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, MANTRA_DETAILS_ACTIVITY_REQUEST_CODE);
                }
            }
        });
    }

    public void createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]
    }


    /**
     * Used to get the mantra details from the NewMantraActivity and consequently store it
     * in the database.
     *
     * @param requestCode Used to make sure that the result is returned from the correct activity.
     * @param resultCode  Used to verify that a valid mantra has been added or not.
     * @param data        This is the intent from which mantra data is extracted to be added to database.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (MANTRA_DETAILS_ACTIVITY_REQUEST_CODE): {
                if (resultCode == RESULT_OK) {
                    String mantraName = data.getStringExtra("mantra_name");
                    int totalMalas = data.getIntExtra("total_malas", 0);
                    Mantra mantra = new Mantra(mantraName, totalMalas);
                    mMantraViewModel.insert(mantra);
                }
            }
            case (RC_SIGN_IN): {
                IdpResponse response = IdpResponse.fromResultIntent(data);
                if (resultCode == RESULT_OK) {
                    // Successfully signed in
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    // ...
                } else {
                    // Sign in failed. If response is null the user canceled the
                    // sign-in flow using the back button. Otherwise check
                    // response.getError().getErrorCode() and handle the error.
                    // ...
                    if (response != null) {
                        response.getError().getErrorCode();
                    }

                }
            }
        }
    }
}

