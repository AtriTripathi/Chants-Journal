package com.atritripathi.chantsjournal;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MantraAdapter extends RecyclerView.Adapter<MantraAdapter.MantraViewHolder> {

    private final LayoutInflater mInflater;
    private List<Mantra> mMantras;  // Cached copies of Mantras

    /**
     * Constructor to set-up the initial layout
     */
    public MantraAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public Mantra getMantraAtPosition (int position) {
        return mMantras.get(position);
    }


    /**
     * Takes care of caching the already created views along with their component's resource ids.
     */
    public static class MantraViewHolder extends RecyclerView.ViewHolder {
        private final TextView mantraItemView;

        private MantraViewHolder(final View itemView) {
            super(itemView);
            mantraItemView = itemView.findViewById(R.id.tv_mantra);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), MantraDetailsActivity.class);
                    intent.putExtra("mantra_position", getAdapterPosition());
                    itemView.getContext().startActivity(intent);
                }
            });

//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    Intent intent = new Intent("delete_mantra");
//                    intent.putExtra("mantra_position",getAdapterPosition());
//                    LocalBroadcastManager.getInstance(itemView.getContext()).sendBroadcast(intent);
//                    return true;
//                }
//            });
        }
    }


    /**
     * Takes care of creating and inflating ViewHolders.
     * @param viewGroup Defines the parent view for which the viewHolder is created, to get the context.
     * @param position  Additional parameter to define the position.(not used here)
     * @return A new and inflated ViewHolder, from a given layout file.
     */
    @NonNull
    @Override
    public MantraViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int position) {
        final View itemView = mInflater.inflate(R.layout.layout_mantra, viewGroup, false);
        return new MantraViewHolder(itemView);
    }


    /**
     * Takes care of binding the data to a ViewHolder
     * @param holder   The existing viewHolder to which the data is to be attached.
     * @param position The position in the data source from from which the data is to be retrieved.
     */
    @Override
    public void onBindViewHolder(@NonNull MantraViewHolder holder, int position) {
        if (mMantras != null) {
            holder.mantraItemView.setText(mMantras.get(position).getMantraName());
        } else {
            holder.mantraItemView.setText(R.string.no_mantras);
        }
    }

    /**
     * To assign the mantras to the mantra list
     * @param mantras is the list of mantras
     */
    void setMantras(List<Mantra> mantras) {
        mMantras = mantras;
        notifyDataSetChanged();
    }


    /**
     * To handle the no of mantra views to create on the screen.
     * @return List size is returned back to the recycler view.
     */
    @Override
    public int getItemCount() {
        if (mMantras != null)
            return mMantras.size();
        else
            return 0;
    }

    void refreshMantraList(int position) {
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,getItemCount());
    }
}

