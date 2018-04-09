package com.celpa.celpaapp.data.source;


import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.data.Farmer;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Optional;

import io.reactivex.Flowable;

public interface FarmerDataSource {

    Flowable<Optional<JsonObject>> registerFarmer(Farmer farmer);

    Flowable<Optional<JsonObject>> loginFarmer(String userName, String password);

    Flowable<Optional<Farmer>> getFarmer(String id);

    Flowable<Optional<Farmer>> saveFarmer(Farmer farmer);

    Flowable deleteAll();

}
