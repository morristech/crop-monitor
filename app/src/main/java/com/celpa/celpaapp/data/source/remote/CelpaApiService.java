package com.celpa.celpaapp.data.source.remote;


import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.data.Farmer;
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

    String BASE_URL = "http://192.168.1.33:3000/celpa/";

    @GET("farmer/getFarmers")
    Flowable<List<Farmer>> getFarmers();

    @GET("farmer/loginFarmer")
    Flowable<Optional<JsonObject>> isFarmerRegistered(@Query("userName") String userName, @Query("password") String password);

    @GET("farmer/getFarmer")
    Flowable<Farmer> getFarmer(@Query("userName") String userName, @Query("password") String password);

    @Multipart
    @POST("crop/details")
    Flowable<Optional<Crop>> uploadCropDetails(@Part List<MultipartBody.Part> photos, @Part("json") RequestBody json);

}
