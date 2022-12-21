package com.example.osoznaator;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitApi {
    @POST("OsoznMains")
    Call<Osozn> createPost(@Body Osozn dataModal);
}
