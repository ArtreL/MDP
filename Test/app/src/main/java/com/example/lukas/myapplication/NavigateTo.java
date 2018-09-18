package com.example.lukas.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class NavigateTo extends AppCompatActivity {
    public static final String VALUE_KEY = "value";
    private static final String LOG_TAG = NavigateTo.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigate_to);
        TextView tvOutput = findViewById(R.id.tv_output);
        tvOutput.setText(getIntent().getStringExtra(VALUE_KEY));
        Log.d(LOG_TAG, "onCreate called.");
    }
}
