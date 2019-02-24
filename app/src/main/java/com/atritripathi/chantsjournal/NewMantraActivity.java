package com.atritripathi.chantsjournal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.thekhaeng.pushdownanim.PushDownAnim;

public class NewMantraActivity extends AppCompatActivity {

    private EditText mantraEditText;
    private TextInputEditText malasEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mantra);

        overridePendingTransition(R.anim.fade_in_layout,R.anim.fade_out_layout);

        CardView mantraNote = findViewById(R.id.cv_mantra_note);

        Animation mantraNoteAnim = AnimationUtils
                .loadAnimation(NewMantraActivity.this,R.anim.mantra_note_anim);
        mantraNote.startAnimation(mantraNoteAnim);

        mantraEditText = findViewById(R.id.et_new_mantra);
        malasEditText = findViewById(R.id.et_total_malas);
        saveButton = findViewById(R.id.done_chanting_button);

        PushDownAnim.setPushDownAnimTo(saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMantra();
            }
        });
    }

    private void addMantra() {
        final String mantra = mantraEditText.getText().toString();
        final String malas = malasEditText.getEditableText().toString();

        Animation shake = AnimationUtils.loadAnimation(NewMantraActivity.this, R.anim.shake);
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // Mantra is empty
        if (TextUtils.isEmpty(mantra)) {
            mantraEditText.startAnimation(shake);
            mantraEditText.setError("Required");
            if (vibrator != null)
                vibrator.vibrate(30);
            return;
        }

        // Malas is empty
        if (TextUtils.isEmpty(malas)) {
            malasEditText.startAnimation(shake);
            malasEditText.setError("Required");
            if (vibrator != null)
                vibrator.vibrate(30);
            return;
        }

        // Save the mantra
        Intent intent = new Intent(NewMantraActivity.this, MainActivity.class);
        intent.putExtra("mantra_name", mantraEditText.getText().toString().trim());
        intent.putExtra("total_malas",Integer.parseInt(malasEditText.getEditableText().toString().trim()));
        setResult(RESULT_OK, intent);
        finish();
    }
}


