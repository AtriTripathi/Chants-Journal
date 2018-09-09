package com.atritripathi.chantsjournal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class NewMantraActivity extends AppCompatActivity {

    private final Calendar mCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mantra);

        final EditText mantraEditText = findViewById(R.id.et_enter_mantra);
        Button saveButton = findViewById(R.id.save_mantra_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mantraEditText.getText().toString().trim().equals("")) {
                    mantraEditText.setError("Mantra is required!");
                } else {
                    Intent intent = new Intent(NewMantraActivity.this, MainActivity.class);
                    intent.putExtra(Intent.EXTRA_RETURN_RESULT, mantraEditText.getText().toString().trim());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        final EditText startDateEditText = findViewById(R.id.et_start_date);
        final EditText endDateEditText = findViewById(R.id.et_end_date);

        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(startDateEditText);
            }
        });

        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(endDateEditText);
            }
        });
    }

    
    /**
     * Helper function to set the date for the respective editFields.
     * @param dateEditText is the passed editText field.
     */
    public void setDate(final EditText dateEditText) {
        int mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        int mMonth = mCalendar.get(Calendar.MONTH);
        int mYear = mCalendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(NewMantraActivity.this,
                R.style.CalendarTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateEditText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, mDay, mMonth, mYear);
        datePickerDialog.updateDate(mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}

