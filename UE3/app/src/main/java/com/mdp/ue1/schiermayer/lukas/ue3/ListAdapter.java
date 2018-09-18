package com.mdp.ue1.schiermayer.lukas.ue3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewHolder> {
    private ListItemClickListener mClickListener;
    private List<Pair<String, Integer>> mItems;

    public ListAdapter(List<Pair<String, Integer>> items, ListItemClickListener clickListener) {
        mItems = items;
        mClickListener = clickListener;
    }

    interface ListItemClickListener {
        void onListItemClick(Pair<String, Integer> item);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mItemTextView;
        private TextView mItemNumber;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mItemTextView = itemView.findViewById(R.id.tv_item);
            mItemNumber = itemView.findViewById(R.id.tv_number);
            itemView.setOnClickListener(this);
        }

        void bind(int index) {
            mItemTextView.setText(mItems.get(index).first);
            String numberOutput = mItems.get(index).second.toString();
            mItemNumber.setText(numberOutput);
        }

        @Override
        public void onClick(View v) {
            int clickedItemIndex = getAdapterPosition();
            mClickListener.onListItemClick(mItems.get(clickedItemIndex));
        }
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

    public List<Pair<String, Integer>> getItems() {
        return mItems;
    }

    public void swapData(List<Pair<String, Integer>> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public void deleteItem(Pair<String, Integer> item) {
        mItems.remove(item);
        notifyDataSetChanged();
    }
}
