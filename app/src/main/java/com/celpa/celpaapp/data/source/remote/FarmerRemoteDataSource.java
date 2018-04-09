package com.celpa.celpaapp.data.source.remote;


import com.celpa.celpaapp.data.Farmer;
import com.celpa.celpaapp.data.source.FarmerDataSource;
import com.google.gson.JsonObject;
import com.squareup.sqlbrite2.QueryObservable;


import io.reactivex.BackpressureStrategy;
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
    public Flowable<JsonObject> registerFarmer(Farmer farmer) {
        return QueryObservable.just(new JsonObject()).toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<JsonObject> loginFarmer(String userName, String password) {
        CelpaApiService celpaApiService = CelpaApiHelper.getApiInstance();
        return celpaApiService.isFarmerRegistered(userName, password)
                .singleElement()
                .toFlowable();
    }

    @Override
    public Flowable<Farmer> getFarmer(String id) {
        return null;
    }

    @Override
    public Flowable<Farmer> saveFarmer(Farmer farmer) {
        return null;
    }

    @Override
    public Flowable deleteAll() {
        return null;
    }


}
