package com.mdp.ue1.schiermayer.lukas.ue3;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements ListAdapter.ListItemClickListener{
    private static ListAdapter mAdapter;
    private RecyclerView mList;

    public static ListAdapter getAdapter() {
        return mAdapter;
    }

    interface ListCallbackInterface {
        void onListItemClicked(String item);
    }

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        mList = rootView.findViewById(R.id.rv_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mList.setLayoutManager(layoutManager);

        mList.setHasFixedSize(true);

        List<Pair<String, Integer>> data = new LinkedList<>();

        mAdapter = new ListAdapter(data, this);
        mList.setAdapter(mAdapter);

        return rootView;
    }

    private Toast mToast;
    @Override
    public void onListItemClick(Pair<String, Integer> item) {
        if(mToast != null)
            mToast.cancel();

        mToast = Toast.makeText(getActivity(), item.first + " has been deleted!", Toast.LENGTH_SHORT);
        mToast.show();

        mAdapter.deleteItem(item);
    }
}
