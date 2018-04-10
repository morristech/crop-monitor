package com.celpa.celpaapp.data;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Optional;

import io.reactivex.Flowable;

public interface CropDataSource {

    Flowable<List<Crop>> getCrops();

    Flowable<JsonArray> getCropsInJson(long farmerId);

    Flowable<Crop> getCrop(String id);

    Flowable<JsonObject> saveCrop(Crop crop);

    Flowable deleteAll();
}
