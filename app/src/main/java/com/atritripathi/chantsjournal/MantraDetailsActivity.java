package com.atritripathi.chantsjournal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.thekhaeng.pushdownanim.PushDownAnim;

public class MantraDetailsActivity extends AppCompatActivity {

    private int malasCompleted = 0;
    private int chantsPerDayPlaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantra_details);

        final TextView mantraName = findViewById(R.id.tv_mantra_name);
        final TextView totalDays = findViewById(R.id.tv_details_total_days);
        final TextView chantsPerDay = findViewById(R.id.tv_details_chants_per_day);
        final TextView count = findViewById(R.id.tv_count);
        final Button addButton = findViewById(R.id.button_add);
        final Button deleteButton = findViewById(R.id.button_delete);

        final int position = getIntent().getIntExtra("mantra_position", 1);
        Toast.makeText(MantraDetailsActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();


        new Thread(new Runnable() {
            @Override
            public void run() {
                Mantra mantra = MantraDatabase.getDatabase(MantraDetailsActivity.this).mantraDao().getMantra(position);
                mantraName.setText(mantra.getMantraName());
                totalDays.setText(mantra.getTotalDays() + " Days");
                chantsPerDay.setText(mantra.getChantsPerDay() + " Malas");
                chantsPerDayPlaceHolder = mantra.getChantsPerDay();
                count.setText(malasCompleted + " of "+ chantsPerDayPlaceHolder + " Malas Completed");
            }
        }).start();

        PushDownAnim.setPushDownAnimTo( addButton, deleteButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (malasCompleted < chantsPerDayPlaceHolder) {
                    malasCompleted++;
                }
                count.setText(malasCompleted + " of "+ chantsPerDayPlaceHolder + " Malas Completed");
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (malasCompleted > 0) {
                    malasCompleted--;
                }
                count.setText(malasCompleted + " of "+ chantsPerDayPlaceHolder + " Malas Completed");
            }
        });

    }
}
