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
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int MANTRA_DETAILS_ACTIVITY_REQUEST_CODE = 0;

    private MantraViewModel mMantraViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final MantraAdapter mMantraAdapter = new MantraAdapter(this);

        RecyclerView mRecyclerView = findViewById(R.id.rv_mantras);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mMantraAdapter);


        // Get a new or existing ViewModel from the ViewModelProvider.
        mMantraViewModel = ViewModelProviders.of(this).get(MantraViewModel.class);


        // Add an observer on the LiveData. The onChanged() method fires when the observed data
        // changes and the activity is in the foreground.
        mMantraViewModel.getAllWords().observe(this, new Observer<List<Mantra>>() {
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

//    LocalDate date = LocalDate.now();
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
//    String text = date.format(formatter);
//    LocalDate parsedDate = LocalDate.parse(text, formatter);


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (MANTRA_DETAILS_ACTIVITY_REQUEST_CODE): {
                if (resultCode == RESULT_OK) {
                    //Toast.makeText(this, data.getStringExtra("mantra_name"), Toast.LENGTH_SHORT).show();
//                    String startDateString = data.getStringExtra("start_date");
//                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//                    try {
//                        Date startDate = df.parse(startDateString);  // Conversion from String
//                        String newDateString = df.format(startDate);
//                        Calendar cal = GregorianCalendar.getInstance();
//                        cal.setTime(startDate);
//                        cal.add(GregorianCalendar.MONTH,5);
//                        Toast.makeText(this, df.format(cal.getTime()), Toast.LENGTH_SHORT).show();
//
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }

//                    Toast.makeText(this, data.getStringExtra("start_date"), Toast.LENGTH_SHORT).show();

                    String mantraName = data.getStringExtra("mantra_name");
                    String malasCount = data.getStringExtra("malas_count");
                    String startDate = data.getStringExtra("start_date");
                    String endDate = data.getStringExtra("end_date");
                    String notes = data.getStringExtra("notes");

                    Mantra mantra = new Mantra(mantraName, malasCount, startDate, endDate, notes);
                    mMantraViewModel.insert(mantra);
                } else {
                    Toast.makeText(this, "Mantra not saved", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    Date dateObjectConverter(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date dateObject = null;
        try {
            dateObject = dateFormat.parse(dateString);  // Conversion from String
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateObject;
    }

}
