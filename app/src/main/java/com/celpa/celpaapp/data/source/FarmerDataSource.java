package com.celpa.celpaapp.data.source;


import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.data.Farmer;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Optional;

import io.reactivex.Flowable;

public interface FarmerDataSource {

    Flowable<JsonObject> registerFarmer(Farmer farmer);

    Flowable<JsonObject> getFarmer(String userName, String password);

    Flowable<JsonObject> getFarmer(String id);

    Flowable<Farmer> saveFarmer(Farmer farmer);

    Flowable deleteAll();

}
