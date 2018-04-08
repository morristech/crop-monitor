package com.celpa.celpaapp.data.source.remote;


import android.content.Context;
import android.net.Uri;

import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.data.CropDataSource;
import com.celpa.celpaapp.data.Image;
import com.celpa.celpaapp.utils.PhotoUploadHelper;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;

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
    public Flowable<Optional<Crop>> getCrop(String id) {
        return null;
    }

    @Override
    public Flowable<Optional<Crop>> saveCrop(Crop crop) {
        List<MultipartBody.Part> files = new ArrayList<>(0);

        for(Image photo: crop.img) {
            PhotoUploadHelper.prepareFilePart(context,
                    "photos",
                    Uri.fromFile(new File(photo.imgPath)));
        }

        JsonObject json = new JsonObject();
        json.addProperty("name", crop.name);
        json.addProperty("location", crop.weather);

        // Save to remote server
        CelpaApiService apiService = CelpaApiHelper.getApiInstance();
        return apiService.uploadCropDetails(files, json);
    }
}
