package com.atritripathi.chantsjournal;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

public class MantraListEmptyObserver extends RecyclerView.AdapterDataObserver {
    private View emptyView;
    private RecyclerView recyclerView;

    private static final String TAG = "MantraListEmptyObserver";


    /**
     * Constructor to set an Empty View for the RV
     */
    public MantraListEmptyObserver(RecyclerView rv, View ev) {
        this.recyclerView = rv;
        this.emptyView = ev;
        checkIfEmpty();
    }


    /**
     * Check if Layout is empty and show the appropriate view
     */
    private void checkIfEmpty() {
        if (emptyView != null && recyclerView.getAdapter() != null) {
            boolean emptyViewVisible = recyclerView.getAdapter().getItemCount() == 0;
            Log.d(TAG, "checkIfEmpty: empty view = " + emptyViewVisible);
            emptyView.setVisibility(emptyViewVisible ? View.VISIBLE : View.GONE);
            Log.d(TAG, "checkIfEmpty: empty view = " + emptyView.getVisibility());
            recyclerView.setVisibility(emptyViewVisible ? View.GONE : View.VISIBLE);
            Log.d(TAG, "checkIfEmpty: recyclerview view = " + recyclerView.getVisibility());
        }
    }


    /**
     Abstract method implementations
     */
    @Override
    public void onChanged() {
        checkIfEmpty();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        checkIfEmpty();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        checkIfEmpty();
    }

}
