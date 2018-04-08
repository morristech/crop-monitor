package com.celpa.celpaapp.data.source;


import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.data.Farmer;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Optional;

import io.reactivex.Flowable;

public interface FarmerDataSource {

    void registerFarmer(Farmer farmer);

    Flowable<JsonObject> loginFarmer(String userName, String password);

}
