package com.example.osoznaator;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitApiPut {
    @PUT("OsoznMains/")
    Call<Osozn> createPut(@Body Osozn dataModal, @Query("ID") int id);
}
