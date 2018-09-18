package com.mdp.ue1.schiermayer.lukas.ue2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getCanonicalName();
    private static final String RESULT_STRING = "result";

    private TextView mResultOutput;
    private Button mLoadButton;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mResultOutput = findViewById(R.id.tv_output);
        mLoadButton = findViewById(R.id.btn_load);

        if (savedInstanceState != null) {
            String savedString = savedInstanceState.getString(RESULT_STRING);
            mResultOutput.setText(savedString);
            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        } else {
            mLoadButton.setEnabled(false);
            loadWebResult();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String result = mResultOutput.getText().toString();
        outState.putString(RESULT_STRING, result);
    }

    public void loadButtonClicked(View v) {
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        mLoadButton.setEnabled(false);
        mResultOutput.setText("");
        loadWebResult();
    }

    private void loadWebResult() {
        try {
            URL url = new URL("https://data.wien.gv.at/daten/geo?service=WFS&request=GetFeature&version=1.1.0&typeName=ogdwien:OEFFHALTESTOGD&srsName=EPSG:4326&outputFormat=json");
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
                String Stations = parseJSON(result);

                mResultOutput.append(Stations);
            }
            mLoadButton.setEnabled(true);
            findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        }
    }

    private String parseJSON(String jsonString) {
        List<String> retList = new ArrayList<>();
        Gson gson = new Gson();

        try {
            StationStore jsonStation = gson.fromJson(jsonString, StationStore.class);

            for (StationFeatures station : jsonStation.features) {
                String newStation = station.properties.HTXT;

                if (!retList.contains(newStation)) {
                    retList.add(newStation);
                }
            }
        } catch (Exception e) {
            retList = null;
            Log.e(LOG_TAG, "Error parsing JSON.", e);
        }


        StringBuilder output = new StringBuilder();
        if (retList != null) {
            Collections.sort(retList);
            int i = 0;
            for (String station : retList) {
                output.append(station).append("\n");
            }
        } else {
            output.append("Error Parsing JSON");
        }

        return output.toString();
    }

    private class StationStore {
        String type;
        int totalFeatures;
        StationFeatures[] features;
        StationCrs crs;
    }

    private class StationFeatures {
        String type;
        String id;
        StationGeometry geometry;
        String geometry_name;
        StationProperties properties;
    }

    private class StationGeometry {
        String type;
        double[] coordinates;
    }

    private class StationProperties {
        int OBJECTID;
        String HTXT;
        String HTXTK;
        String HLINIEN;
        int DIVA_ID;
        String SE_ANNO_CAD_DATA;
    }

    private class StationCrs {
        String type;
        StationCrsProperties properties;
    }

    private class StationCrsProperties {
        String name;
    }

    // Heckin' ugly JSON parse function
    // Simply here for historical reasons; not in use
    private List<String> parseJSONFugly(String jsonString) {
        List<String> retList = new ArrayList<>();
        JsonReader jsonReader = new JsonReader(new StringReader(jsonString));
        try {
            jsonReader.beginObject();
            jsonReader.nextName();
            jsonReader.nextString();
            jsonReader.nextName();
            jsonReader.nextInt();
            jsonReader.nextName();
            jsonReader.beginArray();
            while (jsonReader.hasNext()) {
                try {
                    jsonReader.beginObject();
                } catch (Exception e) {
                    break;
                }
                jsonReader.nextName();
                jsonReader.nextString();
                jsonReader.nextName();
                jsonReader.nextString();
                jsonReader.nextName();
                jsonReader.beginObject();
                jsonReader.nextName();
                jsonReader.nextString();
                jsonReader.nextName();
                jsonReader.beginArray();
                jsonReader.nextDouble();
                jsonReader.nextDouble();
                jsonReader.endArray();
                jsonReader.endObject();
                jsonReader.nextName();
                jsonReader.nextString();
                jsonReader.nextName();
                jsonReader.beginObject();
                jsonReader.nextName();
                jsonReader.nextInt();
                jsonReader.nextName();
                String name = jsonReader.nextString();
                if (!retList.contains(name)) {
                    retList.add(name);
                }
                jsonReader.nextName();
                jsonReader.nextString();
                jsonReader.nextName();
                jsonReader.nextString();
                jsonReader.nextName();
                try {
                    jsonReader.nextInt();
                } catch (Exception e) {
                    jsonReader.nextNull();
                }
                jsonReader.nextName();
                jsonReader.nextNull();
                jsonReader.endObject();
                jsonReader.endObject();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error parsing JSON.", e);
        }

        return retList;
    }
}
