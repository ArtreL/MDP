package com.mdp.ue1.schiermayer.lukas.demo3;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment implements ListAdapter.ListItemClickListener{
    private ListAdapter mAdapter;
    private RecyclerView mList;

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

        mList = rootView.findViewById(R.id.rv_list);/*
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());*/
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        mList.setLayoutManager(layoutManager);

        mList.setHasFixedSize(true);

        List<String> data = new LinkedList<>();
        for (int i = 0; i < 200; ++i) {
            data.add(String.valueOf(i));
        }

        mAdapter = new ListAdapter(data, this);
        mList.setAdapter(mAdapter);

        return rootView;
    }

    private Toast mToast;
    @Override
    public void onListItemClick(String item) {
/*        if(mToast != null)
            mToast.cancel();

        mToast = Toast.makeText(getActivity(), item + " was clicked!", Toast.LENGTH_SHORT);
        mToast.show();*/
        if (getActivity() instanceof ListCallbackInterface)
            ((ListCallbackInterface)getActivity()).onListItemClicked(item);
    }
}
