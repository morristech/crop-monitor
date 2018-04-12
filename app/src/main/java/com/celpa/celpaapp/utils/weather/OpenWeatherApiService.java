package com.celpa.celpaapp.utils.weather;


import com.google.gson.JsonObject;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApiService {

    @GET("weather")
    Flowable<JsonObject> getWeatherFromCoords(@Query("lat") double latitude,
                                              @Query("lon") double longitude,
                                              @Query("appid") String apiKey);

}
