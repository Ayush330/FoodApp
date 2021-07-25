package com.example.test.Networking;

import com.example.test.POJO.RandomFoodResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ClientService
{
    @GET("/api/json/v1/1/random.php")
    Call<RandomFoodResponse> randomFoodFunction();

    @GET("/api/json/v1/1/search.php")
    Call<RandomFoodResponse> searchFoodResult(@Query("s") String value);
}
