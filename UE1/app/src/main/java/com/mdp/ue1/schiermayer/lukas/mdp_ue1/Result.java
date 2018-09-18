package com.mdp.ue1.schiermayer.lukas.mdp_ue1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Result extends AppCompatActivity {
    public static final String VALUE_KEY = "value";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView tvOutput = findViewById(R.id.tv_output);
        tvOutput.setText(getIntent().getStringExtra(VALUE_KEY));
    }
}
