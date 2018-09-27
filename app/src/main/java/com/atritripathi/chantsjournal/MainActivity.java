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

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int MANTRA_DETAILS_ACTIVITY_REQUEST_CODE = 0;

    private MantraViewModel mMantraViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View initBuddhaView = findViewById(R.id.init_buddha_view);
        ImageView buddhaTranslucent = findViewById(R.id.buddha_always_bg);

        final MantraAdapter mMantraAdapter = new MantraAdapter(this);


        RecyclerView mRecyclerView = findViewById(R.id.rv_mantras);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Checks whether list is empty and then displays the initial buddha image
        mMantraAdapter.registerAdapterDataObserver(new MantraListEmptyObserver(mRecyclerView,initBuddhaView,buddhaTranslucent));

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (MANTRA_DETAILS_ACTIVITY_REQUEST_CODE): {
                if (resultCode == RESULT_OK) {

                    String mantraName = data.getStringExtra("mantra_name");
                    String malasCount = data.getStringExtra("malas_count");
                    String startDate = data.getStringExtra("start_date");
                    String endDate = data.getStringExtra("end_date");
                    String notes = data.getStringExtra("notes");
                    int totalDays = data.getIntExtra("total_days", 1);
                    int chantsPerDay = data.getIntExtra("chants_per_day", 1);

                    Mantra mantra = new Mantra(mantraName, malasCount, startDate, endDate, notes, totalDays, chantsPerDay);
                    mMantraViewModel.insert(mantra);
                }
            }
        }
    }

}
