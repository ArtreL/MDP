package com.mdp.ue1.schiermayer.lukas.ue3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "UE3_Log";
    private static final String RESULT_STRING = "result";
    private static final String SAVE_FILE = "todoLogRegister.log";

    EditText etTodoItem;
    SeekBar sbPriority;
    Button btnOK;
    ListAdapter mainAdapter;
    Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etTodoItem = findViewById(R.id.et_input);
        sbPriority = findViewById(R.id.sb_priority);
        btnOK = findViewById(R.id.btn_ok);
        mainAdapter = ListFragment.getAdapter();

        if (savedInstanceState != null) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Pair<String, Integer>>>() {
            }.getType();
            List<Pair<String, Integer>> savedItems = gson.fromJson(savedInstanceState.getString(RESULT_STRING), listType);
            mainAdapter.swapData(savedItems);
        } else {
            File dataStore = new File(this.getFilesDir(), SAVE_FILE);
            String ret;
            Gson gson = new Gson();
            try {
                InputStream iStream = new FileInputStream(dataStore);
                InputStreamReader iStreamReader = new InputStreamReader(iStream);
                BufferedReader bufReader = new BufferedReader(iStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                bufReader.close();
                ret = stringBuilder.toString();

                Type listType = new TypeToken<List<Pair<String, Integer>>>() {
                }.getType();
                List<Pair<String, Integer>> savedItems = gson.fromJson(ret, listType);
                if(savedItems.size() == 0) {
                    savedItems = new ArrayList<>();
                    savedItems.add(new Pair<>("Another with some of both", 17));
                    savedItems.add(new Pair<>("Todo Item with high priority", 17));
                    savedItems.add(new Pair<>("An alphabetically early Item", 5));
                }
                mainAdapter.swapData(savedItems);

            } catch (FileNotFoundException e) {
                Log.e(LOG_TAG, "Error opening file.", e);
                List<Pair<String, Integer>> savedItems = new ArrayList<>();
                if(savedItems.size() == 0) {
                    savedItems = new ArrayList<>();
                    savedItems.add(new Pair<>("Another with some of both", 17));
                    savedItems.add(new Pair<>("Todo Item with high priority", 17));
                    savedItems.add(new Pair<>("An alphabetically early Item", 5));
                }
                mainAdapter.swapData(savedItems);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error reading from file.", e);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        File dataStore = new File(this.getFilesDir(), SAVE_FILE);
        try {
            OutputStream oStream = new FileOutputStream(dataStore);
            OutputStreamWriter oStreamWriter = new OutputStreamWriter(oStream);
            Gson gson = new Gson();
            oStreamWriter.write(gson.toJson(mainAdapter.getItems()));
            oStreamWriter.close();
        } catch (FileNotFoundException e) {
            Log.e(LOG_TAG, "Error opening file.", e);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error writing to file.", e);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Gson gson = new Gson();
        String result = gson.toJson(mainAdapter.getItems());
        outState.putString(RESULT_STRING, result);
    }

    public void createButtonClicked(View v) {
        String todoName = etTodoItem.getText().toString();

        if (!todoName.isEmpty()) {
            List<Pair<String, Integer>> adapterItems = mainAdapter.getItems();
            Pair<String, Integer> newItem = new Pair<>(todoName, sbPriority.getProgress());

            if(adapterItems == null) {
                adapterItems = new ArrayList<>();
            }

            Integer itemsLength = adapterItems.size();
            Integer i = 0;

            for (Pair<String, Integer> item : adapterItems) {
                if (newItem.second > item.second) {
                    adapterItems.add(i, newItem);
                    break;
                } else if (newItem.second.equals(item.second)) {
                    if (newItem.first.compareTo(item.first) < 0) {
                        adapterItems.add(i, newItem);
                        break;
                    }
                }
                ++i;
            }

            if (itemsLength == adapterItems.size()) {
                adapterItems.add(newItem);
            }

            mainAdapter.swapData(adapterItems);
            etTodoItem.setText("");
            sbPriority.setProgress(0);
        } else {
            if (mToast != null)
                mToast.cancel();

            mToast = Toast.makeText(this, "Error: Empty Item", Toast.LENGTH_SHORT);
            mToast.show();
        }
    }
}
