package com.celpa.celpaapp.data.source.remote;


import com.celpa.celpaapp.data.Farmer;
import com.celpa.celpaapp.data.source.FarmerDataSource;

import java.util.List;
import java.util.Optional;

import io.reactivex.Flowable;

public class FarmerRemoteDataSource implements FarmerDataSource {

    private static FarmerRemoteDataSource INSTANCE;

    public static FarmerRemoteDataSource getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new FarmerRemoteDataSource();
        }

        return INSTANCE;
    }

    @Override
    public Flowable<List<Farmer>> getFarmers() {
        CelpaApiService apiService = CelpaApiHelper.getApiInstance();
        return apiService.getFarmers();
    }

    @Override
    public Flowable<Optional<Farmer>> getFarmer(String id) {
        return null;
    }

    @Override
    public void registerFarmer(Farmer farmer) {
    }

}
