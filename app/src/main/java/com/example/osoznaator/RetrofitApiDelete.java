package com.example.osoznaator;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Query;

public interface RetrofitApiDelete {
    @DELETE("OsoznMains/")
    Call<Osozn> createDelete(@Query("ID") int id);
}
