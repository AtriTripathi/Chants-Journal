package com.atritripathi.chantsjournal;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class SettingsBottomSheet extends RoundedBottomSheetDialogFragment {

    public SettingsBottomSheet() {
        // Required empty public constructor
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View BottomSheetView = inflater.inflate(R.layout.settings_bottom_sheet, container, false);

        Animation bottomSheetAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_sheet_anim);
        BottomSheetView.startAnimation(bottomSheetAnim);

        final ConstraintLayout shareButton = BottomSheetView.findViewById(R.id.button_share);
        final ConstraintLayout feedbackButton = BottomSheetView.findViewById(R.id.button_feedback);
        final ConstraintLayout aboutButton = BottomSheetView.findViewById(R.id.button_about);
        final ConstraintLayout exitButton = BottomSheetView.findViewById(R.id.button_exit);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Install Chants Journal \nSearch on google";
                String shareSub = "Install Chants Journal";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share Chants Journal via"));

                if (getActivity() != null)
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .remove(SettingsBottomSheet.this).commit();
            }
        });

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent feedbackIntent = new Intent(Intent.ACTION_SENDTO);
                feedbackIntent.setData(Uri.parse("mailto:"));
                feedbackIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"tripathi.atri@gmail.com"});
                feedbackIntent.putExtra(Intent.EXTRA_SUBJECT, "[Chants Journal] Feedback");
                feedbackIntent.putExtra(Intent.EXTRA_TEXT, "Dear Atri," + "  ");
                startActivity(Intent.createChooser(feedbackIntent, "Send Feedback:"));

                if (getActivity() != null)
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .remove(SettingsBottomSheet.this).commit();
            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog helpDialog = new Dialog(getContext(), R.style.PauseDialog);
                helpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                helpDialog.setContentView(R.layout.layout_about);
                helpDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                helpDialog.show();

                if (getActivity() != null)
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .remove(SettingsBottomSheet.this).commit();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null)
                    getActivity().finishAffinity();
            }
        });

        // Inflate the layout for this fragment
        return BottomSheetView;
    }
}
