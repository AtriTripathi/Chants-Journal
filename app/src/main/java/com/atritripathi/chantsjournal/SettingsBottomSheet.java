package com.atritripathi.chantsjournal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsBottomSheet extends RoundedBottomSheetDialogFragment {
    public SettingsBottomSheet() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View BottomSheetView = inflater.inflate(R.layout.settings_bottom_sheet, container, false);

        TextView shareButton = BottomSheetView.findViewById(R.id.tv_share);
        TextView donateButton = BottomSheetView.findViewById(R.id.tv_donate);
        TextView feedbackButton = BottomSheetView.findViewById(R.id.tv_feedback);
        TextView aboutButton = BottomSheetView.findViewById(R.id.tv_about);
        TextView exitButton = BottomSheetView.findViewById(R.id.tv_exit);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Share clicked", Toast.LENGTH_SHORT).show();
            }
        });

        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "About clicked", Toast.LENGTH_SHORT).show();
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
