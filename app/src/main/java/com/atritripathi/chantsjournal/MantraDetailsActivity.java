package com.atritripathi.chantsjournal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class MantraDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantra_details);

        final TextView mantraName = findViewById(R.id.tv_mantra_name);
        final TextView totalDays = findViewById(R.id.tv_details_total_days);
        final TextView chantsPerDay = findViewById(R.id.tv_details_chants_per_day);

        final int position = getIntent().getIntExtra("mantra_position",1);
        Toast.makeText(MantraDetailsActivity.this,String.valueOf(position),Toast.LENGTH_SHORT).show();


        new Thread(new Runnable() {
            @Override
            public void run() {
                Mantra mantra = MantraDatabase.getDatabase(MantraDetailsActivity.this).mantraDao().getMantra(position);
                mantraName.setText(mantra.getMantraName());
                totalDays.setText(mantra.getTotalDays());
                chantsPerDay.setText(mantra.getChantsPerDay());

            }
        }).start();
    }
}
