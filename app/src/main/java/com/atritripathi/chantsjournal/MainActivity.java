package com.atritripathi.chantsjournal;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.droidbyme.dialoglib.AnimUtils;
import com.droidbyme.dialoglib.DroidDialog;

import java.util.List;

public class MainActivity extends AppCompatActivity{
    private static final int MANTRA_DETAILS_ACTIVITY_REQUEST_CODE = 0;

    private MantraViewModel mMantraViewModel;
    private MantraAdapter mMantraAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To show the translucent Buddha in the background, appropriately.
        View initBuddhaView = findViewById(R.id.init_buddha_view);
        ImageView buddhaTranslucent = findViewById(R.id.buddha_always_bg);

        mMantraAdapter = new MantraAdapter(this);

        RecyclerView mRecyclerView = findViewById(R.id.rv_mantras);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Checks whether list is empty and then displays the initial buddha image
        mMantraAdapter.registerAdapterDataObserver(new MantraListEmptyObserver(mRecyclerView,initBuddhaView,buddhaTranslucent));
        mRecyclerView.setAdapter(mMantraAdapter);

        // Get a new or existing ViewModel from the ViewModelProvider.
        mMantraViewModel = ViewModelProviders.of(this).get(MantraViewModel.class);

        // Add an observer on the LiveData. The onChanged() method fires when the observed data
        // changes and the activity is in the foreground.
        mMantraViewModel.getAllMantras().observe(this, new Observer<List<Mantra>>() {
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

//        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
//                new IntentFilter("delete_mantra"));

        // Add the functionality to swipe items in the
        // recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        deleteMantraAlertDialog(position);
//                        Mantra mantra = mMantraAdapter.getMantraAtPosition(position);
//                        Toast.makeText(MainActivity.this, "Deleting " +
//                                mantra.getMantraName(), Toast.LENGTH_LONG).show();
//
//                        // Delete the word
//                        mMantraViewModel.deleteMantra(mantra);
                    }
                });
        helper.attachToRecyclerView(mRecyclerView);
    }


//    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            int position = intent.getIntExtra("mantra_position",0);
//            deleteMantraAlertDialog(position);
//        }
//    };


    /**
     * Used to get the mantra details from the NewMantraActivity and consequently store it
     * in the database.
     * @param requestCode Used to make sure that the result is returned from the correct activity.
     * @param resultCode Used to verify that a valid mantra has been added or not.
     * @param data This is the intent from which mantra data is extracted to be added to database.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (MANTRA_DETAILS_ACTIVITY_REQUEST_CODE): {
                if (resultCode == RESULT_OK) {

                    String mantraName = data.getStringExtra("mantra_name");
                     int totalMalas = data.getIntExtra("total_malas",0);
//                    String startDate = data.getStringExtra("start_date");
//                    String endDate = data.getStringExtra("end_date");
//                    String notes = data.getStringExtra("notes");
//                    int totalDays = data.getIntExtra("total_days", 1);
//                    int chantsPerDay = data.getIntExtra("chants_per_day", 1);

//                    Mantra mantra = new Mantra(mantraName, totalMalas, startDate, endDate, notes, totalDays, chantsPerDay);
                    Mantra mantra = new Mantra(mantraName, totalMalas);
                    mMantraViewModel.insert(mantra);
                }
            }
        }
    }

    private void deleteMantraAlertDialog(final int position) {

        DroidDialog.onPositiveListener positiveListener = new DroidDialog.onPositiveListener() {
            @Override
            public void onPositive(Dialog dialog) {
                dialog.dismiss();
            }
        };

        DroidDialog.onNegativeListener negativeListener = new DroidDialog.onNegativeListener() {
            @Override
            public void onNegative(Dialog dialog) {

                Mantra mantra = mMantraAdapter.getMantraAtPosition(position);
                mMantraViewModel.deleteMantra(mantra);

                Toast.makeText(MainActivity.this,"Mantra deleted" ,Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        };

        // This if condition is used to prevent app crashes when a dialog is being shown
        // while the activity is closing.
        if(!MainActivity.this.isFinishing()) {
            new DroidDialog.Builder(MainActivity.this)
                    .icon(R.drawable.ic_action_close)
                    .title("Confirm deletion.")
                    .content("Are you sure? This will permanently delete this mantra and all its associated data.")
                    .cancelable(true, true)
                    .positiveButton("Cancel", positiveListener)
                    .negativeButton("Delete", negativeListener)
                    .animation(AnimUtils.AnimUpDown)
                    .color(ContextCompat.getColor(MainActivity.this, R.color.alternate_orange),
                            ContextCompat.getColor(MainActivity.this, R.color.secondaryColor),
                            ContextCompat.getColor(MainActivity.this, R.color.alpha_red))
                    .divider(true, ContextCompat.getColor(MainActivity.this, R.color.orange))
                    .show();
            mMantraAdapter.refreshMantraList(position);
        }
    }
}
