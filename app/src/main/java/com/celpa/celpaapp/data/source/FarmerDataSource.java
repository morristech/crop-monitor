package com.celpa.celpaapp.data.source;


import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.data.Farmer;

import java.util.List;
import java.util.Optional;

import io.reactivex.Flowable;

public interface FarmerDataSource {

    Flowable<List<Farmer>> getFarmers();

    Flowable<Optional<Farmer>> getFarmer(String id);

    void registerFarmer(Farmer farmer);

}
