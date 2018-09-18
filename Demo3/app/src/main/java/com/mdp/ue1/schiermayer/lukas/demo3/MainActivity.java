package com.mdp.ue1.schiermayer.lukas.demo3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements ListFragment.ListCallbackInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onListItemClicked(String item) {
        if(findViewById(R.id.detail_container) != null) {
            DetailFragment fragment = new DetailFragment();
            Bundle args = new Bundle();
            args.putString(Intent.EXTRA_TEXT, item);
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, fragment).commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, item);
            startActivity(intent);
        }
    }
}
