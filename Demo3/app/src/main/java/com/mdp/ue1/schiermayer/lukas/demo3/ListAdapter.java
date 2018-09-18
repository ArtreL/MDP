package com.mdp.ue1.schiermayer.lukas.demo3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewHolder> {
    interface ListItemClickListener {
        void onListItemClick(String item);
    }

    private ListItemClickListener mClickListener;

    private List<String> mItems;

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mItemTextView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mItemTextView = itemView.findViewById(R.id.tv_item);
            itemView.setOnClickListener(this);
        }

        void bind(int index) {
            mItemTextView.setText(mItems.get(index));
        }

        @Override
        public void onClick(View v) {
            int clickedItemIndex = getAdapterPosition();
            mClickListener.onListItemClick(mItems.get(clickedItemIndex));
        }
    }

    public ListAdapter(List<String> items, ListItemClickListener clickListener) {
        mItems = items;
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(layoutIdForListItem, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return (mItems == null) ? 0 : mItems.size();
    }

    public void swapData(List<String> items) {
        mItems = items;
        notifyDataSetChanged();
    }
}
