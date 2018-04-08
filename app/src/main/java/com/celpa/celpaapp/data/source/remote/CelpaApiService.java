package com.celpa.celpaapp.data.source.remote;


import com.celpa.celpaapp.data.Farmer;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CelpaApiService {

    String API_ENDPOINT_CURRENT = "http://192.168.1.33:3000";

    @GET("/getFarmers")
    Flowable<List<Farmer>> getFarmers();

    @GET("/isFarmerRegistered")
    Flowable<JsonObject> isFarmerRegistered(@Query("userName") String userName, @Query("password") String password);

    @GET("/getFarmer")
    Flowable<Farmer> getFarmer(@Query("userName") String userName, @Query("password") String password);


}
