package com.atritripathi.chantsjournal;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NewMantraActivity extends AppCompatActivity {

    private static final String TAG = "NewMantraActivity";

    private boolean startFlag, endFlag;

    private TextInputEditText startDatePlaceHolder;
    private TextInputEditText endDatePlaceHolder;
    private TextInputEditText malasPlaceHolder;
    private int malasPerDayPlaceHolder;
    private int totalDaysPlaceHolder;

    private final Calendar mCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mantra);

        final EditText mantraEditText = findViewById(R.id.et_new_mantra);
        final TextInputEditText malasEditText = findViewById(R.id.et_total_malas);
        final TextInputEditText startDateEditText = findViewById(R.id.et_start_date);
        final TextInputEditText endDateEditText = findViewById(R.id.et_end_date);
        final TextInputEditText notesEditText = findViewById(R.id.et_notes);
        final TextView totalDays = findViewById(R.id.tv_total_days);
        final TextView chantsPerDay = findViewById(R.id.tv_chants_per_day);
        final Button saveButton = findViewById(R.id.save_mantra_button);

        startDatePlaceHolder = startDateEditText;
        endDatePlaceHolder = endDateEditText;
        malasPlaceHolder = malasEditText;


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mantraEditText.getText().toString().trim().equals("")
                        || malasEditText.getEditableText().toString().trim().equals("")
                        || startDateEditText.getEditableText().toString().trim().equals("")
                        || endDateEditText.getEditableText().toString().trim().equals("")) {
                    mantraEditText.setError("Mantra Name Required");
                    malasEditText.setError("Number of Malas Required");
                    startDateEditText.setError("Start Date Required");
                    endDateEditText.setError("End Date Required");
                } else {
                    Intent intent = new Intent(NewMantraActivity.this, MainActivity.class);
                    intent.putExtra("mantra_name", mantraEditText.getText().toString().trim());
                    intent.putExtra("malas_count", malasEditText.getEditableText().toString().trim());
                    intent.putExtra("start_date", startDateEditText.getEditableText().toString().trim());
                    intent.putExtra("end_date", endDateEditText.getEditableText().toString().trim());
                    intent.putExtra("notes", notesEditText.getEditableText().toString().trim());
                    intent.putExtra("total_days", totalDaysPlaceHolder);
                    intent.putExtra("chants_per_day", malasPerDayPlaceHolder);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });


//        malasEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(!hasFocus) {
//                    endDateEntryChecker();
//                }
//            }
//        });

        malasEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    endDateEntryChecker();
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                    handled = true;
                }
                return handled;
            }
        });


        startDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFlag = true;
                setDate(startDateEditText);
            }
        });

        endDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endFlag = true;
                setDate(endDateEditText);
            }
        });

    }


    /**
     * Helper method to check whether the End Date has been entered.
     */
    public void endDateEntryChecker() {

        String startDate = startDatePlaceHolder.getEditableText().toString().trim();
        String endDate = endDatePlaceHolder.getEditableText().toString().trim();
        String malasCount = malasPlaceHolder.getEditableText().toString().trim();

        if (!endDate.equals("") && !startDate.equals("") && !malasCount.equals("")) {

            double daysCount = getDaysDiff(startDatePlaceHolder, endDatePlaceHolder);
            totalDaysPlaceHolder = (int) daysCount;
            String daysDiff = totalDaysPlaceHolder + " Days";

            TextView totalDays = findViewById(R.id.tv_total_days);
            totalDays.setText(daysDiff);

            double malas = Integer.parseInt(malasCount);
            malasPerDayPlaceHolder = (int) Math.ceil(malas / daysCount);
            String malasPerDay = malasPerDayPlaceHolder + " Malas";


            TextView chantsPerDay = findViewById(R.id.tv_chants_per_day);
            chantsPerDay.setText(malasPerDay);
        }
    }


    /**
     * Helper function to set the date for the respective editFields.
     *
     * @param dateEditText is the passed editText field.
     */
    public void setDate(final TextInputEditText dateEditText) {
        int mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        int mMonth = mCalendar.get(Calendar.MONTH);
        int mYear = mCalendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(NewMantraActivity.this,
                R.style.CalendarTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dateString = dayOfMonth + "/" + (month + 1) + "/" + year;

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String formattedDateString = null;
                try {
                    Date dateObject = dateFormat.parse(dateString);
                    formattedDateString = dateFormat.format(dateObject);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                dateEditText.setText(formattedDateString);
                endDateEntryChecker();
            }
        }, mDay, mMonth, mYear);

        if (!startDatePlaceHolder.getEditableText().toString().trim().equals("") && endFlag) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateParser(startDatePlaceHolder));
            cal.add(Calendar.DATE, 1);
            datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
            endFlag = false;
        }

        if (!endDatePlaceHolder.getEditableText().toString().trim().equals("") && startFlag) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateParser(endDatePlaceHolder));
            cal.add(Calendar.DATE, -1);
            datePickerDialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
            startFlag = false;
        }

        datePickerDialog.updateDate(mYear, mMonth, mDay);
        datePickerDialog.show();
        Log.i(TAG, "onDateSet: Both views set");

        endDateEntryChecker();
    }


    /**
     * Helper method to calculate the days betweeen given two dates
     *
     * @param startDate is the starting date
     * @param endDate   is the ending date
     * @return calculated days in string format
     */
    int getDaysDiff(TextInputEditText startDate, TextInputEditText endDate) {
        Date dateStart = dateParser(startDate);
        Date dateEnd = dateParser(endDate);

        long timeDiff = (dateEnd.getTime() - dateStart.getTime());
        double daysDiff = Math.ceil(timeDiff / (24 * 60 * 60 * 1000));

        return (int) daysDiff;
    }


    /**
     * Helper method to parse date from string to date format.
     *
     * @param dateEditText The EditText value from the date field
     * @return parsed date object.
     */
    public Date dateParser(TextInputEditText dateEditText) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date parsedDate = new Date();
        try {
            parsedDate = dateFormat.parse(dateEditText.getEditableText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDate;
    }
}


