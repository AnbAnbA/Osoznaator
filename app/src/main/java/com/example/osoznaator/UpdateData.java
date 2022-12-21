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

public class UpdateData extends AppCompatActivity {
    Bundle arg;
    Osozn osozn;
    EditText Cel, DRazum, Prostr, Ocenkaemotion, Emotion, Doi, Think, Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
        arg = getIntent().getExtras();
        osozn = arg.getParcelable(Osozn.class.getSimpleName());
        Cel = findViewById(R.id.Cel);
        DRazum = findViewById(R.id.DRazum);
        Prostr = findViewById(R.id.Prostr);
        Ocenkaemotion = findViewById(R.id.Ocenkaemotion);
        Emotion = findViewById(R.id.Emotion);
        Doi = findViewById(R.id.Doi);
        Think = findViewById(R.id.Think);
        Time = findViewById(R.id.Time);
        Cel.setText(osozn.getCel());
        DRazum.setText(osozn.getDRazum());
        Prostr.getText().toString();
        Ocenkaemotion.setText(Integer.toString(osozn.getOcenkaemotion()));
        Emotion.getText().toString();
        Doi.getText().toString();
        Think.getText().toString();
        Time.getText().toString();

    }



    public void Update(View v){

            DataPutDelete(osozn, 0, "Данные успешно изменены", v);

    }

    public void Delete(View v){
        DataPutDelete(osozn, 1, "Запись успешно удалена", v);
    }

    public void GoViewData(View v){
        startActivity(new Intent(this, MainActivity.class));
    }

    private void DataPutDelete(Osozn osozn, int number, String str, View v) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/ngknn/БыковаАА/Api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<Osozn> call = null;
        switch (number) {
            case 0:
                RetrofitApiPut retrofitAPI = retrofit.create(RetrofitApiPut.class);
                call = retrofitAPI.createPut(osozn, osozn.getID());
                break;
            case 1:
                RetrofitApiDelete retrofitAPIs = retrofit.create(RetrofitApiDelete.class);
                call = retrofitAPIs.createDelete(osozn.getID());
                break;
            default:
                break;
        }
        call.enqueue(new Callback<Osozn>() {
            @Override
            public void onResponse(Call<Osozn> call, Response<Osozn> response) {
                Toast.makeText(UpdateData.this, str, Toast.LENGTH_SHORT).show();
                GoViewData(v);
            }
            @Override
            public void onFailure(Call<Osozn> call, Throwable t) {
                Toast.makeText(UpdateData.this, "Ошибка!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}