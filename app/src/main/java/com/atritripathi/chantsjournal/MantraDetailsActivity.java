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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.droidbyme.dialoglib.AnimUtils;
import com.droidbyme.dialoglib.DroidDialog;
import com.thekhaeng.pushdownanim.PushDownAnim;

public class MantraDetailsActivity extends AppCompatActivity {

    private static final String TAG = "MantraDetailsActivity";

    private int malasCompleted = 0;
    private int totalMalasCompleted;
    private int totalMalasInt;
    private int completedMalasInt;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantra_details);

        overridePendingTransition(R.anim.fade_in_layout,R.anim.fade_out_layout);

        final TextView mantraName = findViewById(R.id.tv_mantra_name);
        final TextView totalMalas = findViewById(R.id.tv_details_total_malas);
        final TextView completedMalas = findViewById(R.id.tv_details_completed_malas);
        final TextView count = findViewById(R.id.tv_count);
        final Button addButton = findViewById(R.id.button_add);
        final Button doneChanting = findViewById(R.id.done_chanting_button);

        Animation mantraNoteAnim = AnimationUtils.loadAnimation(MantraDetailsActivity.this,R.anim.plus_button_anim);
        addButton.startAnimation(mantraNoteAnim);

        final String mantraIdentifier = getIntent().getStringExtra("mantra_name");

        final Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mp = MediaPlayer.create(this, R.raw.malas_counter_ting);


        new Thread(new Runnable() {
            @Override
            public void run() {
//                Mantra mantra = MantraDatabase.getDatabase(MantraDetailsActivity.this).mantraDao().getMantra(position+1);
                Mantra mantra = MantraDatabase.getDatabase(MantraDetailsActivity.this).mantraDao().getMantra(mantraIdentifier);
                Log.d(TAG, "Value of mantra = " + mantra);
                mantraName.setText(mantra.getMantraName());
                totalMalasInt = mantra.getTotalMalas();
                completedMalasInt = mantra.getCompletedMalas();
                totalMalas.setText(totalMalasInt + " Malas");
                completedMalas.setText(completedMalasInt + " Malas");
//                totalMalasInt = Integer.valueOf(totalMalas.getText().toString());
//                completedMalasInt = Integer.valueOf(completedMalas.getText().toString());
                totalMalasCompleted = mantra.getCompletedMalas();
                count.setText(malasCompleted + " Malas Completed");

            }
        }).start();

        PushDownAnim.setPushDownAnimTo(addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int finalCount = malasCompleted + completedMalasInt;
                Log.d(TAG, "checkChantingGoalReached: fc and tm are " + finalCount + " " + totalMalasInt);
                if (finalCount == totalMalasInt) {
                    checkChantingGoalReached(completedMalasInt, totalMalasInt, mantraIdentifier);
                }

                mp = MediaPlayer.create(getBaseContext(), R.raw.malas_counter_ding);
                if (mp.isPlaying() && mp != null) {
                    mp.stop();
                    mp.reset();
                    mp.release();
                    mp = MediaPlayer.create(getBaseContext(), R.raw.malas_counter_ding);
                }

                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.reset();
                        mp.release();
                    }
                });

//                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mp) {
//                        mp.release();
//                    }
//                });

                vibrator.vibrate(20);

                malasCompleted++;
                count.setText(malasCompleted + " Malas Completed");


                checkChantingGoalReached(completedMalasInt, totalMalasInt, mantraIdentifier);

            }
        });


        doneChanting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp = MediaPlayer.create(MantraDetailsActivity.this, R.raw.done_chanting_dialog_tune);
                if (mp.isPlaying() && mp != null) {
                    mp.stop();
                    mp.reset();
                    mp.release();
                    mp = MediaPlayer.create(MantraDetailsActivity.this, R.raw.done_chanting_dialog_tune);
                }

                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.reset();
                        mp.release();
                    }
                });

                DroidDialog.onPositiveListener positiveListener = new DroidDialog.onPositiveListener() {
                    @Override
                    public void onPositive(Dialog dialog) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                MantraDatabase.getDatabase(MantraDetailsActivity.this)
                                        .mantraDao()
                                        .updateMalas(mantraIdentifier, totalMalasCompleted + malasCompleted);
                            }
                        }).start();

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

                if (!MantraDetailsActivity.this.isFinishing()) {
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

    @Override
    public void onBackPressed() {
        if (malasCompleted != 0) {
            DroidDialog.onPositiveListener positiveListener = new DroidDialog.onPositiveListener() {
                @Override
                public void onPositive(Dialog dialog) {
                    dialog.dismiss();
                }
            };

            DroidDialog.onNegativeListener negativeListener = new DroidDialog.onNegativeListener() {
                @Override
                public void onNegative(Dialog dialog) {
                    dialog.dismiss();
                    finish();
                }
            };

            if (!MantraDetailsActivity.this.isFinishing()) {
                new DroidDialog.Builder(MantraDetailsActivity.this)
                        .icon(R.drawable.ic_action_tick)
                        .title("Alert!")
                        .content("Are you sure? Any unsaved chanting progress will be lost.")
                        .cancelable(true, true)
                        .positiveButton("Cancel", positiveListener)
                        .negativeButton("Continue", negativeListener)
                        .animation(AnimUtils.AnimUp)
                        .color(ContextCompat.getColor(MantraDetailsActivity.this, R.color.alternate_orange),
                                ContextCompat.getColor(MantraDetailsActivity.this, R.color.secondaryColor),
                                ContextCompat.getColor(MantraDetailsActivity.this, R.color.alpha_red))
                        .divider(true, ContextCompat.getColor(MantraDetailsActivity.this, R.color.orange))
                        .show();
            }
        } else {
            finish();
        }

    }

    void checkChantingGoalReached(int completedMalasInt, int totalMalasInt, final String mantraIdentifier) {
        int finalCount = malasCompleted + completedMalasInt;

        Log.d(TAG, "checkChantingGoalReached: fc and tm are " + finalCount + " " + totalMalasInt);
        if (finalCount == totalMalasInt) {

            DroidDialog.onNeutralListener neutralListener = new DroidDialog.onNeutralListener() {
                @Override
                public void onNeutral(Dialog dialog) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MantraDatabase.getDatabase(MantraDetailsActivity.this)
                                    .mantraDao()
                                    .updateMalas(mantraIdentifier, totalMalasCompleted + malasCompleted);
                        }
                    }).start();

                    dialog.dismiss();
                    finish();
                }
            };

            if (!MantraDetailsActivity.this.isFinishing()) {
                new DroidDialog.Builder(MantraDetailsActivity.this)
                        .icon(R.drawable.ic_action_tick)
                        .title("Congratulations!")
                        .content("You have successfully completed your chanting goal. " +
                                "Please delete this mantra from the list, or you can continue to chant as long as you wish.")
                        .cancelable(false, false)
                        .neutralButton("Okay", neutralListener)
                        .animation(AnimUtils.AnimUp)
                        .color(ContextCompat.getColor(MantraDetailsActivity.this, R.color.alternate_orange),
                                ContextCompat.getColor(MantraDetailsActivity.this, R.color.secondaryColor),
                                ContextCompat.getColor(MantraDetailsActivity.this, R.color.alpha_red))
                        .divider(true, ContextCompat.getColor(MantraDetailsActivity.this, R.color.orange))
                        .show();
            }
        }
    }

}



