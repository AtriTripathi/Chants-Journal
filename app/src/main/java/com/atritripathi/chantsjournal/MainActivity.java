package com.atritripathi.chantsjournal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
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
    }
}
