package com.mdp.ue1.schiermayer.lukas.mdp_ue1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tvOutput = findViewById(R.id.tv_output);
        final TextView tvSeekOutput = findViewById(R.id.tv_seekoutput);

        Button btnCalc = findViewById(R.id.btn_calc);
        Button btnNavi = findViewById(R.id.btn_nav);

        final EditText etInputOne = findViewById(R.id.et_input_one);
        final EditText etInputTwo = findViewById(R.id.et_input_two);

        final SeekBar sbSeek = findViewById(R.id.sb_seek);

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int InputOne = 0;
                int InputTwo = 0;

                try
                {
                    InputOne = Integer.parseInt(etInputOne.getText().toString());
                }
                catch(NumberFormatException e)
                {
                    etInputOne.setText("0");
                }

                try
                {
                    InputTwo = Integer.parseInt(etInputTwo.getText().toString());
                }
                catch(NumberFormatException e)
                {
                    etInputTwo.setText("0");
                }

                tvOutput.setText(Integer.toString(InputOne + InputTwo));
            }
        });

        btnNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int InputOne = 0;
                int InputTwo = 0;

                try
                {
                    InputOne = Integer.parseInt(etInputOne.getText().toString());
                }
                catch(NumberFormatException e)
                {
                    etInputOne.setText("0");
                }

                try
                {
                    InputTwo = Integer.parseInt(etInputTwo.getText().toString());
                }
                catch(NumberFormatException e)
                {
                    etInputTwo.setText("0");
                }

                Intent intent = new Intent(MainActivity.this, Result.class);
                intent.putExtra(Result.VALUE_KEY, Integer.toString(InputOne + InputTwo));
                startActivity(intent);
            }
        });

        sbSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSeekOutput.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
