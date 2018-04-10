package com.celpa.celpaapp.data.source.remote;


import android.content.Context;
import android.net.Uri;

import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.data.CropDataSource;
import com.celpa.celpaapp.data.Image;
import com.celpa.celpaapp.utils.PhotoUploadHelper;
import com.celpa.celpaapp.utils.RetrofitUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CropRemoteDataSource implements CropDataSource {

    private static CropRemoteDataSource INSTANCE;

    private Context context;

    public static CropRemoteDataSource getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new CropRemoteDataSource(context);
        }

        return INSTANCE;
    }

    private CropRemoteDataSource(Context context) {
        this.context = context;
    }

    @Override
    public Flowable<List<Crop>> getCrops() {
        return null;
    }

    @Override
    public Flowable<JsonArray> getCropsInJson() {
        CelpaApiService apiService = CelpaApiHelper.getApiInstance();
        return apiService.getCrops();
    }

    @Override
    public Flowable<Crop> getCrop(String id) {
        return null;
    }

    @Override
    public Flowable<JsonObject> saveCrop(Crop crop) {
        List<MultipartBody.Part> files = new ArrayList<>(0);

        for(Image photo: crop.img) {
            files.add(PhotoUploadHelper.prepareFilePart(context,
                    "photos",
                    Uri.fromFile(new File(photo.imgPath))));
        }

        JsonObject json = new JsonObject();
        json.addProperty("name", crop.name);
        json.addProperty("no_of_ferts_used", crop.noOfFertilizersUsed);
        json.addProperty("no_of_water_applied", crop.noOfWaterAppliedPerDay);
        json.addProperty("approx_date_harvest", crop.approxDateOfHarvest);
        json.addProperty("weather", crop.weather);
        json.addProperty("location", crop.location);
        json.addProperty("timeStamp", crop.timeStamp);

        RequestBody requestBody = RetrofitUtils.createPartFromJson(json);

        // Save to remote server
        CelpaApiService apiService = CelpaApiHelper.getApiInstance();
        return apiService.uploadCropDetails(files, requestBody);
    }

    @Override
    public Flowable deleteAll() {
        return null;
    }
}
