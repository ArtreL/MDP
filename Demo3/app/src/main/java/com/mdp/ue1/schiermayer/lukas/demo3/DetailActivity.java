package com.mdp.ue1.schiermayer.lukas.demo3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            DetailFragment fragment = new DetailFragment();
            String item = getIntent().getStringExtra(Intent.EXTRA_TEXT);
            Bundle args = new Bundle();
            args.putString(Intent.EXTRA_TEXT, item);
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, fragment, "details_fragment").commit();
        }
    }
}
