package com.mdp.ue1.schiermayer.lukas.webdemo;

import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getCanonicalName();
    private static final String RESULT_STRING = "result";

    private EditText mURLInput;
    private TextView mResultOutput;
    private Button mLoadButton;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mURLInput = findViewById(R.id.et_url);
        mResultOutput = findViewById(R.id.tv_output);
        mLoadButton = findViewById(R.id.btn_load);

        if(savedInstanceState != null) {
            String savedString = savedInstanceState.getString(RESULT_STRING);
            mResultOutput.setText(savedString);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String result = mResultOutput.getText().toString();
        outState.putString(RESULT_STRING, result);
    }

    public void loadButtonClicked(View v) {
        loadWebResult();
    }

    private void loadWebResult() {
        try {
            URL url = new URL(mURLInput.getText().toString());
            new LoadWebContentTask().execute(url);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "An exception occurred.", e);
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(this, "Invalid URL", Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

    private String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    private class LoadWebContentTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String resultString = null;

            try {
                resultString = getResponseFromHttpUrl(url);
            } catch (IOException e) {
                Log.e(LOG_TAG, "An error occurred.", e);
            }

            return resultString;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result == null || result.equals("")) {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(MainActivity.this, "Result is empty.", Toast.LENGTH_SHORT);
                mToast.show();
            } else {
                mResultOutput.setText(result);
            }
        }
    }
}
