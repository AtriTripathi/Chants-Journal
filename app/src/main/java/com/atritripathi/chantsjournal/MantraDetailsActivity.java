package com.atritripathi.chantsjournal;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.droidbyme.dialoglib.AnimUtils;
import com.droidbyme.dialoglib.DroidDialog;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class MantraDetailsActivity extends AppCompatActivity {

    private static final String TAG = "MantraDetailsActivity";

    private int malasCompleted = 0;
    private int totalMalasCompleted;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantra_details);

        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mp = MediaPlayer.create(this, R.raw.malas_counter_ting);

        final TextView mantraName = findViewById(R.id.tv_mantra_name);
        final TextView totalMalas = findViewById(R.id.tv_details_total_malas);
        final TextView completedMalas = findViewById(R.id.tv_details_completed_malas);
        final TextView count = findViewById(R.id.tv_count);
        final Button addButton = findViewById(R.id.button_add);
        final Button doneChanting = findViewById(R.id.done_chanting_button);

//        final int position = getIntent().getIntExtra("mantra_position", 1);
//        Log.d(TAG, "onCreate: position = " + position);

        final String mantraIdentifier = getIntent().getStringExtra("mantra_name");

        new Thread(new Runnable() {
            @Override
            public void run() {
//                Mantra mantra = MantraDatabase.getDatabase(MantraDetailsActivity.this).mantraDao().getMantra(position+1);
                Mantra mantra = MantraDatabase.getDatabase(MantraDetailsActivity.this).mantraDao().getMantra(mantraIdentifier);
                Log.d(TAG, "Value of mantra = " + mantra);
                mantraName.setText(mantra.getMantraName());
                totalMalas.setText(mantra.getTotalMalas() + " Malas");
                completedMalas.setText(mantra.getCompletedMalas() + " Malas");
                totalMalasCompleted = mantra.getCompletedMalas();
                count.setText(malasCompleted + " Malas Completed");
            }
        }).start();

        PushDownAnim.setPushDownAnimTo(addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mp.isPlaying() && mp != null) {
                    mp.stop();
                    mp.reset();
                    mp.release();
                    mp = null;
                }
                mp = MediaPlayer.create(getBaseContext(), R.raw.malas_counter_ding);
                mp.start();

                vibrator.vibrate(20);

                malasCompleted++;
                count.setText(malasCompleted + " Malas Completed");

                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.reset();
                        mp.release();
                    }
                });
            }
        });


        doneChanting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mp.isPlaying() && mp != null) {
                    mp.stop();
                    mp.reset();
                    mp.release();
                    mp = null;
                }
                mp = MediaPlayer.create(getBaseContext(), R.raw.done_chanting_dialog_tune);
                mp.start();

                DroidDialog.onPositiveListener positiveListener = new DroidDialog.onPositiveListener() {
                    @Override
                    public void onPositive(Dialog dialog) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                MantraDatabase.getDatabase(MantraDetailsActivity.this)
                                        .mantraDao()
                                        .updateMalas(mantraIdentifier,totalMalasCompleted + malasCompleted);
                            }
                        }).start();

                        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mp.reset();
                                mp.release();
                            }
                        });

                        dialog.dismiss();
                        finish();
                    }
                };

                DroidDialog.onNegativeListener negativeListener = new DroidDialog.onNegativeListener() {
                    @Override
                    public void onNegative(Dialog dialog) {
                        dialog.dismiss();
                    }
                };

                if(!MantraDetailsActivity.this.isFinishing()) {
                    new DroidDialog.Builder(MantraDetailsActivity.this)
                            .icon(R.drawable.ic_action_tick)
                            .title(getString(R.string.done_for_day_title))
                            .content(getString(R.string.done_for_day_content))
                            .cancelable(true, true)
                            .positiveButton("I'm Done For Today", positiveListener)
                            .negativeButton("Continue Chanting", negativeListener)
                            .animation(AnimUtils.AnimUp)
                            .color(ContextCompat.getColor(MantraDetailsActivity.this, R.color.alternate_orange),
                                    ContextCompat.getColor(MantraDetailsActivity.this, R.color.secondaryColor),
                                    ContextCompat.getColor(MantraDetailsActivity.this, R.color.alpha_red))
                            .divider(true, ContextCompat.getColor(MantraDetailsActivity.this, R.color.orange))
                            .show();
                }
            }
        });
    }
}


