package com.atritripathi.chantsjournal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewMantraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mantra);

        overridePendingTransition(R.anim.fade_in_layout,R.anim.fade_out_layout);

        final EditText mantraEditText = findViewById(R.id.et_new_mantra);
        final TextInputEditText malasEditText = findViewById(R.id.et_total_malas);
        final Button saveButton = findViewById(R.id.done_chanting_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mantraEditText.getText().toString().trim().equals("")
                        || malasEditText.getEditableText().toString().trim().equals("")) {
                    mantraEditText.setError("Mantra Name Required");
                    malasEditText.setError("Number of Malas Required");
                } else {
                    Intent intent = new Intent(NewMantraActivity.this, MainActivity.class);
                    intent.putExtra("mantra_name", mantraEditText.getText().toString().trim());
                    intent.putExtra("total_malas",Integer.parseInt(malasEditText.getEditableText().toString().trim()));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}


