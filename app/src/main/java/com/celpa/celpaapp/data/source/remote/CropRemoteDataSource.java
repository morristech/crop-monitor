package com.celpa.celpaapp.data.source.remote;


import com.celpa.celpaapp.data.Crop;
import com.celpa.celpaapp.data.CropDataSource;

import java.util.List;
import java.util.Optional;

import io.reactivex.Flowable;

public class CropRemoteDataSource implements CropDataSource {

    private static CropRemoteDataSource INSTANCE;

    public static CropRemoteDataSource getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new CropRemoteDataSource();
        }

        return INSTANCE;
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
    public void saveRecipe(Crop crop) {

    }
}
