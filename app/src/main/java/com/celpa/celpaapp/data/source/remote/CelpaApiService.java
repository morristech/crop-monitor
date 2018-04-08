package com.celpa.celpaapp.data.source.remote;


import com.celpa.celpaapp.data.Farmer;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CelpaApiService {

    String BASE_URL = "http://192.168.1.33:3000/celpa/";

    @GET("getFarmers")
    Flowable<List<Farmer>> getFarmers();

    @GET("loginFarmer")
    Flowable<JsonObject> isFarmerRegistered(@Query("userName") String userName, @Query("password") String password);

    @GET("getFarmer")
    Flowable<Farmer> getFarmer(@Query("userName") String userName, @Query("password") String password);


}
