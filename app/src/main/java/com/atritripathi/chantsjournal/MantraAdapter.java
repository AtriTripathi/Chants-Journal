package com.atritripathi.chantsjournal;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MantraAdapter extends RecyclerView.Adapter<MantraAdapter.MantraViewHolder> {

    private ArrayList<String> mantraList;

    /**
     * Constructor to initialize the mantraList
     *
     * @param mantraList List of all the mantras
     */
    public MantraAdapter(ArrayList<String> mantraList) {
        this.mantraList = mantraList;
        notifyDataSetChanged();
    }


    /**
     * Takes care of caching the already created views along with their component's resource ids.
     */
    public static class MantraViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        private MantraViewHolder(View v) {
            super(v);
            mTextView = v.findViewById(R.id.tv_mantra);
        }
    }


    /**
     * Takes care of creating and inflating ViewHolders.
     *
     * @param viewGroup Defines the parent view for which the viewHolder is created, to get the context.
     * @param position  Additional parameter to define the position.(not used here)
     * @return A new and inflated ViewHolder, from a given layout file.
     */
    @NonNull
    @Override
    public MantraAdapter.MantraViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_mantra, viewGroup, false);
        return new MantraViewHolder(view);
    }


    /**
     * Takes care of binding the data to a ViewHolder
     *
     * @param holder   The existing viewHolder to which the data is to be attached.
     * @param position The position in the data source from from which the data is to be retrieved.
     */
    @Override
    public void onBindViewHolder(@NonNull MantraAdapter.MantraViewHolder holder, int position) {
        holder.mTextView.setText(mantraList.get(position));
    }


    /**
     * To handle the no of views to create on the screen.
     *
     * @return List size is returned back to the recycler view.
     */
    @Override
    public int getItemCount() {
        return mantraList.size();
    }
}

