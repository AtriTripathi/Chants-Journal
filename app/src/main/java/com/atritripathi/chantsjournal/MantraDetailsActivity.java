package com.atritripathi.chantsjournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MantraDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantra_details);

        final EditText mantraEditText = findViewById(R.id.et_enter_mantra);
        Button saveButton = findViewById(R.id.save_mantra_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mantraEditText.getText().toString().trim().equals("")) {
                    mantraEditText.setError("Mantra is required!");
                } else {
                    Intent intent = new Intent(MantraDetailsActivity.this, MainActivity.class);
                    intent.putExtra(Intent.EXTRA_RETURN_RESULT, mantraEditText.getText().toString().trim());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}