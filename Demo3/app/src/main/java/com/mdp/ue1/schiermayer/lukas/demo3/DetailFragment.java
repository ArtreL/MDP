package com.mdp.ue1.schiermayer.lukas.demo3;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        TextView outputTextView = rootView.findViewById(R.id.tv_output);
        if (getArguments() != null && getArguments().getString(Intent.EXTRA_TEXT) != null)
            outputTextView.setText(getArguments().getString(Intent.EXTRA_TEXT) + " was clicked!");

        return rootView;
    }

}
