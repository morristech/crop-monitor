package com.celpa.celpaapp.data.source.remote;


import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.data.Farmer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Optional;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface CelpaApiService {

    @GET("farmer/getFarmers")
    Flowable<List<Farmer>> getFarmers();

    @GET("farmer/getFarmer")
    Flowable<JsonObject> getFarmer(@Query("userName") String userName, @Query("password") String password);

    @POST("farmer/registerFarmer")
    Flowable<JsonObject> registerFarmer(@Body JsonObject body);

    @GET("crop/getCrops")
    Flowable<JsonArray> getCrops(@Query("farmerId") long farmerId);

    @Multipart
    @POST("crop/details")
    Flowable<JsonObject> uploadCropDetails(@Part List<MultipartBody.Part> photos, @Part("json") RequestBody json);

}
