package com.example.osoznaator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;



public class MainActivity extends AppCompatActivity {
    private AdapterO pAdapter;
    private List<Osozn> osoznList = new ArrayList<>();
    EditText etFilter;
    Spinner spinner;
    ListView lstView;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstView = findViewById(R.id.osoznList);
        String[]items = {"<по умолчанию>","Цель","Дата", "Оценка состояния"};
        spinner = findViewById(R.id.spSort);
        etFilter = findViewById(R.id.etFilter);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(etFilter.getText().toString().isEmpty()){
                    Sort(osoznList);
                }
                else{
                    Search();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
        etFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Search();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void SetAdapter(List<Osozn> list){
        pAdapter = new AdapterO(MainActivity.this,list);
        lstView.setAdapter(pAdapter);
        pAdapter.notifyDataSetInvalidated();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void Search(){
        List<Osozn> lstFilter = osoznList.stream().filter(x-> (x.Cel.toLowerCase(Locale.ROOT).contains(etFilter.getText().toString().toLowerCase(Locale.ROOT)))).collect(Collectors.toList());
        Sort(lstFilter);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void Sort(List<Osozn> list){
        lstView.setAdapter(null);

        switch(spinner.getSelectedItemPosition()){
            case 0:
                if(etFilter.getText().toString().isEmpty()){
                    osoznList.clear();
                    new GetProducts().execute();
                }
                break;
            case 1:
                Collections.sort(list, Comparator.comparing(Osozn::getCel));
                break;
            case 2:
                Collections.sort(list, Comparator.comparing(Osozn::getTime));
                break;
            case 3:
                Collections.sort(list, Comparator.comparing(Osozn::getOcenkaemotion));
                break;
            default:
                break;
        }
        SetAdapter(list);
    }

    public void GoAddData(View v){
        startActivity(new Intent(this, AddData.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void Clear(View v) {
        etFilter.setText("");
        spinner.setSelection(0);
    }

    private class GetProducts extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://ngknn.ru:5001/ngknn/БыковаАА/Api/OsoznMains");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();
            } catch (Exception exception) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                JSONArray tempArray = new JSONArray(s);
                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject filmJson = tempArray.getJSONObject(i);
                    Osozn tempProduct = new Osozn(
                            filmJson.getInt("ID"),
                            filmJson.getString("Cel"),
                            filmJson.getString("DRazum"),
                            filmJson.getString("Prostr"),
                            filmJson.getInt("Ocenkaemotion"),
                            filmJson.getString("Emotion"),
                            filmJson.getString("Do"),
                            filmJson.getString("Think"),
                            filmJson.getString("Time")
                    );
                    osoznList.add(tempProduct);
                    pAdapter.notifyDataSetInvalidated();
                }
            } catch (Exception ignored) {
                Log.e(ignored.toString(), ignored.getMessage());
            }
        }
    }

}