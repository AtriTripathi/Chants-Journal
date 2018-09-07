package com.atritripathi.chantsjournal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final static String LOG_TAG = "MainActivity";
    private static final int MANTRA_DETAILS_ACTIVITY_REQUEST_CODE = 0;

    private RecyclerView mRecyclerView;
    private ArrayList<String> mMantraList;
    private MantraAdapter mantraAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMantraList = new ArrayList<>();
        mMantraList.add("Aum Namah Shivaya");
        mMantraList.add("Aum Namah Shivaya");
        mMantraList.add("Aum Namah Shivaya");
        mMantraList.add("Aum Namah Shivaya");
        mMantraList.add("Aum Namah Shivaya");
        mMantraList.add("Aum Namah Shivaya");
        mMantraList.add("Aum Namah Shivaya");
        mMantraList.add("Aum Namah Shivaya");
        mMantraList.add("Aum Namah Shivaya");

        linearLayoutManager = new LinearLayoutManager(this);
        mantraAdapter = new MantraAdapter(mMantraList);

        mRecyclerView = findViewById(R.id.rv_mantras);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mantraAdapter);


        Button addMantraButton = findViewById(R.id.add_mantra_button);
        addMantraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MantraDetailsActivity.class);
                startActivityForResult(intent, MANTRA_DETAILS_ACTIVITY_REQUEST_CODE);
                Log.v(LOG_TAG, "onClick completed successfully");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (MANTRA_DETAILS_ACTIVITY_REQUEST_CODE): {
                if(resultCode == Activity.RESULT_OK) {
                    String mantraName = data.getStringExtra("mantra_name");
                    mMantraList.add(mantraName);
                    mantraAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
