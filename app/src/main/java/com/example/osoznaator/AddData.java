package com.example.osoznaator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddData extends AppCompatActivity {

    EditText Cel, DRazum, Prostr, Ocenkaemotion, Emotion, Doi, Think, Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        Cel = findViewById(R.id.Cel);
        DRazum = findViewById(R.id.DRazum);
        Prostr = findViewById(R.id.Prostr);
        Ocenkaemotion = findViewById(R.id.Ocenkaemotion);
        Emotion = findViewById(R.id.Emotion);
        Doi = findViewById(R.id.Doi);
        Think = findViewById(R.id.Think);
        Time = findViewById(R.id.Time);

    }

    public void GoViewData(View v){
        startActivity(new Intent(this, MainActivity.class));
    }


    public void DataPost(View v){
            postData(Cel.getText().toString(), DRazum.getText().toString(), Prostr.getText().toString(), Integer.parseInt(Ocenkaemotion.getText().toString()), Emotion.getText().toString(), Doi.getText().toString(), Think.getText().toString(), Time.getText().toString(), v);

    }

    private void postData(String cel, String dRazum, String prostr, int ocenkaemotion, String emotion, String doi, String think, String time,  View v) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/ngknn/БыковаАА/Api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitAPI = retrofit.create(RetrofitApi.class);
        Osozn modal = new Osozn(null, cel, dRazum, prostr, ocenkaemotion, emotion, doi, think, time);
        Call<Osozn> call = retrofitAPI.createPost(modal);
        call.enqueue(new Callback<Osozn>() {
            @Override
            public void onResponse(Call<Osozn> call, Response<Osozn> response) {
                Toast.makeText(AddData.this, "Данные успешно добавлены!", Toast.LENGTH_SHORT).show();
                GoViewData(v);
            }

            @Override
            public void onFailure(Call<Osozn> call, Throwable t) {
                Toast.makeText(AddData.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });
    }
}