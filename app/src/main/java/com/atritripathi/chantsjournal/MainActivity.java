package com.atritripathi.chantsjournal;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final static String LOG_TAG = "MainActivity";

    private final static String MANTRA_FRAGMENT_TAG = "New Mantra Entry";

    private RecyclerView mRecyclerView;
    private ArrayList<String> mMantraList;

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
        mMantraList.add("Aum Namah Shivaya");
        mMantraList.add("Aum Namah Shivaya");
        mMantraList.add("Aum Namah Shivaya");
        mMantraList.add("Aum Namah Shivaya");
        mMantraList.add("Aum Namah Shivaya");


        mRecyclerView = findViewById(R.id.rv_mantras);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MantraAdapter(mMantraList));


        Button addMantraButton = findViewById(R.id.add_mantra_button);
        addMantraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.mantra_frag_placeholder,new MantraFragment(),MANTRA_FRAGMENT_TAG);
                fragmentTransaction.commit();
                Log.v(LOG_TAG, "onClick completed successfully");

            }
        });

        final MantraFragment.OnMantraEnteredListener mantraEnteredListener = new MantraFragment.OnMantraEnteredListener() {
            @Override
            public void onMantraEntered(String mantra) {
                //Toast.makeText(MainActivity.this,"Done",Toast.LENGTH_SHORT).show();
            }
        };


        mantraEnteredListener.onMantraEntered("Aum");

    }




}
