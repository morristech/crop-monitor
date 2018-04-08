package com.celpa.celpaapp.data;


import java.util.List;
import java.util.Optional;

import io.reactivex.Flowable;

public interface CropDataSource {

    Flowable<List<Crop>> getCrops();

    Flowable<Optional<Crop>> getCrop(String id);

    Flowable<Optional<Crop>> saveCrop(Crop crop);

}
