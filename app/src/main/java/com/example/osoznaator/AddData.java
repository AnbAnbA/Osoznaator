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

    ImageView img;
    Bitmap bitmap=null, b;
    EditText Name, Country, Year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        img = findViewById(R.id.imgPhoto);
        b = BitmapFactory.decodeResource(AddData.this.getResources(), R.drawable.picture);
        Name = findViewById(R.id.etName);
        Country = findViewById(R.id.etCountry);
        Year = findViewById(R.id.etYear);
        img.setImageBitmap(b);
    }

    private final ActivityResultLauncher<Intent> pickImg = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (result.getData() != null) {
                Uri uri = result.getData().getData();
                try {
                    InputStream is = getContentResolver().openInputStream(uri);
                    bitmap = BitmapFactory.decodeStream(is);
                    img.setImageBitmap(bitmap);
                } catch (Exception e) {
                    Log.e(e.toString(), e.getMessage());
                }
            }
        }
    });

    public void GoViewData(View v){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void SelectPhoto(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pickImg.launch(intent);
    }

    public void DataPost(View v){
        CheckFieldsClass checkFieldsClass = new CheckFieldsClass(AddData.this);
        if(checkFieldsClass.IsClear(Name.getText().toString(), Country.getText().toString(), Year.getText().toString())) {
            EncodeImageClass ei = new EncodeImageClass();
            postData(Name.getText().toString(), Country.getText().toString(), Integer.parseInt(Year.getText().toString()), ei.Image(bitmap), v);
        }
    }

    private void postData(String name, String country, int year, String image, View v) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/ngknn/РомановаЕА/practika/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitAPI = retrofit.create(RetrofitApi.class);
        Films modal = new Films(null, name, country, year, image);
        Call<Films> call = retrofitAPI.createPost(modal);
        call.enqueue(new Callback<Films>() {
            @Override
            public void onResponse(Call<Films> call, Response<Films> response) {
                Toast.makeText(AddData.this, "Данные успешно добавлены!", Toast.LENGTH_SHORT).show();
                GoViewData(v);
            }

            @Override
            public void onFailure(Call<Films> call, Throwable t) {
                Toast.makeText(AddData.this, "Ошибка", Toast.LENGTH_SHORT).show();
            }
        });
    }
}