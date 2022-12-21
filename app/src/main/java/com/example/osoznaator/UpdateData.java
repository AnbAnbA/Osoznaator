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
    Films film;
    EditText Name, Country, Year;
    ImageView img;
    Bitmap bitmap=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
        arg = getIntent().getExtras();
        film = arg.getParcelable(Films.class.getSimpleName());
        Name = findViewById(R.id.etName);
        Country = findViewById(R.id.etCountry);
        Year = findViewById(R.id.etYear);
        img = findViewById(R.id.imgPhoto);
        Name.setText(film.getNameFilm());
        Country.setText(film.getCountry());
        Year.setText(Integer.toString(film.getReleaseYear()));
        DecodeImageClass decodeImageClass = new DecodeImageClass(UpdateData.this);
        Bitmap userImage = decodeImageClass.getUserImage(film.getImageFilm());
        img.setImageBitmap(userImage);
        if(!film.getImageFilm().equals("null")){
            bitmap = userImage;
        }
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

    public void SelectPhoto(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        pickImg.launch(intent);
    }

    public void Update(View v){
        CheckFieldsClass checkFieldsClass = new CheckFieldsClass(UpdateData.this);
        if(checkFieldsClass.IsClear(Name.getText().toString(), Country.getText().toString(), Year.getText().toString())){
            film.setNameFilm(Name.getText().toString());
            film.setCountry(Country.getText().toString());
            film.setReleaseYear(Integer.parseInt(Year.getText().toString()));
            EncodeImageClass encodeImageClass = new EncodeImageClass();
            film.setImageFilm(encodeImageClass.Image(bitmap));
            DataPutDelete(film, 0, "Данные успешно изменены", v);
        }
    }

    public void Delete(View v){
        DataPutDelete(film, 1, "Запись успешно удалена", v);
    }

    public void GoViewData(View v){
        startActivity(new Intent(this, MainActivity.class));
    }

    private void DataPutDelete(Films film, int number, String str, View v) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ngknn.ru:5001/ngknn/РомановаЕА/practika/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<Films> call = null;
        switch (number) {
            case 0:
                RetrofitApiPut retrofitAPI = retrofit.create(RetrofitApiPut.class);
                call = retrofitAPI.createPut(film, film.getIdFilm());
                break;
            case 1:
                RetrofitApiDelete retrofitAPIs = retrofit.create(RetrofitApiDelete.class);
                call = retrofitAPIs.createDelete(film.getIdFilm());
                break;
            default:
                break;
        }
        call.enqueue(new Callback<Films>() {
            @Override
            public void onResponse(Call<Films> call, Response<Films> response) {
                Toast.makeText(UpdateData.this, str, Toast.LENGTH_SHORT).show();
                GoViewData(v);
            }
            @Override
            public void onFailure(Call<Films> call, Throwable t) {
                Toast.makeText(UpdateData.this, "Ошибка!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}